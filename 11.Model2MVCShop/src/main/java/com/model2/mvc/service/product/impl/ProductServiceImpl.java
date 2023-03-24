package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.UserDao;;


//==> ��ǰ���� ���� ����
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	///Method
	public void addProduct(Product product) throws Exception {
		System.out.println("ProductServiceImpl���� addProduct �����");
//		System.out.println("product ���� = "+ product);
		productDao.addProduct(product);
	}

	public Product getProduct(int prodNo) throws Exception {
		System.out.println("ProductServiceImpl���� getProduct �����");
//		System.out.println("prodNo ���� = "+ prodNo);
		return productDao.getProduct(prodNo);
	}
	
	public Map<String , Object > getList(Search search) throws Exception {
		System.out.println("ProductServiceImpl���� getProductList �����");
		System.out.println("search ���� =" +search);
		List<Product> list= productDao.getList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}

	public void updateProduct(Product product) throws Exception {
		System.out.println("ProductServiceImpl���� updateProduct �����");
		System.out.println("product ���� = "+ product);
		productDao.updateProduct(product);
	}
	
	//AutoComplete �߰��κ�
	public List<Map<String,Object>>autoComplete(Map<String,Object> paramMap) throws Exception {		
		return productDao.autoComplete(paramMap);
	}
	
	/* #######################################################################################
	public boolean checkDuplication(String userId) throws Exception {
		System.out.println("UserServiceImpl���� checkDuplication �����");
		boolean result=true;
		User user=userDao.getUser(userId);
		if(user != null) {
			result=false;
		}
		return result;		
	}
	########################################################### */

	
		
	
}