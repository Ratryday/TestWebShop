package com.ratryday.dao;

import com.ratryday.models.Cart;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
public interface CartDao {

    Cart selectOne(Cart cart);

    Cart selectOne(int cartId);

    Cart selectOne(HttpSession httpSession);

    Cart selectOne(String userId);

    boolean insert(Cart cart);

    boolean insert(HttpSession httpSession);

    boolean update(Cart cart);

    boolean delete(Cart cart);

    boolean clear();

}
