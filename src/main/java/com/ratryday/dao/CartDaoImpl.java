package com.ratryday.dao;

import com.ratryday.models.Cart;
import com.ratryday.models.CartEntry;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Repository
@Transactional
public class CartDaoImpl implements CartDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public CartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cart selectOne(Cart cart) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Cart.class);
        criteria.add(Restrictions.eq("cart", cart));
        return (Cart) criteria.uniqueResult();
    }

    @Override
    public Cart selectOne(int cartId) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Cart.class);
        criteria.add(Restrictions.eq("cartId", cartId));
        return (Cart) criteria.uniqueResult();
    }

    @Override
    public Cart selectOne(HttpSession httpSession) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Cart.class);
        criteria.add(Restrictions.eq("userId", httpSession.getId()));
        return (Cart) criteria.uniqueResult();
    }

    @Override
    public boolean insert(Cart cart) {
        session = this.sessionFactory.getCurrentSession();
        if(selectOne(cart) == null){
            session.save(cart);
            return true;
        }
        return false;
    }

    @Override
    public boolean insert(HttpSession httpSession) {
        session = this.sessionFactory.getCurrentSession();
        if(selectOne(httpSession) == null){
            session.save(new Cart(httpSession.getId()));
            return true;
        }
        return true;
    }

    @Override
    public boolean update(Cart cart) {
        return false;
    }

    @Override
    public boolean delete(Cart cart) {
        session = this.sessionFactory.getCurrentSession();
        session.delete(selectOne(cart));
        return true;
    }

    @Override
    public boolean clear() {
        return false;
    }

}
