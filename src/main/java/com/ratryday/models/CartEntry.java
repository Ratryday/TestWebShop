package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity

public class CartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private Product product;

    private int productCount;

}
