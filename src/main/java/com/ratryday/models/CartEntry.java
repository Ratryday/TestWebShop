package com.ratryday.models;

import javax.persistence.*;

@Entity
public class CartEntry {

    @Id
    @Column(name = "cartEntryId", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartEntryId;
    private int productCount;

    @ManyToOne(targetEntity = Cart.class, cascade=CascadeType.ALL)
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

    public int getCartEntryId() {
        return cartEntryId;
    }

    public void setCartEntryId(int cartEntryId) {
        this.cartEntryId = cartEntryId;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
