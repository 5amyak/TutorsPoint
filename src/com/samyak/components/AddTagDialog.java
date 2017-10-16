package com.samyak.components;

import com.samyak.Home;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddTagDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tagNameTextField;
    private int videoId;

    public AddTagDialog(int videoId) {
        this.videoId = videoId;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(e);
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
    }

    private void onOK(ActionEvent e) {
        try {
            String tagName = tagNameTextField.getText().trim();
            if (tagName.equals(""))
                throw new Exception("* marked fields are mandatory.");

            // SQL to create a new tag
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            String sql = "INSERT INTO video_tags(`video_id`, `name`) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, videoId);
            stmt.setString(2, tagName);
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay(String.format("%s Tag Added Successfully!!!", tagName), this.contentPane);
            con.close();
            this.onCancel();

        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), contentPane);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
