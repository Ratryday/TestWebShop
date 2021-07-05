package com.ratryday.dao;

import com.ratryday.controllers.ConnectionPool;
import com.ratryday.models.Category;
import com.ratryday.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ratryday.controllers.Constants.*;

public class OrderDaoImpl implements OrderDao {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Order order = new Order();

    @Override
    public List<Order> select() {
        List<Order> orderList = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ORDER);
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int phoneNumber = resultSet.getInt(2);
                String mailAddress = resultSet.getString(3);
                String customerName = resultSet.getString(4);
                String customerSurname = resultSet.getString(5);
                order = new Order(orderId, phoneNumber, mailAddress, customerName, customerSurname);
                orderList.add(order);
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
        return orderList;
    }

    @Override
    public Order selectOne(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_ORDER);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                int phoneNumber = resultSet.getInt(2);
                String mailAddress = resultSet.getString(3);
                String customerName = resultSet.getString(4);
                String customerSurname = resultSet.getString(5);
                order = new Order(orderId, phoneNumber, mailAddress, customerName, customerSurname);
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
        return order;
    }

    @Override
    public boolean insert(Order order) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER);
            preparedStatement.setInt(1, order.getPhoneNumber());
            preparedStatement.setString(2, order.getMailAddress());
            preparedStatement.setString(3, order.getCustomerName());
            preparedStatement.setString(4, order.getCustomerSurname());
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
    public boolean update(Order order) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER);
            preparedStatement.setInt(1, order.getPhoneNumber());
            preparedStatement.setString(2, order.getMailAddress());
            preparedStatement.setString(3, order.getCustomerName());
            preparedStatement.setString(4, order.getCustomerSurname());
            preparedStatement.setInt(5, order.getOrderId());
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);
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

}
