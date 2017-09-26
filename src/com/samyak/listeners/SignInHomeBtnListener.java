package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.SignInDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInHomeBtnListener implements ActionListener{
    private SignInDialog signInDialog;
    private Home home;

    public SignInHomeBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signInDialog == null || !signInDialog.isDisplayable()) {
            this.signInDialog = new SignInDialog(home.getAccountTypeComboBox().getSelectedItem().toString());
            signInDialog.setContentPane(signInDialog.getScrollPane());
            signInDialog.pack();
            signInDialog.setLocationRelativeTo(home.getHomePanel());
            signInDialog.setVisible(true);
        }
        else {
            signInDialog.setVisible(true);
        }
    }
}
