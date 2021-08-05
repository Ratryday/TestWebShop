package com.ratryday.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import com.ratryday.services.CategoryServices;
import org.springframework.ui.Model;

@Controller
@Transactional
public class IndexController {

    private final CategoryServices categoryServices;

    @Autowired
    public IndexController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @GetMapping
    public String index(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("massage", "There are no categories here.");
            return "index";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "index";
    }
}
