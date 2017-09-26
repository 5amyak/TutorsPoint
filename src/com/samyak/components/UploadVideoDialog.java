package com.samyak.components;

import com.samyak.Home;
import com.samyak.Subtopic;
import com.samyak.listeners.UploadVideoListener;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UploadVideoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox subtopicsComboBox;
    private JTextField videoNameField;

    public UploadVideoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new UploadVideoListener(this));

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

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        ArrayList<Subtopic> subtopics = new ArrayList<>();

        // SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT course_id FROM courses WHERE teacher_id = ?");
            stmt.setInt(1, Home.getHome().getUserId());
            ResultSet rs = stmt.executeQuery();
            // if record found using email
            while (rs.next()) {
                stmt = con.prepareStatement("SELECT subtopic_id, name, description FROM subtopics WHERE course_id = ?");
                stmt.setInt(1, rs.getInt(1));
                ResultSet nrs = stmt.executeQuery();
                while (nrs.next())
                    subtopics.add(new Subtopic(nrs.getInt(1), nrs.getString(2), nrs.getString(3)));
            }
            con.close();

            subtopicsComboBox = new JComboBox(subtopics.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JComboBox getSubtopicsComboBox() {
        return subtopicsComboBox;
    }

    public JTextField getVideoNameField() {
        return videoNameField;
    }
}
