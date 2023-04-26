package com.model2.mvc.web.sms;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.MessageDto;
import com.model2.mvc.service.domain.SmsResponseDto;
import com.model2.mvc.service.domain.PhCodeConfirmRequest;
import com.model2.mvc.service.sms.SmsService;





@RestController
@RequestMapping("/sms")
public class SmsRestController {
	
	@Autowired
	@Qualifier("smsServiceImpl")
    private SmsService smsService;
	
	public SmsRestController() {
		System.out.println("SmsController 생성자");
	}
	
    @RequestMapping( value="/send", method=RequestMethod.POST )
    public SmsResponseDto sendSms( @RequestBody MessageDto messageDto) throws Exception {
    	
    	System.out.println("/sms/send :: POST");
    	
        SmsResponseDto responseDto = smsService.sendSms(messageDto);
        
        System.out.println("controller에 return된 SmsResponseDto값은? ==> " + responseDto);
        
        return responseDto;
    }
    
	@RequestMapping( value="/phCodeConfirm", method=RequestMethod.POST )
	public String phCodeConfirm( @RequestBody PhCodeConfirmRequest request) throws Exception{
		
		System.out.println("/sms/phCodeConfirm : POST");
		
		String phCodeConfirm = request.getPhCodeConfirm();
	    String smsConfirmNum = request.getSmsConfirmNum();
		
		String result=smsService.phCodeConfirm(phCodeConfirm,smsConfirmNum);
		
		System.out.println("controller에 return된 result값은? ==> " + result);

		return result;
	}
}