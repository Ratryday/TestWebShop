package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class CartEntry {

    @Id
    @Column(name = "cartEntryId", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartEntryId;

    @NotNull(message = "Choose how many products you want to buy")
    @Min(value = 1, message = "You must choose at least one product")
    private int productCount;

    @ManyToOne(targetEntity = Cart.class)
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumns(
            {
                    @JoinColumn(name = "productId", referencedColumnName = "productId"),
                    @JoinColumn(name = "productName", referencedColumnName = "productName")
            }
    )
    private Product product;

    public CartEntry(int productCount, Cart cart, Product product) {
        this.productCount = productCount;
        this.cart = cart;
        this.product = product;
    }

    public CartEntry() {
    }

    @Override
    public String toString() {
        return "CartEntry{" +
                "cartEntryId=" + cartEntryId +
                ", productCount=" + productCount +
                ", product=" + product +
                '}';
    }
}
