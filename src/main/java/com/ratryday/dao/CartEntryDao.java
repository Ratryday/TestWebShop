package com.ratryday.dao;

import com.ratryday.models.CartEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartEntryDao {

    List<CartEntry> select();

    CartEntry selectOne(int id);

    boolean insert(CartEntry cartEntry);

    boolean update(CartEntry cartEntry);

    boolean delete(int id);

    boolean clear();

}
