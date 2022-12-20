package com.digital4.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Cart { //장바구니 
	
	@ApiModelProperty(required = false, position = 1, example = "111", dataType = "long", notes = "카트ID")
	private long cartId;
	@ApiModelProperty(required = false, position = 2, example = "111", dataType = "long", notes = "고객ID")
	private long personId;
	@ApiModelProperty(required = true, position = 3, example = "111", dataType = "long", notes = "상품ID")
	private long productId;
	@ApiModelProperty(required = true, position = 4, example = "담을 개수", dataType = "long", notes = "장바구니에 담을 상품의 개수")
	private long productQuantity;
	
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	public Cart() {}
	public Cart(long productId, long productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	
	
}
