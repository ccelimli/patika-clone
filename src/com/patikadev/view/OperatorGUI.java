package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DbConnector;
import com.patikadev.helper.Helper;
import com.patikadev.model.Operator;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;

    private final Operator operator;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    public OperatorGUI(Operator operator){
        this.operator=operator;

        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText(operator.getName());

        //ModelUserList
        mdl_user_list= new DefaultTableModel();
        Object[] col_user_list={"ID", "Ad Soyad", "Kullanıcı Adı","Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        for(User object: User.getList()){
            Object[] row = new Object[col_user_list.length];
            row[0] = object.getId();
            row[1] = object.getName();
            row[2] = object.getUsername();
            row[3] = object.getPassword();
            row[4] = object.getUserType();
            mdl_user_list.addRow(row);
        }

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
    }

    public static void main(String[] args) {
        Operator operator=new Operator();
        Helper.setLayout();
        operator.setId(2);
        operator.setName("2. Kullanıcı");
        operator.setUsername("seconduser");
        operator.setPassword("111111");
        operator.setUserType("operator");
        DbConnector.getInstance();
        OperatorGUI operatorGUI=new OperatorGUI(operator);
    }

}
