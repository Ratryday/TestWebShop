package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private int phoneNumber;
    private String mailAddress;
    private String customerName;
    private String customerSurname;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<CartEntry> cartEntryList;

    @Override
    public String toString() {
        return "Order{" +
                ", phoneNumber=" + phoneNumber +
                ", mailAddress='" + mailAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSurname='" + customerSurname + '\'' +
                '}';
    }
}
