package com.samyak.components;

import com.samyak.Course;
import com.samyak.Home;
import com.samyak.Subtopic;
import com.samyak.listeners.UploadVideoListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UploadVideoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonUploadVideo;
    private JButton buttonCancel;
    private JComboBox subtopicsComboBox;
    private JTextField videoNameField;
    private JComboBox coursesComboBox;

    public UploadVideoDialog() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonUploadVideo);

        buttonUploadVideo.addActionListener(new UploadVideoListener(this));

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

        coursesComboBox.addActionListener(e -> {

            // SQL
            try {
                subtopicsComboBox.removeAllItems();
                subtopicsComboBox.revalidate();
                subtopicsComboBox.repaint();

                Connection con = Home.getHome().getUtil().getConnection();
                if (con == null)
                    return;
                PreparedStatement stmt = con.prepareStatement("SELECT subtopic_id, name, description FROM subtopics WHERE course_id = ?");
                stmt.setInt(1, ((Course) ((JComboBox) e.getSource()).getSelectedItem()).getCourseId());
                ResultSet nrs = stmt.executeQuery();
                while (nrs.next())
                    subtopicsComboBox.addItem(new Subtopic(nrs.getInt(1), nrs.getString(2), nrs.getString(3)));
                con.close();

            } catch (Exception e1) {
                e1.printStackTrace();
                new ErrorMsgDisplay(e1.getMessage(), (Component) e.getSource());
            }
        });

        if (coursesComboBox.getSelectedIndex() != -1)
            coursesComboBox.setSelectedIndex(0);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        coursesComboBox = new JComboBox();
        Home.getHome().getUtil().createCoursesComboBox(coursesComboBox);

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

    public JButton getButtonUploadVideo() {
        return buttonUploadVideo;
    }

    public JComboBox getCoursesComboBox() {
        return coursesComboBox;
    }
}
