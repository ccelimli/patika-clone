package com.patikadev.model;

import com.patikadev.helper.DbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String userType;

    public User() {
    }

    public User(int id, String name, String username, String password, String userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static ArrayList<User> getList(){
        ArrayList<User> users=new ArrayList<>();
        String query="SELECT * FROM user";
        User object;
        try {
            Statement statement= DbConnector.getInstance().createStatement();
            ResultSet resultSet= statement.executeQuery(query);
            while (resultSet.next()){
                object= new User();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setUserType(resultSet.getString("user_type"));

                users.add(object);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
