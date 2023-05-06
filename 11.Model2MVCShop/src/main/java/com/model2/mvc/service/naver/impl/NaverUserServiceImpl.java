package com.model2.mvc.service.naver.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.NaverUser;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.naver.NaverUserDao;
import com.model2.mvc.service.naver.NaverUserService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.UserDao;;



@Service("naverUserServiceImpl")
public class NaverUserServiceImpl implements NaverUserService{
	
	///Field
	@Autowired
	@Qualifier("naverUserDaoImpl")
	private NaverUserDao naverUserDao;
	public void setNaverUserDao(NaverUserDao naverUserDao) {
		this.naverUserDao = naverUserDao;
	}
	
	///Constructor
	public NaverUserServiceImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public NaverUser checkUser(String id) throws Exception {		
		
		return naverUserDao.checkUser(id);
	}
	
	public void addUser(NaverUser naverUser) throws Exception {
		
		naverUserDao.addUser(naverUser);
		
	}
	
}