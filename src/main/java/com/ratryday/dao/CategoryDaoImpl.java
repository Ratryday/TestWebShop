package com.ratryday.dao;

import com.ratryday.models.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Criteria criteria = session.createCriteria(Category.class);
        criteria.add(Restrictions.eq("categoryId", id));
        return (Category) criteria.uniqueResult();
    }

    @Override
    public boolean insert(Category category) {
        session = this.sessionFactory.getCurrentSession();
        session.save(category);
        return true;
    }

    @Override
    public boolean update(Category category) {
        session = this.sessionFactory.getCurrentSession();

        session.save(selectOne(category.getCategoryId()));
        session.evict(selectOne(category.getCategoryId()));

        session.update(category);
        return true;
    }

    @Override
    public boolean delete(int id) {
        session = this.sessionFactory.getCurrentSession();
        session.delete(selectOne(id));
        return true;
    }


    // Delete all categories from database
    @Override
    public boolean clear() {
        // not sure that I will add this
        return false;
    }

}
