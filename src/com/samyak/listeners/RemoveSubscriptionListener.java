package com.samyak.listeners;

import java.sql.Connection;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.ManageSubscriptionsDialog;
import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RemoveSubscriptionListener implements ActionListener {
    JDialog dialog;

    public RemoveSubscriptionListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "DELETE FROM `subscriptions` WHERE `student_id`=? AND `teacher_id`=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            // data deleted successfully
            new ErrorMsgDisplay("Successfully UnSubscribed to Teacher!!!", (Component) e.getSource());
            con.close();
            dialog.dispose();

        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Container) e.getSource());
        }
    }
}
