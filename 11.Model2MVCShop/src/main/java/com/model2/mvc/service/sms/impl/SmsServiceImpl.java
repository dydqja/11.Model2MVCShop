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
		System.out.println("SMSServiceImpl 생성자");
	}
	
	private final String smsConfirmNum = createSmsKey();//휴대폰 인증 번호
	
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
        //String encodeBase64String = Base64.encodeBase64String(rawHmac); ==> org.apache.hc.client5.http.utils.Base64 라이브러리를 이용한방법.
        String encodeBase64String = new String(Base64.getEncoder().encode(rawHmac)); // ==> java.util.Base64 라이브러리를 이용한방법.

        return encodeBase64String;
    }
    
    @Override
    public SmsResponseDto sendSms(MessageDto messageDto) throws Exception{
    	
    	System.out.println("SmsServiceImpl에서 sendSms 실행됨.===========");
    	
        String time = Long.toString(System.currentTimeMillis());
        
        System.out.println("1. time 값은? ==========" + time );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature 서명
        
        System.out.println("2. Header 값은? ==========" + headers);

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);        

        SmsRequestDto request = new SmsRequestDto.Builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("[서비스명 모여행] 인증번호 [" + smsConfirmNum + "]를 입력해주세요")
                .messages(messages)
                .build();
        
        //쌓은 바디를 json형태로 반환        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime 객체 역직렬화 실패로 인한 추가사항
        String body = objectMapper.writeValueAsString(request);
        
        // jsonBody와 헤더 조립
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);        
        System.out.println("3. body+header 값은? ==========" + httpBody);
        
        
        
        
        CloseableHttpClient httpClient = HttpClients.createDefault(); // <= HttpClients를 생성하면서 문제가있는지, 이 메서드 사용하면 creating bean 에러 뜸        
        //CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
//        RequestConfig requestConfig = RequestConfig.custom()
//        	    .setConnectTimeout(5000) // 연결 시간 초과를 5초로 설정
//        	    .setSocketTimeout(5000) // 소켓 시간 초과를 5초로 설정
//        	    .build();
//
//        	CloseableHttpClient httpClient = HttpClients.custom()
//        	    .setDefaultRequestConfig(requestConfig)
//        	    .build();
        
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);        
        RestTemplate restTemplate = new RestTemplate();        
        restTemplate.setRequestFactory(factory);        
        
//        restTemplate.setRequestFactory 확인용(삭제해도 문제없음)
        ClientHttpRequestFactory currentFactory = restTemplate.getRequestFactory();        
        if (currentFactory instanceof HttpComponentsClientHttpRequestFactory) {
            System.out.println("HttpComponentsClientHttpRequestFactory is configured");
        } else {
            System.out.println("HttpComponentsClientHttpRequestFactory is not configured");
        }        
        
        //restTemplate로 post 요청 보내고 오류가 없으면 202코드 반환
        System.out.println("Before restTemplate.postForObject");
        //SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDto.class);
        
        //HttpEntity<?> httpEntity = new HttpEntity<>(httpBody, headers);
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";
        System.out.println("url값 확인용 =====> " + url);
        //System.out.println("httpEntity: " + httpEntity);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.POST, httpBody, String.class);
        System.out.println("responseEntity 확인용 =====> " + responseEntity);
     // JSON 응답을 문자열로 확인
        String jsonResponse = responseEntity.getBody();
        System.out.println("JSON 응답: " + jsonResponse);
     // 이제 필요한 경우 문자열 형식의 JSON 응답을 SmsResponseDto 객체로 변환할 수 있습니다.
        SmsResponseDto smsResponseDto = objectMapper.readValue(jsonResponse, SmsResponseDto.class);        
        System.out.println("4. smsResponseDto 값은? ==========" + smsResponseDto.getSmsConfirmNum());        
        
        SmsResponseDto responseDto = new SmsResponseDto(smsConfirmNum);
        System.out.println("5. responseDto 값은? ==========" + responseDto);
       // redisUtil.setDataExpire(smsConfirmNum, messageDto.getTo(), 60 * 3L); // 유효시간 3분        
        
        return smsResponseDto;
        
    }
    

    // 인증코드 만들기
    public String createSmsKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
    
}