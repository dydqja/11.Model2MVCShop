package com.model2.mvc.service.naver;




import com.model2.mvc.service.domain.NaverUser;

public interface NaverUserService {
	
	// 사용자의 정보가 DB에 있는지 확인
	public NaverUser checkUser(String id) throws Exception;
	
	// 사용자 정보 DB 추가
	public void addUser(NaverUser naverUser) throws Exception;	

}
