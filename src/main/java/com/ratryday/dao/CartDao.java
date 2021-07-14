package com.ratryday.dao;

import com.ratryday.models.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDao {

    List<Cart> select();

    Cart selectOne(int id);

    boolean insert(Cart cart);

    boolean update(Cart cart);

    boolean delete(int id);

    boolean clear();

}
