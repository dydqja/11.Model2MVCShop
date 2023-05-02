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
		System.out.println("NaverLoginController 생성자");
	}
	
    @RequestMapping( value="/getNaverAuthUrl", method=RequestMethod.GET )
    public @ResponseBody String getNaverAuthUrl( HttpSession session) throws Exception {
    	
    	System.out.println("/naverLogin/getNaverAuthUrl :: GET");    	
    	
    	String reqUrl = naverLoginService.getAuthorizationUrl(session);
    	
//    	System.out.println(session.getAttribute("oauth_state"));
    	System.out.println("1.getNaverAuthUrl에서의 session Id = " +session.getId());
    	
    	System.out.println("getNaverAuthUrl에서 return된 Url 값은: ["+reqUrl+"]");    	
    	
        return reqUrl;
    }
    
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	System.out.println("/naverLogin/oauth_naver :: GET");            	
    	
    	HttpSession session = request.getSession();
    	System.out.println("2.oauthNaver에서의 session Id =" + session.getId());
    	
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
        
        System.out.println("QueryString으로 온 code,state,error값은? => code:["+code+"], state:["+state+"], error:["+error+"]");
        
        // 로그인 팝업창에서 취소버튼 눌렀을경우
        if ( error != null ){
            if(error.equals("access_denied")){
                return "redirect:/login";
            }
        }            

        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state);
        
        System.out.println("getAccessToken에서 return된 aouthToken 값은: ["+oauthToken+"]");        
    
        //로그인 사용자 정보를 읽어온다.
        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);
        
        System.out.println("getUserProfile에서 return된 response.getBody() 값은: ["+loginInfo+"]");
        
        JSONObject obj = null;
                
        // JSON 형태로 변환
        obj = new JSONObject(loginInfo);
        System.out.println("JSON형식의 문자열 -> JSONObject:["+obj+"]로 변환");        
        
        JSONObject callbackResponse = null;
        callbackResponse = obj.getJSONObject("response");
        System.out.println("JSONObject 에서 response값만 빼옴 ==> ["+callbackResponse+"]");
        
        String snsId = null;
        snsId = callbackResponse.getString("id");
        System.out.println("response값에서 id값 가져옴 ==> ["+snsId+"]");

        if (snsId != null && !snsId.equals("")) {
        	
//        	NaverLoginUser naverLoginUser = new NaverLoginUser()
//        			.setSnsId()
//        			.set
        	

            /** 
            
                TO DO : 리턴받은 naverUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
            
            */

        // 네이버 정보조회 실패
        } else {            
            throw new Exception("Error occurred");
        }
        return null;
        
    }
	
}