package com.model2.mvc.web.naverLogin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.scribejava.core.model.OAuth2AccessToken;

import com.model2.mvc.service.naverLogin.NaverLoginService;







@RestController
@RequestMapping("/naverLogin")
public class NaverLoginRestController {
	
	@Autowired
	@Qualifier("naverLoginServiceImpl")
    private NaverLoginService naverLoginService;
	
	public NaverLoginRestController() {
		System.out.println("NaverLoginController ������");
	}
	
    @RequestMapping( value="/getNaverAuthUrl", method=RequestMethod.GET )
    public @ResponseBody String getNaverAuthUrl( HttpSession session) throws Exception {
    	
    	System.out.println("/naverLogin/getNaverAuthUrl :: GET");    	
    	
    	String reqUrl = naverLoginService.getAuthorizationUrl(session);
    	
//    	System.out.println(session.getAttribute("oauth_state"));
    	System.out.println("1.getNaverAuthUrl������ session Id = " +session.getId());
    	
    	System.out.println("getNaverAuthUrl���� return�� Url ����: ["+reqUrl+"]");    	
    	
        return reqUrl;
    }
    
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	System.out.println("/naverLogin/oauth_naver :: GET");            	
    	
    	HttpSession session = request.getSession();
    	System.out.println("2.oauthNaver������ session Id =" + session.getId());
    	
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

        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state);
        
        System.out.println("getAccessToken���� return�� aouthToken ����: ["+oauthToken+"]");        
    
        //�α��� ����� ������ �о�´�.
        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);
        
        System.out.println("getUserProfile���� return�� response.getBody() ����: ["+loginInfo+"]");
        
        JSONObject obj = null;
                
        // JSON ���·� ��ȯ
        obj = new JSONObject(loginInfo);
        System.out.println("JSON������ ���ڿ� -> JSONObject:["+obj+"]�� ��ȯ");        
        
        JSONObject callbackResponse = null;
        callbackResponse = obj.getJSONObject("response");
        System.out.println("JSONObject ���� response���� ���� ==> ["+callbackResponse+"]");
        
        String snsId = null;
        snsId = callbackResponse.getString("id");
        System.out.println("response������ id�� ������ ==> ["+snsId+"]");

        if (snsId != null && !snsId.equals("")) {
        	
//        	NaverLoginUser naverLoginUser = new NaverLoginUser()
//        			.setSnsId()
//        			.set
        	

            /** 
            
                TO DO : ���Ϲ��� naverUniqueNo �ش��ϴ� ȸ������ ��ȸ �� �α��� ó�� �� �������� �̵�
            
            */

        // ���̹� ������ȸ ����
        } else {            
            throw new Exception("Error occurred");
        }
        return null;
        
    }
	
}