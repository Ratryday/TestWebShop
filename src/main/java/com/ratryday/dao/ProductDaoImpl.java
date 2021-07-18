package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Product;
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
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> select(Category category) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.eq("category", category));
        return criteria.list();
    }

    @Override
    public Product selectOne(int id) {
        session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.eq("productId", id));
        return (Product) criteria.uniqueResult();
    }

    @Override
    public boolean insert(Product product) {
        session = this.sessionFactory.getCurrentSession();
        session.save(product);
        return true;
    }

    @Override
    public boolean update(Product product, int productId) {
        session = this.sessionFactory.getCurrentSession();

        product.setProductId(productId);
        Product productFromDB = selectOne(productId);
        session.save(productFromDB);
        session.evict(productFromDB);

        session.update(product);
        return true;
    }

    @Override
    public boolean delete(int id) {
        session = this.sessionFactory.getCurrentSession();
        session.delete(selectOne(id));
        return true;
    }

    @Override
    public boolean clear() {
        // not sure that I will add this
        return false;
    }

}
