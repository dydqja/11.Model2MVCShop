package com.model2.mvc.service.sms.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsRequestDto;
import com.model2.mvc.service.domain.SmsResponseDto;
import com.model2.mvc.service.sms.SmsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@PropertySource("classpath:/config/application.properties")
//@Slf4j
//@RequiredArgsConstructor
@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {
	
	public SmsServiceImpl() { 
		System.out.println("SMSServiceImpl ������");
	}
	
	private final String smsConfirmNum = createSmsKey();//�޴��� ���� ��ȣ
	
    //private final RedisUtill redisUtil;

    @Value("#{applicationProperties['naver-cloud-sms.accessKey']}")
    private String accessKey;

    @Value("#{applicationProperties['naver-cloud-sms.secretKey']}")
    private String secretKey;

    @Value("#{applicationProperties['naver-cloud-sms.serviceId']}")
    private String serviceId;

    @Value("#{applicationProperties['naver-cloud-sms.senderPhone']}")
    private String phone;
    
    @Override
    public String getSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
    
    @Override
    public SmsResponseDto sendSms(MessageDto messageDto) throws Exception{
        String time = Long.toString(System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature ����

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        SmsRequestDto request = SmsRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("[���񽺸� �׽�Ʈ��] ������ȣ [" + smsConfirmNum + "]�� �Է����ּ���")
                .messages(messages)
                .build();

        //���� �ٵ� json���·� ��ȯ
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        // jsonBody�� ��� ����
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        //restTemplate�� post ��û ������ ������ ������ 202�ڵ� ��ȯ
        SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDto.class);
        SmsResponseDto responseDto = new SmsResponseDto(smsConfirmNum);
       // redisUtil.setDataExpire(smsConfirmNum, messageDto.getTo(), 60 * 3L); // ��ȿ�ð� 3��
        return smsResponseDto;
    }

    // �����ڵ� �����
    public String createSmsKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // �����ڵ� 6�ڸ�
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
    
}