package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.user.UserDao;


//==> ��ǰ���� DAO CRUD ����
@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao{
	
	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("ProductDaoImpl���� setsqlSession �����");
//		System.out.println("sqlSession ���� = "+ sqlSession);
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public ProductDaoImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public void addProduct(Product product) throws Exception {
		System.out.println("ProductDaoImpl���� addProduct �����");
//		System.out.println("product ���� = "+ product);
		sqlSession.insert("ProductMapper.addProduct", product);
		
	}		

	public Product getProduct(int prodNo) throws Exception {
		System.out.println("ProductDaoImpl���� getProduct �����");
//		System.out.println("prodNo ���� = "+ prodNo);
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}
		
	public void updateProduct(Product product) throws Exception {
		System.out.println("ProductDaoImpl���� updateProduct �����");
		System.out.println("product ���� = "+ product);
		sqlSession.update("ProductMapper.updateProduct", product);
	}
	
	public List<Product> getList(Search search) throws Exception {
		System.out.println("ProductDaoImpl���� getList �����");
		return sqlSession.selectList("ProductMapper.getList", search);
	}

	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception {
		System.out.println("ProductDaoImpl���� getTotalCount �����");
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}
	
	//AutoComplete �߰��κ�
	public List<Map<String,Object>> autoComplete(Map<String,Object> paramMap) throws Exception {
		System.out.println("ProductDaoImpl���� autoComplete �����");		
		return sqlSession.selectList("ProductMapper.getAutoComplete", paramMap);
	}
	
}