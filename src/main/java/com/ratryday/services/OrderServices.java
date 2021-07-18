package com.ratryday.services;

import com.ratryday.dao.OrderDao;
import com.ratryday.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrderServices {

    private final JavaMailSender emailSender;
    private final OrderDao orderDao;

    @Autowired
    public OrderServices(JavaMailSender emailSender, OrderDao orderDao) {
        this.emailSender = emailSender;
        this.orderDao = orderDao;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public boolean save(Order order){
        orderDao.insert(order);
        return true;
    }

    public List<Order> getOrderList(){
        return orderDao.select();
    }

    public Order getOrder(int cartId){
        return orderDao.selectOne(cartId);
    }
}
