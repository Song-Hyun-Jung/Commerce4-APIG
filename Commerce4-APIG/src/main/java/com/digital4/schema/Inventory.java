package com.digital4.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Inventory {
	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes = "인벤토리 상품 ID")
	private long productId;
	@ApiModelProperty(required = true, position = 3, example = "1", dataType = "long", notes = "상품 수량")
	private long quantity;
	
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	
	
}
