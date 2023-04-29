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
		System.out.println("NaverLoginController ������");
	}
	
    @RequestMapping( value="/getNaverAuthUrl", method=RequestMethod.GET )
    public @ResponseBody String getNaverAuthUrl( HttpSession session) throws Exception {
    	
    	System.out.println("/naverLogin/getNaverAuthUrl :: GET");
    	
    	String reqUrl = naverLoginService.getAuthorizationUrl(session);
    	
    	System.out.println("NaverLoginServiceImpl���� return�� accessToken ����: ["+reqUrl+"]");
    	
        return reqUrl;
    }
    
    @RequestMapping(value = "/oauth_naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	System.out.println("/naverLogin/oauth_naver :: GET");        

//        JSONParser parser = new JSONParser(); // => JSON��ü <-> java��ü ��ȯ�� ���
//        Gson gson = new Gson(); // JSON�����Ϳ� �ڹٰ�ü���� ����ȭ �� ������ȭ ó��        

        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
        
        System.out.println("QueryString���� �� session,code,state,error����? => session:["+session+"], code:["+code+"], state:["+state+"], error:["+error+"]");
        
        // �α��� �˾�â���� ��ҹ�ư ���������
        if ( error != null ){
            if(error.equals("access_denied")){
                return "redirect:/login";
            }
        }    

        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state);
        
        System.out.println("NaverLoginServiceImpl���� return�� aouthToken ����: ["+oauthToken+"]");
        
        return null;
    }
//        //�α��� ����� ������ �о�´�.
//        String loginInfo = naverLoginService.getUserProfile(session, oauthToken);
//
//        // JSON ���·� ��ȯ
//        Object obj = parser.parse(loginInfo);
//        JSONObject jsonObj = JSONObject.fromObject(gson.toJson(obj));
//        JSONObject callbackResponse = (JSONObject) jsonObj.get("response");
//        String naverUniqueNo = callbackResponse.get("id").toString();
//
//        if (naverUniqueNo != null && !naverUniqueNo.equals("")) {
//
//            /** 
//            
//                TO DO : ���Ϲ��� naverUniqueNo �ش��ϴ� ȸ������ ��ȸ �� �α��� ó�� �� �������� �̵�
//            
//            */
//
//        // ���̹� ������ȸ ����
//        } else {
//            throw new ErrorMessage("���̹� ������ȸ�� �����߽��ϴ�.");
//        }
//
//    }
//	
}