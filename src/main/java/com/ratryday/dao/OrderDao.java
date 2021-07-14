package com.ratryday.dao;

import com.ratryday.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDao {

    List<Order> select();

    Order selectOne(int id);

    boolean insert(Order order);

    boolean update(Order order);

    boolean delete(int id);

    boolean clear();

}
