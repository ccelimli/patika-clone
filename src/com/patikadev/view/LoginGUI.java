package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Operator;
import com.patikadev.model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wrapper_bottom;
    private JPanel wrapper_top;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JLabel lbl_login;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_icon;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(600,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_username)||Helper.isFieldEmpty(fld_password)){
                Helper.showMessage("fill");
            }else{
                User user= User.getFetch(fld_username.getText(),fld_password.getText());
                if (user==null){
                    Helper.showMessage("Kullanıcı Bulunamadı!");
                }else{
                    switch (user.getUserType()){
                        case "Operator"->{OperatorGUI operatorGUI= new OperatorGUI((Operator) user);}
                        case "Educator"->{EducatorGUI educatorGUI= new EducatorGUI();}
                        case "Student"->{StudentGUI studentGUI= new StudentGUI();}
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI= new LoginGUI();
    }
}
