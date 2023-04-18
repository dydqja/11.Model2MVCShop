package com.model2.mvc.service.sms;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;



public interface SmsService {
	
	String getSignature(String time) throws Exception;    
    
    SmsResponseDto sendSms(MessageDto messageDto) throws Exception;
    
    public String createSmsKey();
    
}