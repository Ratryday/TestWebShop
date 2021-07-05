package com.ratryday.dao;

import com.ratryday.controllers.ConnectionPool;
import com.ratryday.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ratryday.controllers.Constants.*;

public class ProductDaoImpl implements ProductDao {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Product product = new Product();

    @Override
    public List<Product> select(int categoryId) {
        List<Product> productList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
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
        return productList;
    }

    @Override
    public Product selectOne(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_PRODUCT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = getProductFromResultSet(resultSet);
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
        return product;
    }

    @Override
    public boolean insert(Product product) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT);
            preparedStatement.setInt(1, product.getCategoryId());
            preparedStatement.setInt(2, product.getProductPrice());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setString(4, product.getProductImage());
            preparedStatement.setString(5, product.getProductDescription());
            preparedStatement.executeUpdate();
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
    public boolean update(Product product) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
            preparedStatement.setInt(6, product.getProductId());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setInt(3, product.getProductPrice());
            preparedStatement.setString(4, product.getProductName());
            preparedStatement.setString(5, product.getProductImage());
            preparedStatement.setString(1, product.getProductDescription());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
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

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {

        int productId = resultSet.getInt(1);
        int categoryId = resultSet.getInt(2);
        int productPrice = resultSet.getInt(3);
        String productName = resultSet.getString(4);
        String productImage = resultSet.getString(5);
        String productDescription = resultSet.getString(6);

        product = new Product(productId, categoryId, productPrice, productName, productImage, productDescription);

        return product;
    }

}
