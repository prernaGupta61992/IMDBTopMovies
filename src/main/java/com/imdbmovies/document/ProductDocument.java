package com.imdbmovies.document;

import lombok.Data;

@Data
public class ProductDocument {
	
	private String ProductName;
	private int count;
	
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
