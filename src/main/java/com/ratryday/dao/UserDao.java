package com.ratryday.dao;

import com.ratryday.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDao {

    boolean insert(User user);

}
