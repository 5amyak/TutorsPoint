package com.samyak.components;

import com.samyak.listeners.SignInListener;

import javax.swing.*;

public class SignInDialog extends JDialog{
    JScrollPane scrollPane;
    private JPanel signInFrame;
    private JButton cancelButton;
    private JTextField email;
    private JPasswordField passwd;
    private JButton signInButton;
    private String dbName;

    public SignInDialog(String dbType) {
        // Choosing Correct DB
        if (dbType.equals("Student"))
            dbName = "students";
        else
            dbName = "teachers";

        scrollPane = new JScrollPane(signInFrame);
        signInButton.addActionListener(new SignInListener(this));
        cancelButton.addActionListener(e -> onCancel());

    }

    public void onCancel() {
        // closing JDialog
        JFrame frame = (JFrame)SwingUtilities.getRoot(signInFrame);
        frame.dispose();
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JPanel getSignInFrame() {
        return signInFrame;
    }

    public String getDbName() {
        return dbName;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
