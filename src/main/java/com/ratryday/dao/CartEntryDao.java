package com.ratryday.dao;

import com.ratryday.models.CartEntry;

import java.util.List;

public interface CartEntryDao {

    List<CartEntry> select();

    CartEntry selectOne(int id);

    boolean insert(CartEntry cartEntry);

    boolean update(CartEntry cartEntry);

    boolean delete(int id);

    boolean clear();

}
