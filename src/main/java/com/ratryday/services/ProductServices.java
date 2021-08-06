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
import javax.servlet.http.HttpServletRequest;

@Component
@Transactional
public class ProductServices {

    private final ProductDao productDao;
    private final HttpServletRequest request;

    @Autowired
    public ProductServices(ProductDao productDao, HttpServletRequest request) {
        this.productDao = productDao;
        this.request = request;
    }

    public boolean create(Product product, MultipartFile imageFile, Category category) throws IOException {
        String filePath = saveImage(imageFile);
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

    public boolean update(Product product, MultipartFile imageFile, Category category) throws IOException {
        String filePath = saveImage(imageFile);
        product.setProductImage(filePath);
        product.setCategory(category);
        productDao.update(product);
        return true;
    }

    public List<Product> getProductList(Category category) {
        return productDao.select(category);
    }

    public Product getProduct(int id) {
        return productDao.selectOne(id);
    }

    String saveImage(MultipartFile imageFile) throws IOException {
        String projectPath = "images/";
        String realPathToUploads = request.getServletContext().getRealPath(projectPath);
        String orgName = imageFile.getOriginalFilename();
        String filePath = realPathToUploads + orgName;
        String imagePath = projectPath + orgName;
        File dest = new File(filePath);
        imageFile.transferTo(dest);
        return imagePath;
    }

}
