package com.ratryday.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import com.ratryday.dao.OrderDao;
import com.ratryday.models.Order;
import org.mockito.InjectMocks;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;

/**
 * {@link OrderServices} must send mail message, save orders to DB and get order or list of orders
 */

@WebMvcTest(OrderServices.class)
class OrderServicesTest {

    private final static String FROM = "TestWebShop@baeldung.com";
    private final static String TO = "ourMailAddress@gmail.com";
    private final static String SUBJECT = "my subject";
    private final static String TEXT = "message";
    private SimpleMailMessage simpleMailMessage;
    private Order testOrder;
    private int testId;

    @InjectMocks
    private OrderServices orderServices;


    @Mock
    private JavaMailSender mailSenderMock;

    @Mock
    private OrderDao orderDaoMock;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @BeforeEach
    void setUp() {
        simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(FROM);
        simpleMailMessage.setTo(TO);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(TEXT);
        testOrder = new Order();
        testId = 0;
    }

    @Test
    void sendSimpleMessage() {
        orderServices.sendSimpleMessage(TO, SUBJECT, TEXT);

        verify(mailSenderMock).send(simpleMailMessageArgumentCaptor.capture());

        assertThat(simpleMailMessageArgumentCaptor.getValue(), is(simpleMailMessage));
    }

    @Test
    void savePassOrderToOrderDao() {
        orderServices.save(testOrder);

        verify(orderDaoMock).insert(orderArgumentCaptor.capture());

        assertThat(orderArgumentCaptor.getValue(), is(testOrder));
    }

    @Test
    void saveReturnFalseIfOrderDaoReturnFalse() {
        when(orderDaoMock.insert(testOrder)).thenReturn(false);

        assertFalse(orderServices.save(testOrder));
    }

    @Test
    void saveReturnTrueIfOrderDaoReturnTrue() {
        when(orderDaoMock.insert(testOrder)).thenReturn(true);

        assertTrue(orderServices.save(testOrder));
    }

    @Test
    void getOrderListReturnListOfOrdersIfOrderDaoReturnListOfOrders() {
        when(orderDaoMock.select()).thenReturn(new ArrayList<>());

        assertThat(orderDaoMock.select(), is(new ArrayList<>()));
    }

    @Test
    void getOrderReturnNullIfOrderDaoReturnNull() {
        when(orderDaoMock.selectOne(testId)).thenReturn(null);

        assertNull(orderDaoMock.selectOne(testId));
    }

    @Test
    void getOrderReturnOrderIfOrderDaoReturnOrder() {
        when(orderDaoMock.selectOne(testId)).thenReturn(testOrder);

        assertThat(orderDaoMock.selectOne(testId), is(testOrder));
    }
}