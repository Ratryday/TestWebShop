package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements java.io.Serializable {

    @Id
    @Column(name = "productId", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;

    @NotNull(message = "Product price should not be empty")
    @DecimalMin(value = "0.00", inclusive = false, message = "Product price should be valid")
    private BigDecimal productPrice;

    @NotEmpty(message = "Product name should not be empty")
    private String productName;

    private String productImage;

    @NotEmpty(message = "Product description should not be empty")
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(targetEntity = CartEntry.class, mappedBy = "product")
    private List<CartEntry> cartEntry;

    public Product(BigDecimal productPrice, String productName, String productImage, String productDescription, Category category) {
        this.productPrice = productPrice;
        this.productName = productName;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.category = category;
    }

    public Product() {
    }

    public Product(Category category) {
        this.category = category;
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
