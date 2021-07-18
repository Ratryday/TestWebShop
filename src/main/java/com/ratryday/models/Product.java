package com.ratryday.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

    @Id
    @Column(name = "productId", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    private Double productPrice;
    private String productName;
    private String productImage;
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(targetEntity = CartEntry.class, mappedBy = "product")
    private List<CartEntry> cartEntry;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CartEntry> getCartEntry() {
        return cartEntry;
    }

    public void setCartEntry(List<CartEntry> cartEntry) {
        this.cartEntry = cartEntry;
    }

    public Product(Double productPrice, String productName, String productImage, String productDescription, Category category) {
        this.productPrice = productPrice;
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.category = category;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +

                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", category=" + category +
                '}';
    }
}
