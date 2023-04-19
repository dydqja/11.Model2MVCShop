package com.model2.mvc.web.sms;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;
import com.model2.mvc.service.sms.SmsService;
import com.model2.mvc.service.sms.impl.SmsServiceImpl;

import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/sms")
public class SmsController {
	
	@Autowired
	@Qualifier("smsServiceImpl")
    private SmsService smsService;
	
	public SmsController() {
		System.out.println("SmsController »ý¼ºÀÚ");
	}
	
    @RequestMapping( value="/send", method=RequestMethod.POST )
    public SmsResponseDto sendSms( @RequestBody MessageDto messageDto) throws Exception {
    	
    	System.out.println("/sms/send :: POST");
    	
        SmsResponseDto responseDto = smsService.sendSms(messageDto);
        
        return responseDto;
    }
}