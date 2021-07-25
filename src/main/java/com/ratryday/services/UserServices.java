package com.ratryday.services;

import com.ratryday.dao.UserDao;
import com.ratryday.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserServices {

    private final UserDao userDao;

    @Autowired
    public UserServices(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean create(User user) {
        return userDao.insert(user);
    }
}
