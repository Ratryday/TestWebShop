package com.ratryday.controllers;

import com.ratryday.dao.CategoryDao;
import com.ratryday.dao.CategoryDaoImpl;
import com.ratryday.dao.ProductDao;
import com.ratryday.dao.ProductDaoImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ProductController {

    private CategoryDao categoryDao = new CategoryDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("allCategory", categoryDao.select());
        return "index";
    }

    @GetMapping("/products")
    public String productList(@RequestParam("categoryId") int categoryId, Model model) {
        model.addAttribute("allProducts" , productDao.select(categoryId));
        model.addAttribute("category", categoryDao.selectOne(categoryId));
        return "products";
    }

    @GetMapping("/product")
    public String product(@RequestParam("productId") int productId, Model model) {

        return "product";
    }

}
