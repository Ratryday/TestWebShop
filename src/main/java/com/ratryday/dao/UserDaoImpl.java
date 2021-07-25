package com.ratryday.dao;

import com.ratryday.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean insert(User user) {
        session = this.sessionFactory.getCurrentSession();
        session.save(user);
        return true;
    }

}
