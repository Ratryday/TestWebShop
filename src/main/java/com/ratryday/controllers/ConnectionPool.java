package com.ratryday.controllers;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.sql.SQLException;
import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionPool {

    private ConnectionPool() {
    }

    private static ConnectionPool instance = null;
    private DataSource dataSource;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/testPool");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException namingException) {
            namingException.printStackTrace();
        }
        return connection;
    }
}
