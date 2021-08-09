package com.ratryday.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import com.ratryday.services.CartServices;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static com.ratryday.controllers.Constants.*;

@Controller
@Transactional
@RequestMapping(SLASH_PRODUCT)
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

    @GetMapping(SLASH_PRODUCTS)
    public String productList(@RequestParam(CATEGORY_ID) int categoryId, Model model) {
        model.addAttribute(CATEGORY, categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute(MASSAGE, String.format(MASSAGE_CONTENT, PRODUCTS));
            return PRODUCT_SLASH_PRODUCTS;
        }
        model.addAttribute(ALL_PRODUCTS, productServices.getProductList(categoryServices.getCategory(categoryId)));
        return PRODUCT_SLASH_PRODUCTS;
    }

    @GetMapping()
    public String product(@RequestParam(PRODUCT_ID) int productId, Model model, HttpSession httpSession) {
        if (cartServices.getCart(httpSession) != null) {
            if (cartServices.getCart(httpSession).getCartEntry().stream()
                    .anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == productId)) {
                model.addAttribute(ADDED, ADDED);
            }
        }
        model.addAttribute(PRODUCT, productServices.getProduct(productId));
        return PRODUCT_SLASH_PRODUCTS;
    }

}
