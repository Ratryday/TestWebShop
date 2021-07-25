package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDao {

    List<Product> select(Category category);

    Product selectOne(int id);

    boolean selectOne(String productName);

    boolean insert(Product product);

    boolean update(Product product);

    boolean delete(int id);

    boolean clear();

}
