package com.ratryday.controllers;

import com.ratryday.dao.CartDao;
import com.ratryday.models.Order;
import org.springframework.ui.Model;
import com.ratryday.services.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ratryday.controllers.Constants.YOUR_MAIL_ADDRESS;

@Controller
@Transactional
@RequestMapping("/order")
public class OrderController {

    private final OrderServices orderServices;
    private final CartDao cartDao;

    @Autowired
    public OrderController(OrderServices orderServices, CartDao cartDao) {
        this.orderServices = orderServices;
        this.cartDao = cartDao;
    }

    @GetMapping()
    public String order(@ModelAttribute ("order") Order order, HttpSession httpSession){
        return "order/order";
    }

    @PostMapping("/buy")
    public String buy(@ModelAttribute ("order") @Valid Order order, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "order/order";
        }
        String message = "Order{" +
                ", phoneNumber='" + order.getPhoneNumber() + '\'' +
                ", mailAddress='" + order.getMailAddress() + '\'' +
                ", customerName='" + order.getCustomerName() + '\'' +
                ", customerSurname='" + order.getCustomerSurname() + '\'' +
                '}';
        orderServices.sendSimpleMessage(YOUR_MAIL_ADDRESS, "my subject", message);
        order.setCart(cartDao.selectOne(httpSession));
        orderServices.save(order);
        return "order/endOfPurchase";
    }

}
