package com.ratryday.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ratryday.dao.CategoryDao;
import com.ratryday.models.Category;

import java.util.List;

@Component
@Transactional
public class CategoryServices {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServices(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public boolean create(Category category) {
        return categoryDao.insert(category);
    }

    public boolean update(Category category) {
        return categoryDao.update(category);
    }

    public Category getCategory(int id) {
        return categoryDao.selectOne(id);
    }

    public boolean isExist(String categoryName){
        return categoryDao.selectOne(categoryName);
    }

    public List<Category> getCategoryList() {
        return categoryDao.select();
    }

    public boolean delete(int id) {
        return categoryDao.delete(id);
    }

}
