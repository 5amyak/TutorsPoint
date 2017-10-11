package com.samyak.listeners;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SubscribeBtnListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        // SQL to store user_id and teacher_id of subscribed teacher
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            if (((JButton) e.getSource()).getText().equals("Subscribe")) {
                String sql = "INSERT INTO `subscriptions`(`student_id`, `teacher_id`) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data inserted successfully
                new ErrorMsgDisplay("Successfully Subscribed to Teacher!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Unsubscribe");
            } else {
                String sql = "DELETE FROM `subscriptions` WHERE `student_id`=? AND `teacher_id`=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data deleted successfully
                new ErrorMsgDisplay("Successfully UnSubscribed to Teacher!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Subscribe");
            }


            con.close();

        } catch (MySQLIntegrityConstraintViolationException e1) {
            new ErrorMsgDisplay("Already Subscribed. Press again to unsubscribe.", (Container) e.getSource());
            ((JButton) e.getSource()).setText("Unsubscribe");
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Container) e.getSource());
        }
    }
}