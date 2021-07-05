package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private int orderId;
    private int phoneNumber;
    private String mailAddress;
    private String customerName;
    private String customerSurname;

    public Order() {
    }

    public Order(int orderId, int phoneNumber, String mailAddress, String customerName, String customerSurname) {
        this.orderId = orderId;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
    }
}
