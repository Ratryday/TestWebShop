package com.ratryday.controllers;

import com.ratryday.models.User;
import com.ratryday.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("/user")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping()
    public String login() {
        return "/user/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute ("user") User user) {
        return "/user/registration";
    }
    @PostMapping("/new")
    public String createUser(@ModelAttribute ("user") @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "user/registration";
        }
        userServices.create(user);
        return "redirect:/product/products";
    }

}
