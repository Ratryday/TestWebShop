package com.ratryday.services;

import com.ratryday.dao.ProductDao;
import com.ratryday.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServices {

    private final ProductDao productDao;

    @Autowired
    public ProductServices(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean create(){
        return false;
    }

    public boolean delete(){
        return false;
    }

    public boolean update(){
        return false;
    }

    public List<Product> getProductList(int id){
        return productDao.select(id);
    }

    public Product getProduct(int id){
        return productDao.selectOne(id);
    }

}
