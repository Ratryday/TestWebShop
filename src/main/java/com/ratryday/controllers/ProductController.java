package com.ratryday.controllers;

import com.ratryday.dao.ProductContainer;
import com.ratryday.models.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.ratryday.dao.CategoryContainer;
import org.springframework.ui.Model;

@Controller
public class ProductController {

    private CategoryContainer categoryContainer = new CategoryContainer();
    private ProductContainer productContainer = new ProductContainer();

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("allCategory", categoryContainer.categoryList);
        return "index";
    }

    @GetMapping("/products")
    public String productList(@RequestParam("category") String category, Model model) {
        model.addAttribute("allProducts" ,productContainer.getProductList(category));
        model.addAttribute("category", category);
        return "products";
    }

    @GetMapping("/product")
    public String product(@RequestParam("productId") String productId, Model model) {

        return "product";
    }

}
