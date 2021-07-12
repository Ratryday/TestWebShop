package com.ratryday.services;

import com.ratryday.dao.ProductDao;
import com.ratryday.dao.ProductDaoImpl;
import com.ratryday.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServices {

    private ProductDao productDao = new ProductDaoImpl();

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
