package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private Double productPrice;
    private String productName;
    private String productImage;
    private String productDescription;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<CartEntry> cartEntry;

    @Override
    public String toString() {
        return "Product{productName = " + productName + ", productPrice = " + productPrice + '}';
    }
}
