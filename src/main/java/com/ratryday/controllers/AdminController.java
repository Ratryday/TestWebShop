package com.ratryday.controllers;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import com.ratryday.services.OrderServices;
import com.ratryday.services.CartServices;
import com.ratryday.models.Category;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {

    private final CategoryServices categoryServices;
    private final ProductServices productServices;
    private final OrderServices orderServices;
    private final CartServices cartServices;

    @Autowired
    public AdminController(CategoryServices categoryServices, ProductServices productServices,
                           OrderServices orderServices, CartServices cartServices) {
        this.categoryServices = categoryServices;
        this.productServices = productServices;
        this.orderServices = orderServices;
        this.cartServices = cartServices;
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

    @GetMapping()
    public String admin() {
        return "admin/login";
    }



    // Category layer
    @GetMapping("/category/create")
    public String createCategory() {
        return "admin/category/create";
    }

    @PutMapping("/category/create")
    public String saveCategory(@RequestParam("categoryName") String categoryName, Model model) {
        categoryServices.create(categoryName);
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin/admin";
    }

    @GetMapping("/category/edit")
    public String editCategory(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        return "admin/category/edit";
    }

    @PatchMapping("/category/edit")
    public String updateCategory(@RequestParam String categoryName, @RequestParam int categoryId, Model model) {
        categoryServices.update(new Category(categoryId, categoryName));
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin/admin";
    }

    @DeleteMapping("/category/delete")
    public String deleteCategory(@RequestParam int categoryId, Model model) {
        categoryServices.delete(categoryId);
        model.addAttribute("allCategory", categoryServices.getCategoryList());
        return "admin/admin";
    }



    // Product layer
    @GetMapping("/product/products")
    public String productList(@RequestParam int categoryId, Model model) {
        return prepareForAdminProductsPage(categoryId, model);
    }

    @GetMapping("/product/create")
    public String createProduct(@RequestParam int categoryId, Model model) {
        model.addAttribute("categoryId", categoryId);
        return "admin/product/create";
    }

    @PutMapping("/product/create")
    public String saveProduct(@RequestParam int categoryId, Model model, @RequestParam MultipartFile imageFile,
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

    @GetMapping("/product/edit")
    public String editProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("product", productServices.getProduct(productId));
        return "admin/product/edit";
    }

    @PatchMapping("/product/edit")
    public String updateProduct(@RequestParam int categoryId, Model model, @RequestParam MultipartFile imageFile,
                                  @RequestParam String productName, @RequestParam double productPrice,
                                  @RequestParam String productDescription, @RequestParam int productId) {
        try {
            productServices.update(productPrice, productName, imageFile, productDescription,
                            categoryServices.getCategory(categoryId), productId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prepareForAdminProductsPage(categoryId, model);
    }

    @DeleteMapping("/product/delete")
    public String deleteProduct(@RequestParam int categoryId, @RequestParam int productId, Model model) {
        productServices.delete(productId);
        return prepareForAdminProductsPage(categoryId, model);
    }



    // Cart layer
    @GetMapping("/cart/orders")
    public String orders(Model model) {
        model.addAttribute("allOrders", orderServices.getOrderList());
        return "admin/cart/orders";
    }

    @GetMapping("/cart/cartEntry")
    public String orderCart(@RequestParam int cartId, Model model) {
        model.addAttribute("cart", cartServices.getCart(cartId));
        return "admin/cart/cartEntry";
    }

    @DeleteMapping("/cart/delete")
    public String deleteCartEntryProduct(@RequestParam int productId, @RequestParam int cartId, Model model,
                                         HttpSession httpSession) {
        if(cartServices.deleteCartEntry(productServices.getProduct(productId), httpSession)) {
            model.addAttribute("cart", cartServices.getCart(cartId));
            return "/admin/cart/cartEntry";
        }
        model.addAttribute("message", "Fail deleting");
        return "/admin/cart/cartEntry";
    }

    // Another methods
    private String prepareForAdminProductsPage(@RequestParam int categoryId, Model model) {
        model.addAttribute("category", categoryServices.getCategory(categoryId));
        if (CollectionUtils.isEmpty(productServices.getProductList(categoryServices.getCategory(categoryId)))) {
            model.addAttribute("massage", "There are no products here.");
            return "/admin/product/products";
        }
        model.addAttribute("allProducts", productServices.getProductList(categoryServices.getCategory(categoryId)));
        return "/admin/product/products";
    }

}

