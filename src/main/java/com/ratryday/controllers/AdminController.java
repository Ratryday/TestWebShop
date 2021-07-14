package com.ratryday.controllers;

import com.ratryday.services.CategoryServices;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final CategoryServices categoryServices;

    @Autowired
    public AdminController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @GetMapping("/admin")
    public String adminSingIn(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("allCategory", "There are no categories here.");
            return "admin";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin";
    }

    @PostMapping("/login")
    public String admin() {
        return "admin";
    }

    @GetMapping("/addcategory")
    public String addCategory() {
        return "/addcategory";
    }

    @PostMapping("/addcategory")
    public String saveCategory(@RequestParam("categoryName") String categoryName, Model model) {
        categoryServices.create(categoryName);
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "/admin";
    }
}
