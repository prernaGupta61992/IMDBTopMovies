package com.imdbmovies.document;

import lombok.Data;

@Data
public class ProductDocument {
    private String ProductName;
    private int count;

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(final String productName) {
        ProductName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

}
