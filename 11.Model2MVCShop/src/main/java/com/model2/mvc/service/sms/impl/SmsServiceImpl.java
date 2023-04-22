package com.model2.mvc.service.sms.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsRequestDto;
import com.model2.mvc.service.domain.SmsResponseDto;
import com.model2.mvc.service.sms.SmsService;


import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.ClientHttpRequestFactory;



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
    public String getSignature(String time) throws Exception {
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
        //String encodeBase64String = Base64.encodeBase64String(rawHmac); ==> org.apache.hc.client5.http.utils.Base64 ���̺귯���� �̿��ѹ��.
        String encodeBase64String = new String(Base64.getEncoder().encode(rawHmac)); // ==> java.util.Base64 ���̺귯���� �̿��ѹ��.

        return encodeBase64String;
    }
    
    @Override
    public SmsResponseDto sendSms(MessageDto messageDto) throws Exception{
    	
    	System.out.println("SmsServiceImpl���� sendSms �����.===========");
    	
        String time = Long.toString(System.currentTimeMillis());
        
        System.out.println("1. time ����? ==========" + time );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature ����
        
        System.out.println("2. Header ����? ==========" + headers);

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);        

        SmsRequestDto request = new SmsRequestDto.Builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("[���񽺸� ����] ������ȣ [" + smsConfirmNum + "]�� �Է����ּ���")
                .messages(messages)
                .build();
        
        //���� �ٵ� json���·� ��ȯ        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime ��ü ������ȭ ���з� ���� �߰�����
        String body = objectMapper.writeValueAsString(request);
        
        // jsonBody�� ��� ����
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);        
        System.out.println("3. body+header ����? ==========" + httpBody);
        
        
        
        
        CloseableHttpClient httpClient = HttpClients.createDefault(); // <= HttpClients�� �����ϸ鼭 �������ִ���, �� �޼��� ����ϸ� creating bean ���� ��        
        //CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
//        RequestConfig requestConfig = RequestConfig.custom()
//        	    .setConnectTimeout(5000) // ���� �ð� �ʰ��� 5�ʷ� ����
//        	    .setSocketTimeout(5000) // ���� �ð� �ʰ��� 5�ʷ� ����
//        	    .build();
//
//        	CloseableHttpClient httpClient = HttpClients.custom()
//        	    .setDefaultRequestConfig(requestConfig)
//        	    .build();
        
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);        
        RestTemplate restTemplate = new RestTemplate();        
        restTemplate.setRequestFactory(factory);        
        
//        restTemplate.setRequestFactory Ȯ�ο�(�����ص� ��������)
        ClientHttpRequestFactory currentFactory = restTemplate.getRequestFactory();        
        if (currentFactory instanceof HttpComponentsClientHttpRequestFactory) {
            System.out.println("HttpComponentsClientHttpRequestFactory is configured");
        } else {
            System.out.println("HttpComponentsClientHttpRequestFactory is not configured");
        }        
        
        //restTemplate�� post ��û ������ ������ ������ 202�ڵ� ��ȯ
        System.out.println("Before restTemplate.postForObject");
        //SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDto.class);
        
        //HttpEntity<?> httpEntity = new HttpEntity<>(httpBody, headers);
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";
        System.out.println("url�� Ȯ�ο� =====> " + url);
        //System.out.println("httpEntity: " + httpEntity);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.POST, httpBody, String.class);
        System.out.println("responseEntity Ȯ�ο� =====> " + responseEntity);
     // JSON ������ ���ڿ��� Ȯ��
        String jsonResponse = responseEntity.getBody();
        System.out.println("JSON ����: " + jsonResponse);
     // ���� �ʿ��� ��� ���ڿ� ������ JSON ������ SmsResponseDto ��ü�� ��ȯ�� �� �ֽ��ϴ�.
        SmsResponseDto smsResponseDto = objectMapper.readValue(jsonResponse, SmsResponseDto.class);        
        System.out.println("4. smsResponseDto ����? ==========" + smsResponseDto.getSmsConfirmNum());        
        
        SmsResponseDto responseDto = new SmsResponseDto(smsConfirmNum);
        System.out.println("5. responseDto ����? ==========" + responseDto);
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