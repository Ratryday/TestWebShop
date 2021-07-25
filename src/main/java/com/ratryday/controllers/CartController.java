package com.ratryday.controllers;

import com.ratryday.models.CartEntry;
import com.ratryday.services.ProductServices;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CartServices;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/cart")
public class CartController {

    private final CartServices cartServices;
    private final ProductServices productServices;

    @Autowired
    public CartController(CartServices cartServices, ProductServices productServices) {
        this.cartServices = cartServices;
        this.productServices = productServices;
    }

    @GetMapping()
    public String cart(Model model, HttpSession httpSession) {
        if (cartServices.getCart(httpSession) == null) {
            model.addAttribute("message", "You do not add anything");
            return "cart/cart";
        }
        if (CollectionUtils.isEmpty(cartServices.getCart(httpSession).getCartEntry())) {
            model.addAttribute("message", "You do not add anything");
            return "cart/cart";
        }
        List<CartEntry> cartEntryList = cartServices.getCart(httpSession).getCartEntry();
        BigDecimal totalProductPrice = BigDecimal.ZERO;
        for (CartEntry cartEntry : cartEntryList) {
            BigDecimal productPrice = cartEntry.getProduct().getProductPrice();
            int productCount = cartEntry.getProductCount();
            totalProductPrice = totalProductPrice.add(productPrice.multiply(new BigDecimal(productCount)));
        }
        model.addAttribute("totalProductPrice", totalProductPrice);
        model.addAttribute("cart", cartEntryList);
        return "cart/cart";
    }

    @PostMapping("/new")
    public String addToCart(@RequestParam int productCount, @RequestParam int productId, Model model, HttpSession httpSession) {
        cartServices.create(productCount, productServices.getProduct(productId), httpSession);
        model.addAttribute("added", "added");
        model.addAttribute("product", productServices.getProduct(productId));
        return "product/product";
    }

    @PostMapping("/delete")
    public String clearCart(@RequestParam int productId, Model model, HttpSession httpSession) {
        if(cartServices.deleteCartEntry(productServices.getProduct(productId), httpSession)) {
            if (cartServices.getCart(httpSession) == null) {
                model.addAttribute("message", "You do not add anything");
                return "cart/cart";
            }
        }
        return "redirect:/cart";
    }

}
