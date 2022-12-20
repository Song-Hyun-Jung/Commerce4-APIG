package com.digital4.schema;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PurchaseResult {//구매 결과를 보여주기 위한 클래스
	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes ="purchaseID")
	long purchaseId;
	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes = "prepurchaseID")
	long orderId;
	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes = "personID")
	long personId;
	List<Cart> purchaseProductList = new ArrayList<>();
	@ApiModelProperty(required = false, position = 1, example = "1000", dataType = "long", notes = "price")
	long totalPrice;
	
	
	
	public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public List<Cart> getPurchaseProductList() {
		return purchaseProductList;
	}
	public void setPurchaseProductList(List<Cart> purchaseProductList) {
		this.purchaseProductList = purchaseProductList;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}

	
	
	
	
}
