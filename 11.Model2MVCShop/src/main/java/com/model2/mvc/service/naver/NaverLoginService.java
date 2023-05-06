package com.model2.mvc.service.naver;

import javax.servlet.http.HttpSession;

import com.github.scribejava.core.model.OAuth2AccessToken;

public interface NaverLoginService {
	
	// Naver OAuth2 ������ ���� ����URL ����
	String getAuthorizationUrl(HttpSession session) throws Exception;
	
	// AccessToken �߱�
	OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws Exception;	
	
	// Token�� �̿��Ͽ� ����� ������ ���� ��������
	String getUserProfile(HttpSession session, OAuth2AccessToken oauthToken) throws Exception;

}
