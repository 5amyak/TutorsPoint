package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.ManageSubscriptionsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDialogBtnListener implements ActionListener {

    private JDialog dialog;

    public StudentDialogBtnListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        if (dialog == null || !dialog.isDisplayable()) {
            dialog = new ManageSubscriptionsDialog();
            dialog.pack();
            dialog.setLocationRelativeTo((Component) e.getSource());
            dialog.setVisible(true);
        } else
            dialog.setVisible(true);
    }
}
