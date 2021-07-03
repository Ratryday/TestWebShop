package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    private String customerSurname;
    private String customerName;
    private String mailAddress;
    private int phoneNumber;

    public Order(String customerSurname, String customerName, int phoneNumber, String mailAddress) {
        this.customerSurname = customerSurname;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
    }

}
