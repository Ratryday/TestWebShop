package com.ratryday.dao;

import com.ratryday.controllers.ConnectionPool;
import com.ratryday.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ratryday.controllers.Constants.*;

public class CategoryDaoImpl implements CategoryDao {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Category category = new Category();

    @Override
    public List<Category> select() {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_CATEGORY);
            while (resultSet.next()) {
                int categoryId = resultSet.getInt(1);
                String categoryName = resultSet.getString(2);
                category = new Category(categoryId, categoryName);
                categoryList.add(category);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return categoryList;
    }

    @Override
    public Category selectOne(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_CATEGORY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int categoryId = resultSet.getInt(1);
                String categoryName = resultSet.getString(2);
                category = new Category(categoryId, categoryName);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return category;
    }

    @Override
    public boolean insert(Category category) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY);
            preparedStatement.setString(1, category.getCategoryName());
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(Category category) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATEGORY);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setInt(2, category.getCategoryId());
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY);
            preparedStatement.setInt(1, id);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean clear() {
        // not sure that I will add this
        return false;
    }

}
