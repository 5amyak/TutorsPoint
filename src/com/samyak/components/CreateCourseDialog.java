package com.samyak.components;

import com.samyak.Home;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateCourseDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCreateCourse;
    private JButton buttonCancel;
    private JTextField courseNameField;

    public CreateCourseDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCreateCourse);

        buttonCreateCourse.addActionListener(e -> onCreateCourse());

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

    private void onCreateCourse() {

        try {
            String courseName = courseNameField.getText().trim();
            if (courseName.equals(""))
                throw new Exception("* marked fields are mandatory.");

            // SQL to create a new course
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            String sql = "INSERT INTO courses (`teacher_id`, `name`) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            stmt.setString(2, courseName);
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay(String.format("%s Course Created Successfully!!!", courseName), this.contentPane);
            con.close();
            this.onCancel();

            DefaultTreeModel model = (DefaultTreeModel) Home.getHome().getCoursesTree().getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();
            Home.getHome().getUtil().createNodes(root);
            model.reload(root);
        }
        catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), contentPane);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        this.dispose();
    }
}
