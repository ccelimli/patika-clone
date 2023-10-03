package com.patikadev.model;

import com.patikadev.helper.DbConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Patika {
    private int id;
    private String name;

    public Patika() {
    }

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM  patika");
            while (resultSet.next()) {
                obj = new Patika(resultSet.getInt("id"), resultSet.getString("name"));
                patikaList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patikaList;
    }

    public static boolean add(String name) {
        String query = "INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean Update(int id, String name) {
        String query = "UPDATE patika SET name=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Patika getFetch(int id) {
        Patika object = null;
        String query = "SELECT * FROM patika WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                object = new Patika();
                object.setId(resultSet.getInt("id"));
                object.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    public static boolean delete(int id){
        String query="DELETE FROM patika WHERE id=?";
        ArrayList<Course> courses= Course.getList();
        for (Course course: courses){
            if (course.getPatika().getId()==id){
                Course.delete(course.getId());
            }
        }
        try {
            PreparedStatement preparedStatement=DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate()!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
