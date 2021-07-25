package com.ratryday.models;

import javax.persistence.*;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    private String userId;

    @OneToMany(targetEntity = CartEntry.class, mappedBy = "cart")
    private List<CartEntry> cartEntry;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Cart(String userId) {
        this.userId = userId;
    }

    public Cart() {

    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                '}';
    }
}
