package com.model2.mvc.service.sms;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;



public interface SmsService {	
	
	//인증서 생성
	String getSignature(String time) throws Exception;
	
    //sms 전송
    SmsResponseDto sendSms(MessageDto messageDto) throws Exception;
    
    //인증번호 생성
    public String createSmsKey();
    
    //인증번호 확인
    public String phCodeConfirm(String phCodeConfirm, String smsConfirmNum) throws Exception;
    
}