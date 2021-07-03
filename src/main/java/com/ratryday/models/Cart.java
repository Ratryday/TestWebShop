package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Cart {

    private Map<Product, Integer> productList;

}
