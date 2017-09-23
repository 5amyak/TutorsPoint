package com.samyak;

import javax.swing.*;

public class SignInDialog extends JDialog{
    private JPanel signInFrame;
    private JButton cancelButton;
    private JTextField email;
    private JPasswordField passwd;
    private JButton signInButton;
    private String dbType;

    public SignInDialog(String dbType) {
        this.dbType = dbType;

        setContentPane(signInFrame);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(new SignInBtnListener(this));
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

    public String getDbType() {
        return dbType;
    }
}
