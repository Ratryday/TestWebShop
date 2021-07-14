package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int phoneNumber;
    private String mailAddress;
    private String customerName;
    private String customerSurname;

    @OneToMany(mappedBy = "order")
    private List<CartEntry> cartEntryList;

}
