package com.model2.mvc.web.naver;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.github.scribejava.core.model.OAuth2AccessToken;
import com.model2.mvc.service.domain.NaverUser;
import com.model2.mvc.service.naver.NaverLoginService;







@RestController
@RequestMapping("/naver")
public class NaverLoginRestController {
	
	@Autowired
	@Qualifier("naverLoginServiceImpl")
    private NaverLoginService naverLoginService;
	
	public NaverLoginRestController() {
		System.out.println("NaverController ������");
	}
	//NaverLogin ��ư Ŭ���� ajax�� �ش� �޼��� ����
    @RequestMapping( value="/getNaverAuthUrl", method=RequestMethod.GET )
    public @ResponseBody String getNaverAuthUrl( HttpSession session) throws Exception {
    	
    	System.out.println("/naver/getNaverAuthUrl :: GET");    	
    	//NaverLoginâ���� �̵��ϴ� url ����� �޼���
    	String reqUrl = naverLoginService.getAuthorizationUrl(session);    	
    	
    	System.out.println("getNaverAuthUrl���� return�� Url ����: ["+reqUrl+"]");    	
    	//return�� Ŭ���̾�Ʈ���� �ش� url������ ����
        return reqUrl;
    }
    //NaverLoginâ���� �α��� �� �������������ϸ� return�Ǵ� callback url ����
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	System.out.println("/naver/oauth_naver :: GET");            	
    	
    	HttpSession session = request.getSession();    	
    	//�������ΰ�� error���� null �̾����.
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
        
        System.out.println("QueryString���� �� code,state,error����? => code:["+code+"], state:["+state+"], error:["+error+"]");
        
        // �α��� �˾�â���� ��ҹ�ư ���������
        if ( error != null ){
            if(error.equals("access_denied")){
                return "redirect:/login";
            }
        }            
        //���ǿ� ����� state�� ���ؼ� ��ū �߱� �޴°���
        OAuth2AccessToken oauthToken;
        //��ū �߱��ؼ� ��ü�� ����
        oauthToken = naverLoginService.getAccessToken(session, code, state);    
        //�α��� ����� ������ �о�´�.
        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);        
        //JSON ������ ���ڿ��� JSONObject�� ��ȯ
        JSONObject obj = new JSONObject(loginInfo);      
        //JSONObject���� reponse���� ����
        JSONObject callbackResponse = obj.getJSONObject("response");        
        //response ������ id�� ����
        String snsId = callbackResponse.getString("id");        
        // response�� domain�� �����ϰ� controller ȣ��
        if (snsId != null && !snsId.equals("")) {
        	
        	NaverUser naverUser = new NaverUser()
        			.setId(callbackResponse.getString("id"))
        			.setBirthday(callbackResponse.getString("birthday"))
        			.setGender(callbackResponse.getString("gender"))
        			.setBirthyear(callbackResponse.getString("birthyear"))
        			.setMobile(callbackResponse.getString("mobile"))
        			.setName(callbackResponse.getString("name"))
        			.setAge(callbackResponse.getString("age"))
        			.setEmail(callbackResponse.getString("email"));        	
        	
        	session.setAttribute("naverUser", naverUser);        	
        	
        	response.sendRedirect("/naver/checkUser");
        	
        	return null;


        // ���̹� ������ȸ ����
        } else {            
            throw new Exception("Error occurred");
        }
        
        
    }
	
}