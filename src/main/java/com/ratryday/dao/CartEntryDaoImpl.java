package com.ratryday.dao;

import com.ratryday.models.Cart;
import com.ratryday.models.CartEntry;
import com.ratryday.models.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CartEntryDaoImpl implements CartEntryDao {

    private final SessionFactory sessionFactory;
    private final CartDao cartDao;
    private Session session;

    @Autowired
    public CartEntryDaoImpl(SessionFactory sessionFactory, CartDao cartDao) {
        this.sessionFactory = sessionFactory;
        this.cartDao = cartDao;
    }

    @Override
    public List<CartEntry> select() {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CartEntry.class);
        return criteria.list();
    }

    @Override
    public CartEntry selectOne(Product product, Cart cart) {
        session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM CartEntry where product = :productParam and cart =:cartParam");
        query.setParameter("productParam", product);
        query.setParameter("cartParam", cart);
        return (CartEntry) query.setMaxResults(1).uniqueResult();
    }

    @Override
    public boolean insert(CartEntry cartEntry) {
        session = this.sessionFactory.getCurrentSession();
        session.save(cartEntry);
        return true;
    }

    @Override
    public boolean update(CartEntry cartEntry) {
        return false;
    }

    @Override
    public boolean delete(List<CartEntry> cartEntryList) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(CartEntry.class);
        for (CartEntry cartEntry : cartEntryList) {
            session.delete(cartEntry);
        }
        return true;
    }

    @Override
    public boolean delete(Product product, Cart cart) {
        session = this.sessionFactory.getCurrentSession();
        CartEntry cartEntry = selectOne(product, cart);
        session.remove(cartEntry);
        return true;
    }

    @Override
    public boolean clear() {

        return false;
    }

}
