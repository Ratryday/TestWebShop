package com.ratryday.controllers;

import com.ratryday.services.CartServices;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;


@Controller
@Transactional
@RequestMapping("/product")
public class ProductController {

    private final CategoryServices categoryServices;
    private final ProductServices productServices;
    private final CartServices cartServices;

    @Autowired
    public ProductController(CategoryServices categoryServices, ProductServices productServices, CartServices cartServices) {
        this.categoryServices = categoryServices;
        this.productServices = productServices;
        this.cartServices = cartServices;
    }

    @GetMapping("/products")
    public String productList(@RequestParam("categoryId") int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute("massage", "There are no products here.");
            return "product/products";
        }
        model.addAttribute("allProducts", productServices.getProductList(categoryServices.getCategory(categoryId)));
        return "product/products";
    }

    @GetMapping()
    public String product(@RequestParam("productId") int productId, Model model, HttpSession httpSession) {
        System.out.println(cartServices.getCart(httpSession) != null);
        if (cartServices.getCart(httpSession) != null) {
            if (cartServices.getCart(httpSession).getCartEntry().stream()
                    .anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == productId)) {
                model.addAttribute("added", "added");
            }
        }
        model.addAttribute("product", productServices.getProduct(productId));
        System.out.println(productServices.getProduct(productId));
        return "product/product";
    }

}
