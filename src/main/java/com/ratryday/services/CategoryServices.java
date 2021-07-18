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

    public boolean create(String categoryName) {
        return categoryDao.insert(new Category(categoryName));
    }

    public boolean update(Category category) {
        return categoryDao.update(category);
    }

    public Category getCategory(int id) {
        return categoryDao.selectOne(id);
    }

    public List<Category> getCategoryList() {
        return categoryDao.select();
    }

    public boolean delete(int id) {
        return categoryDao.delete(id);
    }

}
