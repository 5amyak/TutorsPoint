package com.samyak.components;

import com.samyak.Course;
import com.samyak.Home;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AddSubtopicDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonAddSubtopic;
    private JButton buttonCancel;
    private JComboBox courseComboBox;
    private JTextField nameField;
    private JTextArea descriptionTextArea;

    public AddSubtopicDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonAddSubtopic);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        buttonAddSubtopic.addActionListener(e -> onAddSubtopic());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onAddSubtopic() {
        try {
            String subtopicName = nameField.getText().trim();
            String subtopicDescription = descriptionTextArea.getText().trim();
            if (subtopicName.equals("") || subtopicDescription.equals(""))
                throw new Exception("* marked fields are mandatory.");
            // SQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint","root","");
            String sql = "INSERT INTO subtopics (`course_id`, `name`, `description`) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, ((Course)courseComboBox.getSelectedItem()).getCourseId());
            stmt.setString(2, subtopicName);
            stmt.setString(3, subtopicDescription);
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay(String.format("Subtopic %s Added Successfully to %s!!!", nameField.getText(), courseComboBox.getSelectedItem().toString()), this.contentPane);
            con.close();
            this.onCancel();
        }
        catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), contentPane);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        ArrayList<Course> courses = new ArrayList<>();

        // SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT course_id, name, avg_rating FROM courses WHERE teacher_id = ?");
            stmt.setInt(1, Home.getHome().getUserId());
            ResultSet rs = stmt.executeQuery();
            // if record found using email
            while (rs.next()) {
                courses.add(new Course(rs.getInt(1), Home.getHome().getUserId(), rs.getString(3), rs.getInt(4)));
            }
            con.close();

            courseComboBox = new JComboBox(courses.toArray());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
