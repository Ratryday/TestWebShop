package com.ratryday.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import org.apache.commons.lang3.StringUtils;
import com.ratryday.services.OrderServices;
import com.ratryday.services.CartServices;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Category;
import org.springframework.ui.Model;
import com.ratryday.models.Product;

import java.util.List;
import java.io.IOException;
import javax.validation.Valid;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final CategoryServices categoryServices;
    private final ProductServices productServices;
    private final OrderServices orderServices;
    private final HttpServletRequest request;
    private final CartServices cartServices;

    @Autowired
    public AdminController(CategoryServices categoryServices, ProductServices productServices,
                           OrderServices orderServices, HttpServletRequest request, CartServices cartServices) {
        this.categoryServices = categoryServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.cartServices = cartServices;
        this.request = request;
    }

    // Admin layer
    @PostMapping("/login")
    public String adminSingIn(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("massage", "There are no categories here.");
            return "admin/admin";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin/admin";
    }

    @PostMapping("/logout")
    public String logout() {
        return "index";
    }

    @GetMapping()
    public String adminLogin() {
        return "admin/login";
    }


    // Category layer
    @GetMapping("/category/new")
    public String newCategory(@ModelAttribute("category") Category category) {
        return "admin/category/create";
    }

    @PostMapping("/category")
    public String createCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (categoryServices.isExist(category.getCategoryName())) {
            bindingResult.addError(new FieldError("categoryName", "categoryName",
                    "Category with the same name already exists"));
        }
        if (bindingResult.hasErrors()) {
            return "admin/category/create";
        }
        if (categoryServices.create(category)) {
            return "forward:/admin";
        }
        bindingResult.addError(new FieldError("categoryCreation", "categoryCreation", "Category has not created"));
        return "admin/category/create";
    }

    @GetMapping("/category/edit")
    public String editCategory(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        return "admin/category/edit";
    }

    @PostMapping("/category/edit")
    public String updateCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult, Model model) {
        if (!StringUtils.isEmpty(category.getCategoryName())) {
            if (!categoryServices.getCategory(category.getCategoryId()).getCategoryName().equals(category.getCategoryName())) {
                if (categoryServices.isExist(category.getCategoryName())) {
                    bindingResult.addError(new FieldError("categoryName", "categoryName",
                            "Category with the same name already exists"));
                }
            }
        }
        if (bindingResult.hasErrors()) {
            return "admin/category/edit";
        }
        if (categoryServices.update(category)) {
            return "forward:/admin";
        }
        model.addAttribute("massage", "Category wasn't be updated");
        return "admin/category/edit";
    }

    @PostMapping("/category/delete")
    public String deleteCategory(@RequestParam int categoryId, Model model) {
        if (categoryServices.delete(categoryId)) {
            return "forward:/admin";
        }
        model.addAttribute("massage", "Category doesn't deleted");
        return "admin/admin";
    }


    // Product layer
    @GetMapping("/product/products")
    public String productList(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute("massage", "There are no products here.");
            return "admin/product/products";
        }
        model.addAttribute("allProducts", productServices.getProductList(categoryServices.getCategory(categoryId)));
        return "admin/product/products";
    }

    @GetMapping("/product/new")
    public String newProduct(@ModelAttribute("product") Product product, @RequestParam int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "admin/product/create";
    }

    @PostMapping("/product")
    public String createProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                @RequestParam MultipartFile imageFile, @RequestParam int categoryId, Model model) {
        if (imageFile.isEmpty()) {
            bindingResult.addError(new FieldError("productImage", "productImage",
                    "You should chose image"));
        }
        if (productServices.isExist(product.getProductName())) {
            bindingResult.addError(new FieldError("productName", "productName",
                    "Product with the same name already exists"));
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryId", categoryId);
            return "admin/product/create";
        }
        try {
            productServices.create(product, imageFile, categoryServices.getCategory(categoryId),
                    request.getServletContext().getRealPath("images/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("categoryId", categoryId);
        return "redirect:/admin/product/products";
    }

    @GetMapping("/product/edit")
    public String editProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        model.addAttribute("product", productServices.getProduct(productId));
        return "admin/product/edit";
    }

    @PostMapping("/product/edit")
    public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                @RequestParam MultipartFile imageFile, @RequestParam int categoryId, Model model) {
        if (!StringUtils.isEmpty(product.getProductName())) {
            if (!productServices.getProduct(product.getProductId()).getProductName().equals(product.getProductName())) {
                if (productServices.isExist(product.getProductName())) {
                    bindingResult.addError(new FieldError("productName", "productName",
                            "Product with the same name already exists"));
                }
            }
        }
        if (bindingResult.hasErrors()) {
            product.setProductImage(productServices.getProduct(product.getProductId()).getProductImage());
            model.addAttribute("product", product);
            model.addAttribute("category", categoryServices.getCategory(categoryId));
            return "admin/product/edit";
        }
        try {
            productServices.update(product, imageFile, categoryServices.getCategory(categoryId),
                    request.getServletContext().getRealPath("images/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("categoryId", categoryId);
        return "redirect:/admin/product/products";
    }

    @DeleteMapping("/product/delete")
    public String deleteProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        productServices.delete(productId);
        model.addAttribute("categoryId", categoryId);
        return "redirect:/admin/product/products";
    }


    // Cart layer
    @GetMapping("/cart/orders")
    public String orders(Model model) {
        model.addAttribute("allOrders", orderServices.getOrderList());
        return "admin/cart/orders";
    }

    @GetMapping("/cart/cartEntry")
    public String orderCart(@RequestParam int cartId, Model model) {
        List<CartEntry> cartEntryList = cartServices.getCart(cartServices.getCart(cartId).getUserId()).getCartEntry();
        model.addAttribute("cart", cartEntryList);
        return "admin/cart/cartEntry";
    }

    @DeleteMapping("/cart/delete")
    public String deleteCartEntryProduct(@RequestParam int productId, @RequestParam int cartId, Model model,
                                         HttpSession httpSession) {
        if (cartServices.deleteCartEntry(productServices.getProduct(productId), httpSession)) {
            model.addAttribute("cart", cartServices.getCart(cartId));
            return "/admin/cart/cartEntry";
        }
        model.addAttribute("message", "Fail deleting");
        return "/admin/cart/cartEntry";
    }

}

