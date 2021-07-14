package com.ratryday.services;

import com.ratryday.dao.CategoryDao;
import com.ratryday.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class CategoryServices {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServices(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public boolean create(String categoryName) {

        return categoryDao.insert(new Category(categoryName));
    }

    public boolean delete() {
        return false;
    }

    public boolean update() {
        return false;
    }

    public List<Category> getCategoryList() {
        return categoryDao.select();
    }

    public Category getCategory(int id) {
        return categoryDao.selectOne(id);
    }

}
