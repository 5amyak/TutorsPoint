package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.SignInDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInHomeBtnListener implements ActionListener{
    private JFrame signInFrame;
    private Home home;

    public SignInHomeBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signInFrame == null || !signInFrame.isDisplayable()) {
            this.signInFrame = new JFrame("SignInDialog");
            signInFrame.setContentPane(new SignInDialog(home.getAccountTypeComboBox().getSelectedItem().toString()).getSignInFrame());
            signInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signInFrame.pack();
            signInFrame.setLocationRelativeTo(home.getHomePanel());
            signInFrame.setVisible(true);
        }
        else {
            signInFrame.setVisible(true);
        }
    }
}
