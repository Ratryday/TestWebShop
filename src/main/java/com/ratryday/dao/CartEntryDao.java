package com.ratryday.dao;

import com.ratryday.models.Cart;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Product;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface CartEntryDao {

    List<CartEntry> select();

    CartEntry selectOne(Product product, Cart cart);

    boolean insert(CartEntry cartEntry);

    boolean update(CartEntry cartEntry);

    boolean delete(List<CartEntry> cartEntryList);

    boolean delete(Product product, Cart cart);

    boolean clear();

}
