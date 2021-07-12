package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Product;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    private final SessionFactory sessionFactory;

    private Session session;

    @Autowired
    public CategoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> select() {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Category.class);
        return criteria.list();
    }

    @Override
    public Category selectOne(int id) {
        session = this.sessionFactory.getCurrentSession();
        return null;
    }

    @Override
    public boolean insert(Category category) {
        return false;
    }

    @Override
    public boolean update(Category category) {
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
