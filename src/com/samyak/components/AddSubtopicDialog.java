package com.samyak.components;

import com.samyak.Course;
import com.samyak.Home;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddSubtopicDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonAddSubtopic;
    private JButton buttonCancel;
    private JComboBox coursesComboBox;
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
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            String sql = "INSERT INTO subtopics (`course_id`, `name`, `description`) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, ((Course) coursesComboBox.getSelectedItem()).getCourseId());
            stmt.setString(2, subtopicName);
            stmt.setString(3, subtopicDescription);
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay(String.format("Subtopic %s Added Successfully to %s!!!", nameField.getText(), coursesComboBox.getSelectedItem().toString()), this.contentPane);
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
        dispose();
    }

    private void createUIComponents() {
        coursesComboBox = new JComboBox();
        Home.getHome().getUtil().createCoursesComboBox(coursesComboBox);
    }

}
