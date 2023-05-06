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


//==> ȸ������ Controller
@Controller
@RequestMapping("/naver/*")
public class NaverLoginController {
	
	///Field
	@Autowired
	@Qualifier("naverUserServiceImpl")
	private NaverUserService NaverUserService;
	//setter Method ���� ����
		
	public NaverLoginController(){
		System.out.println(this.getClass());
	}
	//����ȸ������ �ű�ȸ������ Ȯ�� �� �α��� ��Ű�� �޼���
	@RequestMapping( value="checkUser", method=RequestMethod.GET )
	public String checkUser( String id, HttpSession session, Model model, NaverUser naverUser ) throws Exception{
		
		System.out.println("/naver/checkUser : GET");
		
		naverUser = (NaverUser) session.getAttribute("naverUser");		
		id = naverUser.getId();		
		
		//Business Logic
		NaverUser DbUser=NaverUserService.checkUser(id);		
		//DB�� �ش� ������ ������ DB�� �߰� �� �α���
		if (DbUser == null) {
			
			NaverUserService.addUser(naverUser);			
			
			return "forward:main.jsp";
		//DB�� �ִ� ȸ���̸� �ٷ� �α���	
		}else {
			
		return "forward:/main.jsp";
		}
	}
}
	
