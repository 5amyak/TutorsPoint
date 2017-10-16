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

public class InProgressCourseBtnListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        // SQL to store user_id and course_id in Database
        try {
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            if (((JButton) e.getSource()).getText().equals("Continue Later")) {
                String sql = "INSERT INTO `in_progress_courses`(`student_id`, `course_id`) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data inserted successfully
                new ErrorMsgDisplay("Successfully Added to In-Progress Courses!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Remove from In-Progress Courses");
            } else {
                String sql = "DELETE FROM `in_progress_courses` WHERE `student_id` = ? AND `course_id` = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data deleted successfully
                new ErrorMsgDisplay("Successfully Removed From In-Progress Courses!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Continue Later");
            }
            con.close();

        } catch (MySQLIntegrityConstraintViolationException e1) {
            new ErrorMsgDisplay("You've already added it to in-progress courses. Press Again to remove from it.", (Component) e.getSource());
            ((JButton) e.getSource()).setText("Remove from In-Progress Courses");
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Component) e.getSource());
        }
    }

}