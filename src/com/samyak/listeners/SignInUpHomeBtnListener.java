package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.SignInDialog;
import com.samyak.components.SignUpDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// same listener used to display signIn/signUp dialog distinguished on the basis of text of JButton
public class SignInUpHomeBtnListener implements ActionListener {
    private JDialog dialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog == null || !dialog.isDisplayable()) {
            if (((JButton) e.getSource()).getText().equals("Sign In"))
                this.dialog = new SignInDialog(Home.getHome().getAccountTypeComboBox().getSelectedItem().toString());
            else
                this.dialog = new SignUpDialog(Home.getHome().getAccountTypeComboBox().getSelectedItem().toString());
            dialog.pack();
            dialog.setLocationRelativeTo(Home.getHome().getHomePanel());
            dialog.setVisible(true);
        } else {
            dialog.setVisible(true);
        }
    }
}
