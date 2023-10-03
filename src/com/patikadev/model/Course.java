package com.patikadev.model;

import com.patikadev.helper.DbConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private Patika patika;
    private User educator;

    public Course(){}

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetchById(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList = new ArrayList<>();

        Course object;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");
            while (resultSet.next()) {
                object = new Course(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("patika_id"),
                        resultSet.getString("name"),
                        resultSet.getString("language")
                );
                courseList.add(object);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static boolean add(Course course) {
        String query = "INSERT INTO course(user_id, patika_id, name, language) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement= DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,course.user_id);
            preparedStatement.setInt(2,course.patika_id);
            preparedStatement.setString(3, course.name);
            preparedStatement.setString(4, course.language);
            int response=preparedStatement.executeUpdate();
            if (response==-1){
                Helper.showMessage("error");
            }
            return response!=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Course> getListByUser(int user_id) {
        ArrayList<Course> courseList = new ArrayList<>();

        Course object;
        try {
            Statement statement = DbConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE id="+user_id);
            while (resultSet.next()) {
                object = new Course(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("patika_id"),
                        resultSet.getString("name"),
                        resultSet.getString("language")
                );
                courseList.add(object);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM course WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DbConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
