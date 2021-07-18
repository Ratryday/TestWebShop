package com.ratryday.controllers;

import com.ratryday.services.CartServices;
import com.ratryday.services.OrderServices;
import com.ratryday.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.models.Category;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AdminController {

    private final CategoryServices categoryServices;
    private final ProductServices productServices;
    private final OrderServices orderServices;
    private final CartServices cartServices;

    @Autowired
    public AdminController(CategoryServices categoryServices, ProductServices productServices, OrderServices orderServices, CartServices cartServices) {
        this.categoryServices = categoryServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.cartServices = cartServices;
    }

    @PostMapping("/login")
    public String adminSingIn(Model model) {
        if (CollectionUtils.isEmpty(categoryServices.getCategoryList())) {
            model.addAttribute("massage", "There are no categories here.");
            return "admin";
        }
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin";
    }

    @GetMapping("/admin")
    public String admin() {

        return "login";
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

    @GetMapping("/edit")
    public String editGetCategory(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        return "/editcategory";
    }

    @PostMapping("/edit")
    public String editPostCategory(@RequestParam String categoryName, @RequestParam int categoryId, Model model) {
        categoryServices.update(new Category(categoryId, categoryName));
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin";
    }

    @PostMapping("delete")
    public String deleteCategory(@RequestParam int categoryId, Model model) {
        categoryServices.delete(categoryId);
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin";
    }

    @GetMapping("/adminproducts")
    public String productList(@RequestParam int categoryId, Model model) {
        return prepareForAdminProductsPage(categoryId, model);
    }

    @GetMapping("/addproduct")
    public String addGetProduct(@RequestParam int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "/addproduct";
    }

    @PostMapping("/addproduct")
    public String addPostProduct(@RequestParam int categoryId, Model model, @RequestParam MultipartFile imageFile,
                                 @RequestParam String productName, @RequestParam double productPrice,
                                 @RequestParam String productDescription) {
        try {
            productServices
                    .create(productPrice, productName, imageFile, productDescription, categoryServices.getCategory(categoryId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prepareForAdminProductsPage(categoryId, model);
    }

    @GetMapping("/editproduct")
    public String editGetProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("product", productServices.getProduct(productId));
        return "/editproduct";
    }

    @PostMapping("/editproduct")
    public String editPostProduct(@RequestParam int categoryId, Model model, @RequestParam MultipartFile imageFile,
                                  @RequestParam String productName, @RequestParam double productPrice,
                                  @RequestParam String productDescription, @RequestParam int productId) {
        try {
            System.out.println("Admin Controller");
            productServices
                    .update(productPrice, productName, imageFile, productDescription,
                            categoryServices.getCategory(categoryId), productId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prepareForAdminProductsPage(categoryId, model);
    }

    @PostMapping("/deleteproduct")
    public String deleteProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        productServices.delete(productId);
        return prepareForAdminProductsPage(categoryId, model);
    }

    @GetMapping("/adminorders")
    public String orders(Model model) {
        model.addAttribute("allOrders", orderServices.getOrderList());
        return "/adminorders";
    }

    @GetMapping("/admincart")
    public String orderCart(@RequestParam int cartId, Model model) {
        model.addAttribute("cart", cartServices.getCart(cartId));
        return "adminordercart";
    }

    private String prepareForAdminProductsPage(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute("massage", "There are no products here.");
            return "adminproducts";
        }
        model.addAttribute("allProducts", productServices.getProductList(categoryServices.getCategory(categoryId)));
        return "/adminproducts";
    }

}
