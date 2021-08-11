package com.ratryday.controllers;

import com.ratryday.models.Cart;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Category;
import com.ratryday.models.Product;
import com.ratryday.services.CartServices;
import com.ratryday.services.ProductServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

    private Cart testCart;
    private Category testCategory;
    private Product testProduct;
    private BigDecimal totalProductPrice;
    private int productCount;
    private int id;

    @MockBean
    private CartServices cartServicesMockBean;

    @MockBean
    private ProductServices productServicesMockBean;

    @Mock
    private MockHttpSession mockHttpSession;

    private final MockMvc mockMvc;

    @Autowired
    CartControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
        testCart = new Cart();
        List<CartEntry> cartEntryList = new ArrayList<>();
        CartEntry cartEntry = new CartEntry();
        productCount = 1;
        testCategory = new Category();
        id = 0;
        cartEntry.setProductCount(productCount);
        testProduct = new Product(new BigDecimal(1.1), "name", "image",
                "description", testCategory);
        testProduct.setProductPrice(new BigDecimal(1));
        cartEntryList.add(cartEntry);
        testCart.setCartEntry(new ArrayList<>());
    }

    @Test
    void cartIfCartServicesGetCartReturnNull() throws Exception {
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(null);

        assertNull(cartServicesMockBean.getCart(mockHttpSession));

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attribute("message", "You do not add anything"));
    }

    @Test
    void cartIfCartServicesGetCartGetCartEntryReturnEmptyList() throws Exception {
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(testCart);

        assertEquals(cartServicesMockBean.getCart(mockHttpSession).getCartEntry(), new ArrayList<>());

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attribute("message", "You do not add anything"));
    }

    @Test
    void cartWhenEverythingOk() throws Exception {
        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(testCart);

        assertNotNull(cartServicesMockBean.getCart(mockHttpSession));
        assertEquals(cartServicesMockBean.getCart(mockHttpSession).getCartEntry(), new ArrayList<>());

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attribute("totalProductPrice", (Object) null))
                .andExpect(model().attribute("cart", (Object) null));
    }

    @Test
    void addToCartTest() throws Exception {
        when(productServicesMockBean.getProduct(id)).thenReturn(testProduct);
        mockMvc.perform(post("/cart/new")
                        .param("productCount", String.valueOf(productCount))
                        .param("productId", String.valueOf(id))
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("product/product"))
                .andExpect(model().attribute("product", productServicesMockBean.getProduct(id)))
                .andExpect(model().attribute("added", "added"));
    }

    @Test
    void clearCartWhenCartServicesReturnFalse() throws Exception {
        when(cartServicesMockBean.deleteCartEntry(productServicesMockBean.getProduct(id), mockHttpSession)).thenReturn(false);

        assertFalse(cartServicesMockBean.deleteCartEntry(productServicesMockBean.getProduct(id), mockHttpSession));

        mockMvc.perform(post("/cart/delete")
                        .param("productId", String.valueOf(id))
                        .session(mockHttpSession))
                .andExpect(redirectedUrlPattern("/cart*"));
    }

    @Test
    void clearCartWhenCartServicesReturnTrueButCartServicesGetCartReturnNull() throws Exception {
        when(cartServicesMockBean.deleteCartEntry(productServicesMockBean.getProduct(id), mockHttpSession)).thenReturn(true);

        assertTrue(cartServicesMockBean.deleteCartEntry(productServicesMockBean.getProduct(id), mockHttpSession));

        when(cartServicesMockBean.getCart(mockHttpSession)).thenReturn(null);

        assertNull(cartServicesMockBean.getCart(mockHttpSession));

        mockMvc.perform(post("/cart/delete")
                        .param("productId", String.valueOf(id))
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attribute("message", "You do not add anything"));
    }
}