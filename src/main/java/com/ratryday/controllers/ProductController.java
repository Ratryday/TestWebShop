package com.ratryday.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
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

    @GetMapping
    public String index(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("massage", "There are no categories here.");
            return "index";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "index";
    }

    @GetMapping("/product/products")
    public String productList(@RequestParam("categoryId") int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute("massage", "There are no products here.");
            return "product/products";
        }
        model.addAttribute("allProducts", productServices.getProductList(categoryServices.getCategory(categoryId)));
        return "product/products";
    }

    @GetMapping("/product")
    public String product(@RequestParam("productId") int productId, Model model) {
        model.addAttribute("product", productServices.getProduct(productId));
        return "product/product";
    }

}
