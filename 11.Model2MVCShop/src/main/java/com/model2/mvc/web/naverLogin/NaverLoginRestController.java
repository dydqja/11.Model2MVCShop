package com.model2.mvc.web.naverLogin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
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
    	
    	System.out.println("NaverLoginServiceImpl에서 return된 accessToken 값은: ["+reqUrl+"]");
    	
        return reqUrl;
    }
    
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	System.out.println("/naverLogin/oauth_naver :: GET");        

//        JSONParser parser = new JSONParser(); // => JSON객체 <-> java객체 변환에 사용
//        Gson gson = new Gson(); // JSON데이터와 자바객체간의 직렬화 및 역직렬화 처리        

        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
        
        System.out.println("QueryString으로 온 session,code,state,error값은? => session:["+session+"], code:["+code+"], state:["+state+"], error:["+error+"]");
        
        // 로그인 팝업창에서 취소버튼 눌렀을경우
        if ( error != null ){
            if(error.equals("access_denied")){
                return "redirect:/login";
            }
        }    

        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state);
        
        System.out.println("NaverLoginServiceImpl에서 return된 aouthToken 값은: ["+oauthToken+"]");
        
        return null;
    }
//        //로그인 사용자 정보를 읽어온다.
//        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);
//
//        // JSON 형태로 변환
//        Object obj = parser.parse(loginInfo);
//        JSONObject jsonObj = JSONObject.fromObject(gson.toJson(obj));
//        JSONObject callbackResponse = (JSONObject) jsonObj.get("response");
//        String naverUniqueNo = callbackResponse.get("id").toString();
//
//        if (naverUniqueNo != null && !naverUniqueNo.equals("")) {
//
//            /** 
//            
//                TO DO : 리턴받은 naverUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
//            
//            */
//
//        // 네이버 정보조회 실패
//        } else {
//            throw new ErrorMessage("네이버 정보조회에 실패했습니다.");
//        }
//
//    }
//	
}