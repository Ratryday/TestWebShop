package com.ratryday.dao;

import com.ratryday.models.Cart;

import java.util.List;

public interface CartDao {

    List<Cart> select();

    Cart selectOne(int id);

    boolean insert(Cart cart);

    boolean update(Cart cart);

    boolean delete(int id);

    boolean clear();

}
