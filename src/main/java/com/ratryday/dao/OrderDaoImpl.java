package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Order;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Order> select() {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        return criteria.list();
    }

    @Override
    public Order selectOne(int id) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("cartId", id));
        return (Order) criteria.uniqueResult();
    }

    @Override
    public boolean insert(Order order) {
        session = this.sessionFactory.getCurrentSession();
        session.save(order);
        return true;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean clear() {
        // not sure that I will add this
        return false;
    }

}
