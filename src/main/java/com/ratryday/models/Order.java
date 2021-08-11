package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "Mail address should not be empty")
    @Email(message = "Email should be valid")
    private String mailAddress;

    @NotEmpty(message = "Your name should not be empty")
    private String customerName;

    @NotEmpty(message = "Your surname should not be empty")
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId() && Objects.equals(getPhoneNumber(), order.getPhoneNumber()) && Objects.equals(getMailAddress(), order.getMailAddress()) && Objects.equals(getCustomerName(), order.getCustomerName()) && Objects.equals(getCustomerSurname(), order.getCustomerSurname()) && Objects.equals(getCart(), order.getCart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getPhoneNumber(), getMailAddress(), getCustomerName(), getCustomerSurname(), getCart());
    }
}
