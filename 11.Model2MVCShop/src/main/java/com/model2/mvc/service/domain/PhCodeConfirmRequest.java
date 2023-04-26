package com.model2.mvc.service.domain;




public class PhCodeConfirmRequest {
    private String phCodeConfirm;
    private String smsConfirmNum;
    
	
	public PhCodeConfirmRequest() {
		
	}


	public String getPhCodeConfirm() {
		return phCodeConfirm;
	}


	public void setPhCodeConfirm(String phCodeConfirm) {
		this.phCodeConfirm = phCodeConfirm;
	}


	public String getSmsConfirmNum() {
		return smsConfirmNum;
	}


	public void setSmsConfirmNum(String smsConfirmNum) {
		this.smsConfirmNum = smsConfirmNum;
	}

	
}