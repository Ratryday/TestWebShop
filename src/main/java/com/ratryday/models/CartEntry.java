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
    private Long cartEntryId;

    private int productCount;

    @ManyToOne()
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "id")
    private Order order;

}
