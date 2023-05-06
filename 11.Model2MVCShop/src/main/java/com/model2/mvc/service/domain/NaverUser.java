package com.model2.mvc.service.domain;

public class NaverUser {
	
	private String id;
	private String birthday;
	private String gender;
	private String birthyear;
	private String mobile;
	private String name;
	private String age;
	private String email;
	

	public NaverUser() {
		
	}


	public String getId() {
		return id;
	}


	public String getBirthday() {
		return birthday;
	}


	public String getGender() {
		return gender;
	}


	public String getBirthyear() {
		return birthyear;
	}


	public String getMobile() {
		return mobile;
	}


	public String getName() {
		return name;
	}


	public String getAge() {
		return age;
	}


	public String getEmail() {
		return email;
	}


	public NaverUser setId(String id) {
		this.id = id;
		return this;
	}


	public NaverUser setBirthday(String birthday) {
		this.birthday = birthday;
		return this;
	}


	public NaverUser setGender(String gender) {
		this.gender = gender;
		return this;
	}


	public NaverUser setBirthyear(String birthyear) {
		this.birthyear = birthyear;
		return this;
	}


	public NaverUser setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}


	public NaverUser setName(String name) {
		this.name = name;
		return this;
	}


	public NaverUser setAge(String age) {
		this.age = age;
		return this;
	}


	public NaverUser setEmail(String email) {
		this.email = email;
		return this;
	}


	@Override
	public String toString() {
		return "NaverUser : [id=" + id + ", birthday=" + birthday + ", gender=" + gender + ", birthyear=" + birthyear
				+ ", mobile=" + mobile + ", name=" + name + ", age=" + age + ", email=" + email + "]";
	}	

}
