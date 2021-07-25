package com.ratryday.services;

import com.ratryday.dao.CartDao;
import com.ratryday.dao.CartEntryDao;
import com.ratryday.models.Cart;
import com.ratryday.models.Product;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ratryday.models.CartEntry;

import javax.servlet.http.HttpSession;

@Component
@Transactional
public class CartServices {

    private final CartDao cartDao;
    private final CartEntryDao cartEntryDao;

    @Autowired
    public CartServices(CartDao cartDao, CartEntryDao cartEntryDao) {
        this.cartDao = cartDao;
        this.cartEntryDao = cartEntryDao;
    }

    public boolean create(int productCount, Product product, HttpSession httpSession) {
        if (cartDao.insert(httpSession)) {
            cartEntryDao.insert(new CartEntry(productCount, cartDao.selectOne(httpSession), product));
            return true;
        }
        return false;
    }

    public boolean delete(Cart cart) {
        if (cartEntryDao.delete(cart.getCartEntry())) {
            cartDao.delete(cart);
            return true;
        }
        return false;
    }
    public boolean deleteCartEntry(Product product, HttpSession httpSession) {
        cartEntryDao.delete(product, cartDao.selectOne(httpSession));
        return true;
    }

    public boolean update() {
        return false;
    }

    public Cart getCart(HttpSession httpSession) {
        return cartDao.selectOne(httpSession);
    }

    public Cart getCart(String userId) {
        return cartDao.selectOne(userId);
    }

    public Cart getCart(int cartId) {
        return cartDao.selectOne(cartId);
    }

}
