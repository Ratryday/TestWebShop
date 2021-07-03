package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private int productId;
    private int productPrice;
    private String productName;
    private String productImage;
    private String productDescription;

    public Product(int productId, int productPrice, String productName, String productImage, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

}
