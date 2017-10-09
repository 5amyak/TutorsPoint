package com.samyak.components;

import com.samyak.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RateCourseDialog extends JDialog {
    private int courseId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton radioOne;
    private JRadioButton radioTwo;
    private JRadioButton radioThree;
    private JRadioButton radioFour;
    private JRadioButton radioFive;
    private JButton removeRatingButton;

    public RateCourseDialog(int courseId) {
        this.courseId = courseId;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        radioOne.addActionListener(e -> {
            radioOne.setSelected(true);
            radioTwo.setSelected(false);
            radioThree.setSelected(false);
            radioFour.setSelected(false);
            radioFive.setSelected(false);
        });
        radioTwo.addActionListener(e -> {
            radioOne.setSelected(true);
            radioTwo.setSelected(true);
            radioThree.setSelected(false);
            radioFour.setSelected(false);
            radioFive.setSelected(false);
        });
        radioThree.addActionListener(e -> {
            radioOne.setSelected(true);
            radioTwo.setSelected(true);
            radioThree.setSelected(true);
            radioFour.setSelected(false);
            radioFive.setSelected(false);
        });
        radioFour.addActionListener(e -> {
            radioOne.setSelected(true);
            radioTwo.setSelected(true);
            radioThree.setSelected(true);
            radioFour.setSelected(true);
            radioFive.setSelected(false);
        });
        radioFive.addActionListener(e -> {
            radioOne.setSelected(true);
            radioTwo.setSelected(true);
            radioThree.setSelected(true);
            radioFour.setSelected(true);
            radioFive.setSelected(true);
        });
        removeRatingButton.addActionListener(e -> {
            try {
                // SQL to store data of new user
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
                String sql = "DELETE FROM course_ratings WHERE course_id=? AND student_id=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, courseId);
                stmt.setInt(2, Home.getHome().getUserId());
                stmt.executeUpdate();

                con.close();
                new ErrorMsgDisplay("Successfully UnRated!!!", (Component) e.getSource());
                updateRating();

            } catch (Exception e1) {
                e1.printStackTrace();
                new ErrorMsgDisplay(e1.getMessage(), (Component) e.getSource());
            }
        });
    }

    private void onOK() {
        int rating = -1;
        if (radioFive.isSelected())
            rating = 5;
        else if (radioFour.isSelected())
            rating = 4;
        else if (radioThree.isSelected())
            rating = 3;
        else if (radioTwo.isSelected())
            rating = 2;
        else if (radioOne.isSelected())
            rating = 1;

        try {
            if (rating == -1)
                throw new Exception("Select Rating First!!!");

            // SQL to store data of new user
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "INSERT INTO course_ratings (`student_id`, `course_id`, `rating`) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            stmt.setInt(2, courseId);
            stmt.setInt(3, rating);
            stmt.executeUpdate();

            con.close();
            updateRating();
            new ErrorMsgDisplay("Successfully Rated!!!", null);
            buttonCancel.doClick();

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }
    }

    private void updateRating() {
        try {
            float avg_rating;
            // SQL to store data of new user
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "SELECT AVG(rating) FROM course_ratings WHERE course_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            avg_rating = rs.getFloat(1);

            sql = "UPDATE courses SET avg_rating=? WHERE course_id=?";
            stmt = con.prepareStatement(sql);
            stmt.setFloat(1, avg_rating);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
