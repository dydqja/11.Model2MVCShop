package com.model2.mvc.service.naver;




import com.model2.mvc.service.domain.NaverUser;

public interface NaverUserService {
	
	// ������� ������ DB�� �ִ��� Ȯ��
	public NaverUser checkUser(String id) throws Exception;
	
	// ����� ���� DB �߰�
	public void addUser(NaverUser naverUser) throws Exception;	

}
