package com.ratryday.controllers;

public class Constants {

    // Category SQL statement
    public static final String SELECT_CATEGORY = "SELECT * FROM category";
    public static final String INSERT_CATEGORY = "INSERT INTO category (name) Values (?)";
    public static final String DELETE_CATEGORY = "DELETE FROM category WHERE categoryId = ?";
    public static final String UPDATE_CATEGORY = "UPDATE category SET name = ? WHERE categoryId = ?";
    public static final String SELECT_ONE_CATEGORY = "SELECT * FROM category WHERE categoryId=?";

    // Product SQL statement
    public static final String SELECT_PRODUCT = "SELECT * FROM product WHERE categoryId=?";
    public static final String SELECT_ONE_PRODUCT = "SELECT * FROM product WHERE categoryId=?";
    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE productId = ?";
    public static final String UPDATE_PRODUCT = "UPDATE product SET productDescription = ?, categoryId = ?," +
            " productPrice = ?, productName = ?, productImage = ? WHERE productId = ?";
    public static final String INSERT_PRODUCT = "INSERT INTO product (categoryId, productPrice, productName," +
            " productImage, productDescription) Values (?, ?, ?, ?, ?)";

    // Order SQL statement
    public static final String SELECT_ORDER = "SELECT * FROM order";
    public static final String INSERT_ORDER = "INSERT INTO order (phoneNumber, mailAddress, customerName, customerSurname)" +
            " Values (?, ?, ?, ?)";
    public static final String DELETE_ORDER = "DELETE FROM order WHERE orderId = ?";
    public static final String UPDATE_ORDER = "UPDATE order SET phoneNumber = ?, mailAddress = ?, customerName = ?," +
            " customerSurname = ? WHERE orderId = ?";
    public static final String SELECT_ONE_ORDER = "SELECT * FROM order WHERE orderId=?";

}
