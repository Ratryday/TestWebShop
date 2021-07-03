package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private int id;
    private int phoneNumber;
    private String mailAddress;
    private String customerName;
    private String customerSurname;

    public Order(int id, int phoneNumber, String mailAddress, String customerName, String customerSurname) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
    }
}
