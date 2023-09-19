package com.patikadev.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int screenCenterPoint(String axis, Dimension size) {
        int point = 0;
        switch (axis) {
            case "x" -> point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> point=0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String message){
        optionPageTr();
        String msg;
        String title;
        switch (message){
            case "fill":
                msg="Lütfen alanları eksiksiz doldurun!";
                title="Mesaj";
                break;
            case "done":
                msg="İşlem Başarılı";
                title="Sonuç";
                break;
            case "error":
                msg="İşlem esnasında bir hata oluştu!";
                title="Error";
            default:
                msg=message;
                title="Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPageTr(){
        UIManager.put("OptionPane.okButtonText","Tamam");
    }
}

