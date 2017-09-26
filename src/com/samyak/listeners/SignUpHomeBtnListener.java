package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.SignUpDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpHomeBtnListener implements ActionListener {
    private Home home;
    private SignUpDialog signUpDialog;

    public SignUpHomeBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signUpDialog == null || !signUpDialog.isDisplayable()) {
            this.signUpDialog = new SignUpDialog(home.getAccountTypeComboBox().getSelectedItem().toString());
            signUpDialog.setContentPane(signUpDialog.getScrollPane());
            signUpDialog.pack();
            signUpDialog.setLocationRelativeTo(home.getHomePanel());
            signUpDialog.setVisible(true);
        }
        else {
            signUpDialog.setVisible(true);
        }
    }
}
