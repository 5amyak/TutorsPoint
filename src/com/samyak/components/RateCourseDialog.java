package com.samyak.components;

import com.samyak.Home;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
            stmt.setInt(1, courseId);
            stmt.setInt(2, Home.getHome().getUserId());
            stmt.setInt(3, rating);
            stmt.executeUpdate();

            con.close();
            new ErrorMsgDisplay("Successfully Rated!!!", null);
            buttonCancel.doClick();

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
