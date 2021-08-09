package com.ratryday.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Component;
import com.ratryday.models.Category;
import com.ratryday.dao.ProductDao;
import com.ratryday.models.Product;

import java.io.File;
import java.util.List;
import java.io.IOException;

@Component
@Transactional
public class ProductServices {

    private final ProductDao productDao;
    private final static String PROJECT_PATH = "images/";

    @Autowired
    public ProductServices(ProductDao productDao) {
        this.productDao = productDao;
    }

    public boolean create(Product product, MultipartFile imageFile, Category category, String realPathToUpload) throws IOException {
        String filePath = saveImage(imageFile, realPathToUpload);
        product.setProductImage(filePath);
        product.setCategory(category);
        return productDao.insert(product);
    }

    public boolean delete(int id) {
        return productDao.delete(id);
    }

    public boolean isExist(String productName) {
        return productDao.selectOne(productName);
    }

    public boolean update(Product product, MultipartFile imageFile, Category category, String realPathToUploads) throws IOException {
        String filePath = saveImage(imageFile, realPathToUploads);
        product.setProductImage(filePath);
        product.setCategory(category);
        return productDao.update(product);
    }

    public Product getProduct(int id) {
        return productDao.selectOne(id);
    }

    public List<Product> getProductList(Category category) {
        return productDao.select(category);
    }

    private String saveImage(MultipartFile imageFile, String realPathToUploads) throws IOException {
        String orgName = imageFile.getOriginalFilename();
        String filePath = realPathToUploads + orgName;
        String imagePath = PROJECT_PATH + orgName;
        File dest = new File(filePath);
        imageFile.transferTo(dest);
        return imagePath;
    }

}
