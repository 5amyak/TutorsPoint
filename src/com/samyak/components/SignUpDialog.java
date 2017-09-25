package com.samyak.components;

import com.samyak.listeners.SignUpListener;

import javax.swing.*;

public class SignUpDialog extends JDialog{
    private JPanel signUpForm;
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

        signUpButton.addActionListener(new SignUpListener(this));
        cancelButton.addActionListener(e -> onCancel());
    }

    public JPanel getSignUpForm() {
        return signUpForm;
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
        JFrame frame = (JFrame)SwingUtilities.getRoot(signUpForm);
        frame.dispose();
    }
}
