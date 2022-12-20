package com.digital4.schema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Category {

	@ApiModelProperty(required = false, position = 1, example = "1", dataType = "long", notes = "상품 카테고리 ID")
	private long categoryId;
	
	@ApiModelProperty(required = true, position = 1, example = "카테고리명", dataType = "string")
	private String categoryName;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

}
