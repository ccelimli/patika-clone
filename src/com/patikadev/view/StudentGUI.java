package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_screen_student;

    public StudentGUI(){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
    }
}
