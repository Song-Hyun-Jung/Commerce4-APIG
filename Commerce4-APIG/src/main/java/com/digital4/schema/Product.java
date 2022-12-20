package com.digital4.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Product {

	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long")
	private long productId;
	
	@ApiModelProperty(required = true, position = 2, example = "1", dataType = "long")
	private long categoryId;
	
	@ApiModelProperty(required = true, position = 4, example = "10000", dataType = "long")
	private long price;
	
	@ApiModelProperty(required = true, position = 5, example = "상품명", dataType = "string")
	private String productName;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}