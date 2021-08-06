package com.ratryday.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import com.ratryday.services.CartServices;
import org.junit.jupiter.api.BeforeEach;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Category;
import com.ratryday.models.Product;
import org.junit.jupiter.api.Test;
import com.ratryday.models.Cart;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * {@link ProductController} responsible for products.
 * It gets one/list of products and sen them to product/products page.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    private Cart testCart;
    private Product testProduct;
    private Category testCategory;
    private CartEntry testCartEntry;
    private static final Integer TEST_ID = 0;
    private static final String ADDED = "added";
    private static final String MASSAGE = "massage";
    private static final String PRODUCT = "product";
    private static final String CATEGORY = "category";
    private static final String PRODUCT_ID = "productId";
    private static final String CATEGORY_ID = "categoryId";
    private static final String SLASH_PRODUCT = "/product";
    private static final String ALL_PRODUCTS = "allProducts";
    private static final String PRODUCT_PRODUCT = "product/product";
    private static final String PRODUCT_PRODUCTS = "product/products";
    private static final String MASSAGE_CONTENT = "There are no products here.";
    private static final String SLASH_PRODUCT_SLASH_PRODUCTS = "/product/products";

    @BeforeEach
    void setProductController() {
        testCart = new Cart();
        testCartEntry = new CartEntry();
        testCategory = new Category();
        testProduct = new Product();
        testProduct.setProductImage("Image");
        testProduct.setCategory(testCategory);
        testCartEntry.setProduct(testProduct);
        List<CartEntry> cartEntryList = new ArrayList<>();
        cartEntryList.add(testCartEntry);
        testCart.setCartEntry(cartEntryList);
    }

    private final MockMvc mockMvc;

    @Mock
    private HttpSession mockHttpSession;

    @MockBean
    private CategoryServices categoryServicesMockBean;

    @MockBean
    private ProductServices productServicesMockBean;

    @MockBean
    private CartServices cartServicesMockBean;

    @Autowired
    ProductControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /*
     * For productList() method I must check that addAttribute() add correct attribute to model.
     * Then this method has two situation:
     * - what addAttribute will add to model when productServices return empty list / list of products
     * - does it return the correct view name as a String
     * */

    @Test
    void productListWhenProductServicesReturnEmptyListOfProducts() throws Exception {
        when(categoryServicesMockBean.getCategory(TEST_ID)).thenReturn(testCategory);
        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID))).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID)));

        mockMvc.perform(get(SLASH_PRODUCT_SLASH_PRODUCTS)
                        .param(CATEGORY_ID, String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCTS))
                .andExpect(model().attribute(CATEGORY, categoryServicesMockBean.getCategory(TEST_ID)))
                .andExpect(model().attribute(MASSAGE, equalTo(MASSAGE_CONTENT)));
    }

    @Spy
    List<Product> spyListOfProducts = new ArrayList<>();

    {
        spyListOfProducts.add(new Product());
    }

    @Test
    void productListWhenProductServicesReturnListOfProducts() throws Exception {
        when(categoryServicesMockBean.getCategory(TEST_ID)).thenReturn(testCategory);

        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID))).thenReturn(spyListOfProducts);

        assertNotNull(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID)).get(0));

        mockMvc.perform(get(SLASH_PRODUCT_SLASH_PRODUCTS).param(CATEGORY_ID, String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCTS))
                .andExpect(model().attribute(CATEGORY, categoryServicesMockBean.getCategory(TEST_ID)))
                .andExpect(model().attribute(ALL_PRODUCTS, productServicesMockBean
                        .getProductList(categoryServicesMockBean.getCategory(TEST_ID))));
    }

    /*
     * For product() method I must check that addAttribute() add correct attribute to model.
     * Then this method has few situation:
     * - when cart for user exist/not exist
     * - check does cart entry have Product with productId
     * - does it return the correct view name as a String
     * */

    @Test
    void productWhenCartDoesNotExist() throws Exception {
        Product product = new Product();
        product.setCategory(testCategory);
        when(productServicesMockBean.getProduct(TEST_ID)).thenReturn(product);

        // Check that cartServices.getCart() return null
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(null);
        assertNull(cartServicesMockBean.getCart(mockHttpSession));

        mockMvc.perform(get(SLASH_PRODUCT)
                        .param(PRODUCT_ID, String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID)));
    }


    @Test
    void productWhenCartExistAndCartEntryDoesNotHaveProductWithProductId() throws Exception {
        Product product = new Product();
        product.setCategory(testCategory);
        when(productServicesMockBean.getProduct(TEST_ID)).thenReturn(product);

        List<CartEntry> cartEntryArrayList = new ArrayList<>();
        CartEntry cartE = new CartEntry();
        cartE.setProduct(testProduct);
        cartE.getProduct().setProductId(1);
        cartEntryArrayList.add(cartE);

        Cart cart = new Cart();
        cart.setCartEntry(cartEntryArrayList);

        // Check that cartServices.getCart() return Cart
        when(cartServicesMockBean.getCart(mockHttpSession))
                .thenReturn(cart);
        assertNotNull(cartServicesMockBean.getCart(mockHttpSession));

        // If doesn't fiend product with the same product id in cart entry return false
        List<CartEntry> cartEntryList = cartServicesMockBean.getCart(mockHttpSession).getCartEntry();
        assertFalse(cartEntryList.stream().anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == TEST_ID));

        mockMvc.perform(get(SLASH_PRODUCT)
                        .param(PRODUCT_ID, String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID)));
    }

    @Test
    void productWhenCartExistAndCartEntryHaveProductWithProductId() throws Exception {
        testProduct.setCategory(testCategory);
        when(productServicesMockBean.getProduct(TEST_ID)).thenReturn(testProduct);

        List<CartEntry> cartEntryArrayList = new ArrayList<>();
        CartEntry cartE = new CartEntry();
        cartE.setProduct(testProduct);
        cartEntryArrayList.add(cartE);

        Cart cart = new Cart();
        cart.setCartEntry(cartEntryArrayList);

        // Check that cartServices.getCart() return Cart
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(cart);

        System.out.println(cartServicesMockBean.getCart(mockHttpSession) != null);

        assertNotNull(cartServicesMockBean.getCart(mockHttpSession));

        // If fiend product with the same product id in cart entry return true
        List<CartEntry> cartEntryList = cartServicesMockBean.getCart(mockHttpSession).getCartEntry();
        assertTrue(cartEntryList.stream().anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == TEST_ID));

        mockMvc.perform(get(SLASH_PRODUCT).param(PRODUCT_ID, String.valueOf(TEST_ID)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(ADDED, ADDED))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID)));
    }
}
