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

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList= new ArrayList<>();
        Patika obj;

        try {
            Statement statement= DbConnector.getInstance().createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT  * FROM  patika");
            while(resultSet.next()){
                obj= new Patika(resultSet.getInt("id"),resultSet.getString("name"));
                patikaList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patikaList;
    }

    public static boolean add(String name){
        String query="INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement preparedStatement= DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,name);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
