package com.patikadev.model;

import com.patikadev.helper.DbConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
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

    public static ArrayList<User> getList() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        User object;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                object = new User();
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

    public static boolean add(User user) {
        String query = "INSERT INTO user(name, username, password, user_type) VALUES (?,?,?,?)";
        User findUser = User.getFetch(user.getUsername());
        if (findUser != null) {
            Helper.showMessage("Kayıtlı kullanıcı adı!");
            return false;
        }
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserType());
            int response = preparedStatement.executeUpdate();
            if (response == -1) {
                Helper.showMessage("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getFetch(String username) {
        User object = null;
        String query = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = new User();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setUserType(resultSet.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    public static User getFetch(String username, String password) {
        User object = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("user_type")){
                    case "Operator"->object= new Operator();
                    default -> object=new User();
                }
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setUserType(resultSet.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    public static User getFetchById(int id) {
        User object = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = new User();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
                object.setUsername(resultSet.getString("username"));
                object.setPassword(resultSet.getString("password"));
                object.setUserType(resultSet.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        ArrayList<Course> courses=Course.getListByUser(id);
        for (Course course: courses){
            Course.delete(course.getId());
        }
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(User user){
        String query="UPDATE user SET name=?, username=?, password=?, user_type=? WHERE id=?";
        User findUser = User.getFetch(user.getUsername());
        if ((findUser != null) && (findUser.getId() != user.getId())) {
            Helper.showMessage("Kayıtlı kullanıcı adı!");
            return false;
        }
        try {
            PreparedStatement preparedStatement= DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,user.name);
            preparedStatement.setString(2,user.username);
            preparedStatement.setString(3,user.password);
            preparedStatement.setString(4,user.userType);
            preparedStatement.setInt(5,user.id);
            return preparedStatement.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> users = new ArrayList<>();
        User object;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                object = new User();
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

    public static String searchQuery(String name, String username, String userType){
        String query="SELECT * FROM user WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%' AND user_type LIKE '%{{user_type}}%'";
        query=query.replace("{{username}}",username);
        query=query.replace("{{name}}",name);
        query=query.replace("{{user_type}}",userType);
//        if (!userType.isEmpty()){
//            query+="AND user_type='{{user_type}}'";
//            query=query.replace("{{user_type}}",userType);
//        }
        return query;
    }

    public static ArrayList<User> getByUserTypeEducator(){
        ArrayList<User> userList= new ArrayList<>();
        String query="SELECT * FROM user WHERE user_type='Educator'";
        User object;
        try {
           Statement statement= DbConnector.getInstance().createStatement();
           ResultSet resultSet= statement.executeQuery(query);
           while (resultSet.next()){
               object= new User(resultSet.getInt("Id"),
                                resultSet.getString("name"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("user_type")
                                );
               userList.add(object);
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
