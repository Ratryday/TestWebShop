package com.ratryday.controllers;

import com.ratryday.dao.CartDao;
import com.ratryday.models.Order;
import org.springframework.ui.Model;
import com.ratryday.services.OrderServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

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
    public String order(Model model){
        return "order/order";
    }

    @PostMapping("/buy")
    public String buy(@RequestParam String customerName, @RequestParam String customerSurname,
                      @RequestParam String mailAddress,@RequestParam String phoneNumber,
                      Model model, HttpSession httpSession) {
        String message = "Order{" +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerSurname='" + customerSurname + '\'' +
                '}';
        orderServices.sendSimpleMessage(mailAddress, "my subject", message);
        orderServices.save(new Order(customerName, customerSurname, mailAddress, phoneNumber, cartDao.selectOne(httpSession)));
        return "order/endOfPurchase";
    }

}
