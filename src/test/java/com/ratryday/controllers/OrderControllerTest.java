package com.ratryday.controllers;

import com.ratryday.dao.OrderDao;
import com.ratryday.services.OrderServices;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ratryday.models.Order;
import com.ratryday.dao.CartDao;
import com.ratryday.models.Cart;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    private Order testOrder;

    @MockBean
    private OrderServices orderServicesMockBean;

    @Mock
    private MockHttpSession mockHttpSession;

    @Mock
    private BindingResult bindingResultMock;

    @Mock
    private CartDao cartDaoMock;

    private final MockMvc mockMvc;

    @Autowired
    OrderControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
        testOrder = new Order("0", "mail@mail.com", "name", "surname", new Cart());
    }

    @Test
    void order() throws Exception {
        mockMvc.perform(get("/order")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("order/order"))
                .andExpect(model().attribute("order", new Order()));
    }

    @Test
    void buyWhenOrderIsNotValid() throws Exception {
        when(bindingResultMock.hasErrors()).thenReturn(true);

        assertTrue(bindingResultMock.hasErrors());

        mockMvc.perform(post("/order/buy")
                        .flashAttr("order", new Order()))
                .andExpect(status().isOk())
                .andExpect(view().name("order/order"));
    }

    @Spy
    private OrderServices orderServices;
    {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        OrderDao orderDao = mock(OrderDao.class);
        orderServices = new OrderServices(javaMailSender, orderDao);
    }



    @Test
    void buyWhenOrderIsValid() throws Exception {
        when(bindingResultMock.hasErrors()).thenReturn(false);

        doNothing().when(orderServices).sendSimpleMessage(anyString(),anyString(),anyString());

        assertFalse(bindingResultMock.hasErrors());

        mockMvc.perform(post("/order/buy")
                        .flashAttr("order", testOrder))
                .andExpect(status().isOk())
                .andExpect(view().name("order/endOfPurchase"));
    }
}