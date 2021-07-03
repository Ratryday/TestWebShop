package com.ratryday.dao;

import com.ratryday.models.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> select();

    Category selectOne(int id);

    boolean insert(Category category);

    boolean update(Category category);

    boolean delete(int id);

    boolean clear();

}
