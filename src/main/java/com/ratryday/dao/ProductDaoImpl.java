package com.ratryday.dao;

import com.ratryday.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.ratryday.controllers.Constants.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> select(int categoryId) {
        return null;
    }

    @Override
    public Product selectOne(int id) {
        return null;
    }

    @Override
    public boolean insert(Product product) {
        return false;
    }

    @Override
    public boolean update(Product product) {
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
