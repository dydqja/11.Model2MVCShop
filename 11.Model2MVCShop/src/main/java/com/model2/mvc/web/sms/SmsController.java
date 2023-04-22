package com.model2.mvc.web.sms;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;
import com.model2.mvc.service.sms.SmsService;





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