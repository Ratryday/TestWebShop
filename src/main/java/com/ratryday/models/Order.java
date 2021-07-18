package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private String phoneNumber;
    private String mailAddress;
    private String customerName;
    private String customerSurname;

    @OneToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSurname='" + customerSurname + '\'' +
                '}';
    }

    public Order(String phoneNumber, String mailAddress, String customerName, String customerSurname, Cart cart) {
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.cart = cart;
    }

    public Order() {
    }
}
