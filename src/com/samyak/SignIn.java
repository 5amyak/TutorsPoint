package com.samyak;

import javax.swing.*;

public class SignIn {
    private JPanel signInForm;
    private JTextField email;
    private JPasswordField passwd;
    private JButton signInButton;

    public SignIn() {
        signInButton.addActionListener(new SignInBtnListener(this));
    }

    public JPanel getSignInForm() {
        return signInForm;
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

}
