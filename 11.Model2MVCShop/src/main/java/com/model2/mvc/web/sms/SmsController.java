package com.model2.mvc.web.sms;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.sms.SmsService;
import com.model2.mvc.service.user.UserService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/sms/*")
public class SmsController {
	
	///Field
	@Autowired
	@Qualifier("smsServiceImpl")
	private SmsService smsService;
	//setter Method ���� ����
		
	public SmsController(){
		
	}		
	
//	@RequestMapping( value="phCodeConfirm", method=RequestMethod.POST )
//	public String phCodeConfirm( @RequestParam("phCodeConfirm") String phCodeConfirm , Model model ) throws Exception{
//		
//		System.out.println("/sms/phCodeConfirm : POST");
//		//Business Logic
//		boolean result=smsService.phCodeConfirm(phCodeConfirm);
//		// Model �� View ����
//		model.addAttribute("result", new Boolean(result));
//		model.addAttribute("phCodeConfirm", phCodeConfirm);
//
//		return "forward:/sms/phCodeConfirm.jsp";
//	}

	
	
}