package com.ratryday.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import org.springframework.ui.Model;


@Controller
@Transactional
public class ProductController {

    private final CategoryServices categoryServices;
    private final ProductServices productServices;

    @Autowired
    public ProductController(CategoryServices categoryServices, ProductServices productServices) {
        this.categoryServices = categoryServices;
        this.productServices = productServices;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("allCategory", "There are no categories here.");
            return "index";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "index";
    }

    @GetMapping("/products")
    public String productList(@RequestParam("categoryId") int categoryId, Model model) {
        model.addAttribute("allProducts", productServices.getProductList(categoryId));
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        return "products";
    }

    @GetMapping("/product")
    public String product(@RequestParam("productId") int productId, Model model) {
        model.addAttribute("product", productServices.getProduct(productId));
        return "product";
    }

}
