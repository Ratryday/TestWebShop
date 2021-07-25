package com.ratryday.dao;

import com.ratryday.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryDao {

    List<Category> select();

    Category selectOne(int id);

    boolean selectOne(String categoryName);

    boolean insert(Category category);

    boolean update(Category category);

    boolean delete(int id);

    boolean clear();

}
