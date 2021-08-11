package com.ratryday.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ratryday.services.CategoryServices;
import com.ratryday.services.ProductServices;
import com.ratryday.services.CartServices;
import org.junit.jupiter.api.BeforeEach;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Category;
import org.springframework.ui.Model;
import com.ratryday.models.Product;
import org.junit.jupiter.api.Test;
import com.ratryday.models.Cart;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private static final String ADDED = "added";
    private static final Integer TEST_ID_ONE = 1;
    private static final Integer TEST_ID_ZERO = 0;
    private static final String message = "message";
    private static final String PRODUCT = "product";
    private static final String CATEGORY = "category";
    private static final String PRODUCT_ID = "productId";
    private static final String CATEGORY_ID = "categoryId";
    private static final String SLASH_PRODUCT = "/product";
    private static final String ALL_PRODUCTS = "allProducts";
    private static final String PRODUCT_PRODUCT = "product/product";
    private static final String PRODUCT_PRODUCTS = "product/products";
    private static final String message_CONTENT = "There are no products here.";
    private static final String SLASH_PRODUCT_SLASH_PRODUCTS = "/product/products";

    @BeforeEach
    void setUp() {
        testCategory = new Category();
        testCategory.setCategoryName("name");
        testProduct = new Product();
        testProduct.setProductImage("Image");
        testProduct.setCategory(testCategory);

        testCartEntry = new CartEntry();
        testCartEntry.setProduct(testProduct);
        List<CartEntry> cartEntryList = new ArrayList<>();
        cartEntryList.add(testCartEntry);

        testCart = new Cart();
        testCart.setCartEntry(cartEntryList);
    }

    private final MockMvc mockMvc;

    @Mock
    private MockHttpSession mockHttpSession;

    @MockBean
    private CartServices cartServicesMockBean;

    @MockBean
    private ProductServices productServicesMockBean;

    @MockBean
    private CategoryServices categoryServicesMockBean;

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
        when(categoryServicesMockBean.getCategory(TEST_ID_ZERO)).thenReturn(testCategory);
        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID_ZERO))).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID_ZERO)));

        mockMvc.perform(get(SLASH_PRODUCT_SLASH_PRODUCTS).param(CATEGORY_ID, String.valueOf(TEST_ID_ZERO)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCTS))
                .andExpect(model().attribute(CATEGORY, categoryServicesMockBean.getCategory(TEST_ID_ZERO)))
                .andExpect(model().attribute(message, equalTo(message_CONTENT)));
    }

    @Spy
    List<Product> spyListOfProducts = new ArrayList<>();

    {
        spyListOfProducts.add(new Product());
    }

    @Test
    void productListWhenProductServicesReturnListOfProducts() throws Exception {
        when(categoryServicesMockBean.getCategory(TEST_ID_ZERO)).thenReturn(testCategory);

        when(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID_ZERO))).thenReturn(spyListOfProducts);

        assertNotNull(productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID_ZERO)).get(0));

        mockMvc.perform(get(SLASH_PRODUCT_SLASH_PRODUCTS).param(CATEGORY_ID, String.valueOf(TEST_ID_ZERO)))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCTS))
                .andExpect(model().attribute(CATEGORY, categoryServicesMockBean.getCategory(TEST_ID_ZERO)))
                .andExpect(model().attribute(ALL_PRODUCTS,
                        productServicesMockBean.getProductList(categoryServicesMockBean.getCategory(TEST_ID_ZERO))));
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
        when(productServicesMockBean.getProduct(TEST_ID_ZERO)).thenReturn(testProduct);

        // Check that cartServices.getCart() return null
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(null);
        
        assertNull(cartServicesMockBean.getCart(mockHttpSession));

        mockMvc.perform(get(SLASH_PRODUCT)
                        .param(PRODUCT_ID, String.valueOf(TEST_ID_ZERO))
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID_ZERO)));
    }


    @Test
    void productWhenCartExistAndCartEntryDoesNotHaveProductWithProductId() throws Exception {
        when(productServicesMockBean.getProduct(TEST_ID_ZERO)).thenReturn(testProduct);

        testCart.getCartEntry().get(TEST_ID_ZERO).getProduct().setProductId(TEST_ID_ONE);

        // Check that cartServices.getCart() return Cart
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(testCart);

        assertNotNull(cartServicesMockBean.getCart(mockHttpSession));

        // If it doesn't fiend product with the same product id in cart entry return false
        List<CartEntry> cartEntryList = cartServicesMockBean.getCart(mockHttpSession).getCartEntry();

        assertFalse(cartEntryList.stream().anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == TEST_ID_ZERO));

        mockMvc.perform(get(SLASH_PRODUCT).param(PRODUCT_ID, String.valueOf(TEST_ID_ZERO))
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID_ZERO)));
    }

    @Test
    void productWhenCartExistAndCartEntryHaveProductWithProductId() throws Exception {
        // Check that cartServices.getCart() return Cart
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(testCart);

        assertNotNull(cartServicesMockBean.getCart(mockHttpSession));

        when(productServicesMockBean.getProduct(TEST_ID_ZERO)).thenReturn(testProduct);

        // If fiend product with the same product id in cart entry return true
        List<CartEntry> cartEntryList = cartServicesMockBean.getCart(mockHttpSession).getCartEntry();

        assertTrue(cartEntryList.stream().anyMatch(cartEntry -> cartEntry.getProduct().getProductId() == TEST_ID_ZERO));

        mockMvc.perform(get(SLASH_PRODUCT)
                        .param(PRODUCT_ID, String.valueOf(TEST_ID_ZERO))
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCT_PRODUCT))
                .andExpect(model().attribute(ADDED, ADDED))
                .andExpect(model().attribute(PRODUCT, productServicesMockBean.getProduct(TEST_ID_ZERO)));
    }
}
