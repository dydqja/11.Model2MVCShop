package com.model2.mvc.service.sms;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;



public interface SmsService {	
	
	//������ ����
	String getSignature(String time) throws Exception;
	
    //sms ����
    SmsResponseDto sendSms(MessageDto messageDto) throws Exception;
    
    //������ȣ ����
    public String createSmsKey();
    
    //������ȣ Ȯ��
    public String phCodeConfirm(String phCodeConfirm, String smsConfirmNum) throws Exception;
    
}