package com.model2.mvc.service.naver;




import com.model2.mvc.service.domain.NaverUser;




public interface NaverUserDao {	

	// SELECT ONE
	public NaverUser checkUser(String id) throws Exception ;
	
	// INSERT
	public void addUser(NaverUser naverUser) throws Exception ;
	
}