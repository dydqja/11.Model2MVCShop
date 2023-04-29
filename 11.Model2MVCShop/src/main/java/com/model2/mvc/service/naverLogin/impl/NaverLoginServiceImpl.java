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
		System.out.println("NaverLoginServiceImpl ������");
	}
	
	// �߱޹��� client id;
	@Value("#{applicationProperties['naver.client.id']}")
    private String CLIENT_ID;

    // �߱޹��� client Secret
	@Value("#{applicationProperties['naver.client.secret']}")
    private String CLIENT_SECRET;

    // �α��μ����� ���ϵ� URL
	@Value("#{applicationProperties['naver.redirect.url']}")
    private String REDIRECT_URI;
    
    // ���� ��ȿ�������� üũ��
    private static final String SESSION_STATE = "oauth_state"; 

    // ������ ��ȸ API URL
    private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
    
    @Override
    public String getAuthorizationUrl(HttpSession session) {
    	
    	System.out.println("NaverLoginServiceImpl���� getAuthorizationUrl �����.");
    	
        // ���� ��ȿ�� ������ ���Ͽ� ������ ����
        String state = generateRandomString();
        // ������ ���� ���� session�� ����
        setSession(session, state);

        // Scribe���� �����ϴ� ���� URL ���� ����� �̿��Ͽ� �׾Ʒ� ���� URL ����
        OAuth20Service oauthService = new ServiceBuilder()
        		.apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)
                .state(state) //�ռ� ������ �������� ���� URL������ �����
                .build(NaverLogin.instance());
        return oauthService.getAuthorizationUrl();
    }
    
    @Override
    public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
    	
    	System.out.println("NaverLoginServiceImpl ���� getAccessToken �����.");
    	
        // Callback���� ���޹��� ���������� �������� ���ǿ� ����Ǿ��ִ� ���� ��ġ�ϴ��� Ȯ��
        String sessionState = getSession(session);
        
        System.out.println("getSession(session) == "+sessionState);
        
        if (StringUtils.pathEquals(sessionState, state)) {

            OAuth20Service oauthService = new ServiceBuilder()
                    .apiKey(CLIENT_ID)
                    .apiSecret(CLIENT_SECRET)
                    .callback(REDIRECT_URI)
                    .state(state)
                    .build(NaverLogin.instance());

            // Scribe���� �����ϴ� AccessToken ȹ�� ������� �׾Ʒ� Access Token�� ȹ��
            OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
            return accessToken;
        }
        return null;
    }

    // ���� ��ȿ�� ������ ���� ���� ������
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    // http session�� ������ ����
    private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }

    // http session���� ������ ��������
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }

    // Access Token�� �̿��Ͽ� ���̹� ����� ������ API�� ȣ��
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
