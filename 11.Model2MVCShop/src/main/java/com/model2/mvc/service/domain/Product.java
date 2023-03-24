package com.model2.mvc.service.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


//==>상품정보를 모델링(추상화/캡슐화)한 Bean
public class Product {
	
	//Field
	private int prodNo;
	private int price;
	private String fileName;
	private String manuDate;
	private String prodDetail;
	private String prodName;
	private String proTranCode;
	private Date regDate;
	// JSON ==> Domain Object  Binding을 위해 추가된 부분
	private String regDateString;
	// fileUpload 추가 부분(단일)
	private MultipartFile uploadFile;
	// fileUpload 추가 부분(다중)
	private List<MultipartFile> uploadFiles;
	
	
	
	//Constructor
	public Product(){
	}
	
	public String getProTranCode() {
		return proTranCode;
	}
	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getManuDate() {
		return manuDate;
	}
	public void setManuDate(String manuDate) {
		this.manuDate = manuDate.replaceAll("-","");
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProdDetail() {
		return prodDetail;
	}
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	// JSON ==> Domain Object  Binding을 위해 추가
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
		
		if(regDate !=null) {
			// JSON ==> Domain Object  Binding을 위해 추가된 부분
			this.setRegDateString( regDate.toString().split("-")[0]
													+"-"+ regDate.toString().split("-")[1]
													+"-"+ regDate.toString().split("-")[2] );
		}
	}
	public String getRegDateString() {
		return regDateString;
	}

	public void setRegDateString(String regDateString) {
		this.regDateString = regDateString;
	}

	@Override
	public String toString() {
		return "Product : [fileName]" + fileName
				+ "[manuDate]" + manuDate+ "[price]" + price + "[prodDetail]" + prodDetail
				+ "[prodName]" + prodName + "[prodNo]" + prodNo;
	}
	
	//fileUpload 추가부분(단일)
	public MultipartFile getUploadFile() {		 
		return uploadFile;
	}	
	
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;	
	}
	
	//fileUpload 추가부분(다중)
	public List<MultipartFile> getUploadFiles() {
		System.out.println("뭐가문제야ㅡㅡ11111"+uploadFiles);
		return uploadFiles;	
	}

	public void setUploadFiles(List<MultipartFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
		System.out.println("뭐가문제야ㅡㅡ"+uploadFiles);
	}	
	
}