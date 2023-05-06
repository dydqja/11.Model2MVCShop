package com.model2.mvc.web.naver;




import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model2.mvc.service.domain.NaverUser;
import com.model2.mvc.service.naver.NaverUserService;


//==> 회원관리 Controller
@Controller
@RequestMapping("/naver/*")
public class NaverLoginController {
	
	///Field
	@Autowired
	@Qualifier("naverUserServiceImpl")
	private NaverUserService NaverUserService;
	//setter Method 구현 않음
		
	public NaverLoginController(){
		System.out.println(this.getClass());
	}
	//기존회원인지 신규회원인지 확인 후 로그인 시키는 메서드
	@RequestMapping( value="checkUser", method=RequestMethod.GET )
	public String checkUser( String id, HttpSession session, Model model, NaverUser naverUser ) throws Exception{
		
		System.out.println("/naver/checkUser : GET");
		
		naverUser = (NaverUser) session.getAttribute("naverUser");		
		id = naverUser.getId();		
		
		//Business Logic
		NaverUser DbUser=NaverUserService.checkUser(id);		
		//DB에 해당 유저가 없으면 DB에 추가 후 로그인
		if (DbUser == null) {
			
			NaverUserService.addUser(naverUser);			
			
			return "forward:main.jsp";
		//DB에 있는 회원이면 바로 로그인	
		}else {
			
		return "forward:/main.jsp";
		}
	}
}
	
