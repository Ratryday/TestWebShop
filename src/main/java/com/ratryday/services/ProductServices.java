package com.ratryday.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.ratryday.models.Category;
import com.ratryday.dao.ProductDao;
import com.ratryday.models.Product;

import java.io.IOException;
import java.util.List;
import java.io.File;

@Component
@Transactional
public class ProductServices {

    private final ProductDao productDao;

    @Autowired
    public ProductServices(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean create(double productPrice, String productName, MultipartFile imageFile,
                          String productDescription, Category category) throws IOException {
        String filePath = CreateOrUpdate(imageFile);
        productDao.insert(new Product(productPrice, productName, filePath, productDescription, category));
        return true;
    }

    public boolean delete(int id) {
        return productDao.delete(id);
    }

    public boolean update(double productPrice, String productName, MultipartFile imageFile,
                          String productDescription, Category category, int productId) throws IOException {
        if(!StringUtils.isEmpty(imageFile.getOriginalFilename())) {
            String filePath = CreateOrUpdate(imageFile);
            productDao.update(new Product(productPrice, productName, filePath, productDescription, category), productId);
            return true;
        }
        String filePath = getProduct(productId).getProductImage();
        productDao.update(new Product(productPrice, productName, filePath, productDescription, category), productId);
        return true;
    }

    private String CreateOrUpdate(MultipartFile imageFile) throws IOException {
        String realPath = "D:/Java/Projects/TestWebShop/src/main/webapp/WEB-INF/";
        String projectPath = "images/";
        String imageName = imageFile.getOriginalFilename();
        String filePath = projectPath + imageName;
        imageFile.transferTo(new File(realPath + projectPath + imageName));
        return filePath;
    }

    public List<Product> getProductList(Category category) {
        return productDao.select(category);
    }

    public Product getProduct(int id) {
        return productDao.selectOne(id);
    }

}
