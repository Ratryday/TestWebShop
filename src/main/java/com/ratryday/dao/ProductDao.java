package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Product;

import java.util.List;

public interface ProductDao {

    List<Product> select(int categoryId);

    Product selectOne(int id);

    boolean insert(Product product);

    boolean update(Product product);

    boolean delete(int id);

    boolean clear();

}
