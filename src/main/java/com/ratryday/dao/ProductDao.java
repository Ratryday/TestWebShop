package com.ratryday.dao;

import com.ratryday.models.Category;
import com.ratryday.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDao {

    List<Product> select(Category category);

    Product selectOne(int id);

    boolean insert(Product product);

    boolean update(Product product, int productId);

    boolean delete(int id);

    boolean clear();

}
