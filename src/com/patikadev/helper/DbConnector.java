package com.patikadev.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private Connection connection=null;
    public Connection connectDb(){
        try {
            this.connection= DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.connection;
    }

    public static Connection getInstance(){
        DbConnector database= new DbConnector();
        return database.connectDb();
    }
}
