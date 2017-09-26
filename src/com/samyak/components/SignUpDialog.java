package com.samyak.components;

import com.samyak.listeners.SignUpListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignUpDialog extends JDialog{
    private JScrollPane scrollPane;
    private JPanel signUpPanel;
    private JTextField name;
    private JTextField email;
    private JPasswordField passwd;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton signUpButton;
    private JButton cancelButton;
    private String dbName;

    public SignUpDialog(String dbType) {
        // Choosing Correct DB
        if (dbType.equals("Student"))
            dbName = "students";
        else
            dbName = "teachers";

        scrollPane = new JScrollPane(signUpPanel);
        signUpButton.addActionListener(new SignUpListener(this));
        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    public JPanel getSignUpPanel() {
        return signUpPanel;
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JRadioButton getMaleRadioButton() {
        return maleRadioButton;
    }

    public JRadioButton getFemaleRadioButton() {
        return femaleRadioButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JTextField getUserName() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }

    public void onCancel() {
        // closing dialog
        this.dispose();
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
