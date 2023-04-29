package com.model2.mvc.service.naverLogin.impl;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.model2.mvc.service.domain.NaverLogin;
import com.model2.mvc.service.naverLogin.NaverLoginService;

@Service("naverLoginServiceImpl")
public class NaverLoginServiceImpl implements NaverLoginService {
	
	public NaverLoginServiceImpl() { 
		System.out.println("NaverLoginServiceImpl 생성자");
	}
	
	// 발급받은 client id;
	@Value("#{applicationProperties['naver.client.id']}")
    private String CLIENT_ID;

    // 발급받은 client Secret
	@Value("#{applicationProperties['naver.client.secret']}")
    private String CLIENT_SECRET;

    // 로그인성공시 리턴될 URL
	@Value("#{applicationProperties['naver.redirect.url']}")
    private String REDIRECT_URI;
    
    // 세션 유효값검증용 체크용
    private static final String SESSION_STATE = "oauth_state"; 

    // 프로필 조회 API URL
    private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
    
    @Override
    public String getAuthorizationUrl(HttpSession session) {
    	
    	System.out.println("NaverLoginServiceImpl에서 getAuthorizationUrl 실행됨.");
    	
        // 세션 유효성 검증을 위하여 난수를 생성
        String state = generateRandomString();
        // 생성한 난수 값을 session에 저장
        setSession(session, state);

        // Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성
        OAuth20Service oauthService = new ServiceBuilder()
        		.apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state) //앞서 생성한 난수값을 인증 URL생성시 사용함
                .build(NaverLogin.instance());
        return oauthService.getAuthorizationUrl();
    }
    
    @Override
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
    	
    	System.out.println("NaverLoginServiceImpl 에서 getAccessToken 실행됨.");
    	
        // Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인
        String sessionState = getSession(session);
        
        System.out.println("getSession(session) == "+sessionState);
        
        if (StringUtils.pathEquals(sessionState, state)) {

            OAuth20Service oauthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLogin.instance());

            // Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득
            OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
            return accessToken;
        }
        return null;
    }

    // 세션 유효성 검증을 위한 난수 생성기
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    // http session에 데이터 저장
    private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }

    // http session에서 데이터 가져오기
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }

    // Access Token을 이용하여 네이버 사용자 프로필 API를 호출
    @Override
    public String getUserProfile(HttpSession session, OAuth2AccessToken oauthToken) throws IOException {

        OAuth20Service oauthService = new ServiceBuilder()
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI).build(NaverLogin.instance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
        oauthService.signRequest(oauthToken, request);
        Response response = request.send();
        return response.getBody();
    }

}
