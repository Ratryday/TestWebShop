package com.ratryday.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminSingIn(Model model) {

        return "login";
    }

    @PostMapping("/login")
    public String admin(Model model) {

        return "admin";
    }

}
