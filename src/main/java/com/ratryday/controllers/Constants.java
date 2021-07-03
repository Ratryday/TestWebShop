package com.ratryday.controllers;

public class Constants {

    // Category SQL statement
    public static final String SELECT_CATEGORY = "SELECT * FROM category";
    public static final String INSERT_CATEGORY = "INSERT INTO category (name) Values (?)";
    public static final String DELETE_CATEGORY = "DELETE FROM category WHERE categoryId = ?";
    public static final String UPDATE_CATEGORY = "UPDATE category SET name = ? WHERE categoryId = ?";
    public static final String SELECT_ONE_CATEGORY = "SELECT * FROM category WHERE categoryId=?";

}
