package com.model2.mvc.web.product;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==>상품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 X
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping( value="addProduct", method=RequestMethod.POST, consumes="multipart/form-data")
	public String addProduct( @ModelAttribute Product product, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/product/addProduct :: POST");
		
		//file upload
		String fileName = null;
		//upload 한 file의 이름을 가져온다.
		System.out.println(product);
		List<MultipartFile> uploadFiles = product.getUploadFiles();
		//upload 한 file의 이름이 empty가 아니라면 true / empty라면 false
		
		if (!uploadFiles.isEmpty()) {
			
			List<Map<String,String>> fileList = new ArrayList<>();			
			// file 저장위치 설정
			String filePath = "C:\\Users\\LG\\git\\10.Model2MVCShop-Ajax-\\10.Model2MVCShop(Ajax)\\src\\main\\webapp\\images\\uploadFiles";
			
			for(int i = 0; i<uploadFiles.size(); i++) {			
			
			// getOriginalFilename => form에서 직접 지정한 파일명 return
			String originalFileName = uploadFiles.get(i).getOriginalFilename();			
			// file 확장자명 구하는작업
			String ext = FilenameUtils.getExtension(originalFileName);			
			// UUID 구하는작업(고유id)
//			UUID uuid = UUID.randomUUID();
			// 파일이름 = 고유id.확장자
//			fileName = uuid.toString()+"."+ext;
			fileName = UUID.randomUUID().toString()+"."+ext;
			//고유id.확장자 map으로 넣기			
			Map<String,String> map = new HashMap<>();
			map.put("fileName", fileName);			
			fileList.add(map);			
			}			
			//transferTo => 업로드 한 파일 data를 지정한 파일에 저장한다.
			for (int i = 0; i<uploadFiles.size(); i++) {
				
				uploadFiles.get(i).transferTo(new File(filePath+"\\"+fileList.get(i).get("fileName")));			
			}
		}
		product.setFileName(fileName);
		
		//Business Logic
		productService.addProduct(product);
		
		//Model 과 View 연결
		System.out.println(request.getParameter("menu"));
		model.addAttribute("product",product);
		model.addAttribute("menu",request.getParameter("menu"));
		
		return "forward:/product/readProduct.jsp";
	}
	
	@RequestMapping( value="getProduct", method=RequestMethod.POST)
	public String getProduct( @RequestParam("prodNo") int prodNo, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/product/getProduct :: POST");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		//Model 과 View 연결
		System.out.println("----------------");
		System.out.println(product);
		model.addAttribute("product",product);		
		model.addAttribute("menu",request.getParameter("menu"));
		
		return "forward:/product/updateProduct.jsp";
	}
	
	@RequestMapping( value="listProduct")
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
	
	@RequestMapping( value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product, HttpServletRequest request) throws Exception {
		
		System.out.println("/product/updateProduct :: POST");
		
		//file upload
		String fileName = null;
		//upload 한 file의 이름을 가져온다.		
		List<MultipartFile> uploadFiles = product.getUploadFiles();
		//upload 한 file의 이름이 empty가 아니라면 true / empty라면 false
		if (!uploadFiles.isEmpty()) {
					
			List<Map<String,String>> fileList = new ArrayList<>();			
			// file 저장위치 설정
			String filePath = "C:\\work\\fileUpload";
					
			for(int i = 0; i<uploadFiles.size(); i++) {			
					
			// getOriginalFilename => form에서 직접 지정한 파일명 return
			String originalFileName = uploadFiles.get(i).getOriginalFilename();			
			// file 확장자명 구하는작업
			String ext = FilenameUtils.getExtension(originalFileName);			
			// UUID 구하는작업(고유id)
//			UUID uuid = UUID.randomUUID();
			// 파일이름 = 고유id.확장자
//			fileName = uuid.toString()+"."+ext;
			fileName = UUID.randomUUID().toString()+"."+ext;
			//고유id.확장자 map으로 넣기			
			Map<String,String> map = new HashMap<>();
			map.put("fileName", fileName);			
			fileList.add(map);			
			}			
			//transferTo => 업로드 한 파일 data를 지정한 파일에 저장한다.
			for (int i = 0; i<uploadFiles.size(); i++) {
						
				uploadFiles.get(i).transferTo(new File(filePath+"\\"+fileList.get(i).get("fileName")));			
			}
		}
		product.setFileName(fileName);
		
		//Business logic
		productService.updateProduct(product);		
		
//		model.addAttribute("product", product);		
		
		return "forward:/product/getProduct";
	}
	
	@RequestMapping( value="updateProduct", method=RequestMethod.GET)
	public String updateProduct( @RequestParam("prodNo") int prodNo, Model model, HttpServletRequest request ) throws Exception {
		
		System.out.println("/product/updateProduct :: GET");
		//Business logic
		Product product = productService.getProduct(prodNo);
		//Model 과 View 연결
		model.addAttribute("product",product);
		model.addAttribute("menu",request.getParameter("menu"));		
		
		return "forward:/product/updateProductView.jsp";		
	}

}
