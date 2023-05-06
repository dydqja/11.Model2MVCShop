package com.model2.mvc.service.naver.impl;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import com.model2.mvc.service.domain.NaverUser;

import com.model2.mvc.service.naver.NaverUserDao;



//==> ȸ������ DAO CRUD ����
@Repository("naverUserDaoImpl")
public class NaverUserDaoImpl implements NaverUserDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public NaverUserDaoImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public NaverUser checkUser(String id) throws Exception {
		System.out.println("NaverUserDaoImpe���� checkUser �����.");				
		
		return sqlSession.selectOne("NaverUserMapper.checkUser", id);		
	}
	
	public void addUser(NaverUser naverUser) throws Exception {
		System.out.println("NaverUserDaoImpe���� addUser �����.");
		
		sqlSession.insert("NaverUserMapper.addUser", naverUser);
		
	}	

}