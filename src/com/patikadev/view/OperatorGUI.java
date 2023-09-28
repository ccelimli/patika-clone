package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.DbConnector;
import com.patikadev.helper.Helper;
import com.patikadev.helper.Item;
import com.patikadev.model.Course;
import com.patikadev.model.Operator;
import com.patikadev.model.Patika;
import com.patikadev.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;

    private final Operator operator;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JLabel lbl_user_name;
    private JTextField fld_user_username;
    private JLabel lbl_user_password;
    private JPasswordField fld_user_password;
    private JLabel lbl_user_type;
    private JComboBox cbx_user_type;
    private JButton btn_add_user;
    private JLabel lbl_user_id;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JPanel pnl_search;
    private JTextField fld_src_name;
    private JLabel lbl_src_name;
    private JLabel lbl_src_username;
    private JTextField fld_src_username;
    private JLabel lbl_src_user_type;
    private JComboBox cbx_src_user_type;
    private JButton btn_src_user;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JLabel lbl_patika_name;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cbx_course_patika;
    private JLabel lbl_course_educator;
    private JLabel lbl_course_patika;
    private JLabel lbl_course_language;
    private JLabel lbl_couse_name;
    private JComboBox cbx_course_educator;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText(operator.getName());

        //ModelUserList
        mdl_user_list = new DefaultTableModel(){
            //Tablo üzerinde edit
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);


        //Tablodan Seçme
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_user_id= tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_user_id.setText(selected_user_id);
            }catch (Exception ignored){}
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                User user= new User();
                user.setId(Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString()));
                user.setName(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString());
                user.setUsername(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString());
                user.setPassword(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString());
                user.setUserType(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString());

                if(User.update(user)){
                    Helper.showMessage("done");
                }
                loadUserModel();
            }
        });

        //ModelPatikaList
        patikaMenu= new JPopupMenu();
        JMenuItem updateMenu=new JMenuItem("Güncelle");
        JMenuItem deleteMenu=new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        //UpdatePatikaGUI
        updateMenu.addActionListener(e->{
            int select_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updatePatikaGUI= new UpdatePatikaGUI(Patika.getFetch(select_id));

            //Refresh List
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                }
            });
        });

        //Delete
        deleteMenu.addActionListener(e->{
           if (Helper.confirm("sure")){
               int select_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
               if(Patika.delete(select_id)){
                   loadPatikaModel();
                   loadPatikaCombo();
                   Helper.showMessage("done");
               }
               else {
                   Helper.showMessage("error");
               }
           }
        });

        mdl_patika_list=new DefaultTableModel();
        Object[] col_patika_list={"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list= new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);


        //Mouse Click Pop Menu
        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point= e.getPoint();
                int selected_row=tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });

        //CourseList
        mdl_course_list= new DefaultTableModel();
        Object[] col_courseList={"ID","Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_courseList);
        row_course_list= new Object[col_courseList.length];
        loadCourseList();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadEducatorCombo();
        //##CourseList

        btn_add_user.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)) {
                Helper.showMessage("fill");
            } else {
                User user = new User();
                user.setName(fld_user_name.getText());
                user.setUsername(fld_user_username.getText());
                user.setPassword(fld_user_password.getText());
                user.setUserType(cbx_user_type.getSelectedItem().toString());

                if (User.add(user)) {
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                    Helper.showMessage("done");
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMessage("fill");
            }else {
                if (Helper.confirm("sure")){
                    int user_id=Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)){
                        Helper.showMessage("done");
                        loadUserModel();
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_src_user.addActionListener(e -> {
            String name=fld_src_name.getText();
            String username=fld_src_username.getText();
            String type=cbx_src_user_type.getSelectedItem().toString();
            String query=User.searchQuery(name,username,type);
            loadUserModel(User.searchUserList(query));
        });

        //Button Cikis Yap
        btn_logout.addActionListener(e -> {
            dispose();
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }else{
                if (Patika.add(fld_patika_name.getText())){
                    loadPatikaModel();
                    loadPatikaCombo();
                    Helper.showMessage("done");
                }else {
                    Helper.showMessage("error");
                }
            }
        });
    }

    private void loadCourseList() {
        DefaultTableModel clearModel=(DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Course object: Course.getList()){
            i=0;
            row_course_list[i++]=object.getId();
            row_course_list[i++]=object.getName();
            row_course_list[i++]=object.getLanguage();
            row_course_list[i++]=object.getPatika().getName();
            row_course_list[i++]=object.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Patika patika: Patika.getList()){
            i=0;
            row_patika_list[i++]=patika.getId();
            row_patika_list[i++]=patika.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User object : User.getList()) {
            int i = 0;
            row_user_list[i++] = object.getId();
            row_user_list[i++] = object.getName();
            row_user_list[i++] = object.getUsername();
            row_user_list[i++] = object.getPassword();
            row_user_list[i++] = object.getUserType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> users) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User object : users) {
            int i = 0;
            row_user_list[i++] = object.getId();
            row_user_list[i++] = object.getName();
            row_user_list[i++] = object.getUsername();
            row_user_list[i++] = object.getPassword();
            row_user_list[i++] = object.getUserType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPatikaCombo(){
        cbx_course_patika.removeAllItems();
        for (Patika object : Patika.getList()){
            cbx_course_patika.addItem(new Item(object.getId(),object.getName()));
        }
    }

    public void loadEducatorCombo(){
        cbx_course_educator.removeAllItems();
        for (User object: User.getByUserTypeEducator()){
            if (object.getUserType().equals("Educator")){
                cbx_course_educator.addItem(new Item(object.getId(),object.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Operator operator = new Operator();
        operator.setId(2);
        operator.setName("Admin");
        operator.setPassword("admin");
        operator.setUserType("Admin");
        Helper.setLayout();
        DbConnector.getInstance();
        OperatorGUI operatorGUI = new OperatorGUI(operator);
    }

}
