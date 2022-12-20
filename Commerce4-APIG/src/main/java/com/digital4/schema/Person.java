package com.digital4.schema;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Person {
	@ApiModelProperty(required = false, position = 1, example = "1111", dataType = "long", notes = "고객ID")
	private long personId;
	
	@ApiModelProperty(required = true, position = 2, example = "고객명", dataType = "string", notes = "고객명")
	private String personName;

	@ApiModelProperty(required = true, position = 3, example = "남/여", dataType = "string", notes = "성별")
	private String gender;
	
	@ApiModelProperty(required = true, position = 4, example = "[{\"phoneId\":11,\"phoneNumber\":\"010-5778-4305\"}]", dataType = "array", notes="전화번호 목록")
	private List<Phone> phoneList;
	
	@ApiModelProperty(required = true, position = 5, example = "[{\"addressId\":11,\"addressDetail\":\"서울 중랑구 숙선옹주로 45\"}]", dataType = "array", notes="주소목록")
	private List<Address> addressList;
	
	@ApiModelProperty(required = true, position = 6, example = "id", dataType = "string", notes = "로그인ID")
	private String loginId;
	
	@ApiModelProperty(required = true, position = 7, example = "password", dataType = "string", notes = "비밀번호")
	private String password;
	
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
