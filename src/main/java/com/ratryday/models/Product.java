package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private int productId;
    private int categoryId;
    private int productPrice;
    private String productName;
    private String productImage;
    private String productDescription;

    public Product() {
    }

    public Product(int productId, int categoryId, int productPrice, String productName, String productImage, String productDescription) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "Product{productName = " + productName + ", productPrice = " + productPrice + '}';
    }
}
