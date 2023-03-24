package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==>상품관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;			
	//setter Method 구현 X
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;	
	
//	 상품등록으로는 ajax 안쓸것같음
	@RequestMapping( value="json/addProduct", method=RequestMethod.POST)
	public void addProduct( @RequestBody Product product, Model model, HttpServletRequest request ) throws Exception {
		
		
		System.out.println("/product/json/addProduct :: POST");
		//Business Logic
		productService.addProduct(product);
		//Model 과 View 연결
//		System.out.println(request.getParameter("menu"));
//		model.addAttribute("product",product);
//		model.addAttribute("menu",request.getParameter("menu"));
		
//		return "forward:/product/readProduct.jsp";		
	}
	
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo ) throws Exception {
		
		System.out.println("/product/json/getProduct :: GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		//Model 과 View 연결
		System.out.println("----------------");
		System.out.println(product);
//		model.addAttribute("product",product);		
//		model.addAttribute("menu",request.getParameter("menu"));
		
//		return "forward:/product/updateProduct.jsp";
		return productService.getProduct(prodNo);
	}
	
	//AutoComplete 추가부분
	@RequestMapping( value="/json/autoComplete", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> autoComplete(@RequestParam Map<String, Object> paramMap) throws Exception {
		
		System.out.println("/product/json/autoComplete :: POST");
		
		List<Map<String,Object>> acList = productService.autoComplete(paramMap);
		paramMap.put("acList", acList);
		
		return paramMap;
	}
	
	/*
	@RequestMapping("listProduct")
	public String listProduct( @ModelAttribute("search") Search search, HttpServletRequest request, Model model) throws Exception {
		System.out.println("-------------------------------------");
		System.out.println(request.getParameter("menu"));
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		//Business logic 수행
		Map<String, Object> map=productService.getList(search);
		
		Page resultPage	= new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		//Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu",request.getParameter("menu"));
//		model.addAttribute("uri",request.getRequestURI());	==> pageNavigator if문 돌려보기 실패
		
		return "forward:/product/listProduct.jsp";
	}
	*/
	/*
	@RequestMapping("updateProduct")
	public String updateProduct( @ModelAttribute("product") Product product, HttpServletRequest request) throws Exception {
		
		System.out.println("/product/updateProduct");
		//Business logic
		productService.updateProduct(product);		
		
//		model.addAttribute("product", product);		
		
		return "forward:/product/getProduct";
	}
	
	@RequestMapping("updateProductView")
	public String updateProductView( @RequestParam("prodNo") int prodNo, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/product/updateProductView");
		//Business logic
		Product product = productService.getProduct(prodNo);
		//Model 과 View 연결
		model.addAttribute("product",product);
		model.addAttribute("menu",request.getParameter("menu"));		
		
		return "forward:/product/updateProductView.jsp";		
	}
	*/
}
