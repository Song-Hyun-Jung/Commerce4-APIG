package com.digital4.schema;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Prepurchase { //주문 상세정보 입력을 위한 클래스

	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes = "prepurchaseID")
	private long orderId;
	
	@ApiModelProperty(required = false, position = 2, example = "1", dataType = "long", notes = "고객personID")
	private long personId;
	
	@ApiModelProperty(required = true, position = 3,  example = "{\"phoneId\":1,\"phoneNumber\":\"전화번호\"}", dataType = "object", notes="전화번호 정보")
	private Phone phone;
	
	@ApiModelProperty(required = true, position = 4,  example = "{\"addressId\":1,\"addressDetail\":\"주소\"}", dataType = "object", notes="주소 정보")
	private Address address;
	
	@ApiModelProperty(required = true, position = 5,  example = "[{\"cartId\":1,\"personId\":1, \"productId\":1, \"productQuantity\":1}]", dataType = "array", notes="구매할 상품")
	private List<Cart> cartList;

	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public List<Cart> getCartList() {
		return cartList;
	}
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
