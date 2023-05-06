package com.model2.mvc.service.naver;

import javax.servlet.http.HttpSession;

import com.github.scribejava.core.model.OAuth2AccessToken;

public interface NaverLoginService {
	
	// Naver OAuth2 인증을 위한 인증URL 생성
	String getAuthorizationUrl(HttpSession session) throws Exception;
	
	// AccessToken 발급
	OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws Exception;	
	
	// Token을 이용하여 사용자 프로필 정보 가져오기
	String getUserProfile(HttpSession session, OAuth2AccessToken oauthToken) throws Exception;

}
