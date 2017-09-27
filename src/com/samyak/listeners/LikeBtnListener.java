package com.samyak.listeners;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LikeBtnListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getAccountTypeComboBox().getSelectedItem().toString().equals("Student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        // SQL to store user_id and video_id of liked video
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "INSERT INTO `video_likes`(`student_id`, `video_id`) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay("Successfully Liked Video!!!", (Component) e.getSource());
            con.close();

        } catch (MySQLIntegrityConstraintViolationException e1) {
            try {
                String sql = "DELETE FROM `video_likes` WHERE `student_id`=? AND `video_id`=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data deleted successfully
                new ErrorMsgDisplay("Successfully UnLiked Video!!!", (Component) e.getSource());
                con.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
