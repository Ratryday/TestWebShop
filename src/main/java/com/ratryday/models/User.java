package com.ratryday.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @NotEmpty(message = "User name should not be empty")
    private String userName;

    @NotEmpty(message = "User surname should not be empty")
    private String userSurname;

    @NotEmpty(message = "User mail should not be empty")
    @Email(message = "User mail should be valid")
    private String userMail;

    @NotEmpty(message = "Phone number should not be empty")
    private String userPhoneNumber;

    @NotEmpty(message = "User password should not be empty")
    private String userPassword;

    public User() {
    }
}
