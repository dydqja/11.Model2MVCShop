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
		System.out.println("NaverController 생성자");
	}
	//NaverLogin 버튼 클릭시 ajax로 해당 메서드 실행
    @RequestMapping( value="/getNaverAuthUrl", method=RequestMethod.GET )
    public @ResponseBody String getNaverAuthUrl( HttpSession session) throws Exception {
    	
    	System.out.println("/naver/getNaverAuthUrl :: GET");    	
    	//NaverLogin창으로 이동하는 url 만드는 메서드
    	String reqUrl = naverLoginService.getAuthorizationUrl(session);    	
    	
    	System.out.println("getNaverAuthUrl에서 return된 Url 값은: ["+reqUrl+"]");    	
    	//return시 클라이언트에서 해당 url페이지 실행
        return reqUrl;
    }
    //NaverLogin창에서 로그인 후 개인정보동의하면 return되는 callback url 실행
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	
    	System.out.println("/naver/oauth_naver :: GET");            	
    	
    	HttpSession session = request.getSession();    	
    	//정상적인경우 error값은 null 이어야함.
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
        //세션에 저장된 state값 비교해서 토큰 발급 받는과정
        OAuth2AccessToken oauthToken;
        //토큰 발급해서 객체에 저장
        oauthToken = naverLoginService.getAccessToken(session, code, state);    
        //로그인 사용자 정보를 읽어온다.
        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);        
        //JSON 형식의 문자열을 JSONObject로 변환
        JSONObject obj = new JSONObject(loginInfo);      
        //JSONObject에서 reponse값만 추출
        JSONObject callbackResponse = obj.getJSONObject("response");        
        //response 값에서 id값 추출
        String snsId = callbackResponse.getString("id");        
        // response값 domain에 저장하고 controller 호출
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


        // 네이버 정보조회 실패
        } else {            
            throw new Exception("Error occurred");
        }
        
        
    }
	
}