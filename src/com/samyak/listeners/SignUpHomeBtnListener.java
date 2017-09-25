package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpHomeBtnListener implements ActionListener {
    private Home home;
    private JFrame signUpFrame;

    public SignUpHomeBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signUpFrame == null || !signUpFrame.isDisplayable()) {
            this.signUpFrame = new JFrame("SignUpDialog");
            signUpFrame.setContentPane(new SignUpDialog(home.getAccountTypeComboBox().getSelectedItem().toString()).getSignUpForm());
            signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signUpFrame.pack();
            signUpFrame.setLocationRelativeTo(home.getHomePanel());
            signUpFrame.setVisible(true);
        }
        else {
            signUpFrame.setVisible(true);
        }
    }
}
