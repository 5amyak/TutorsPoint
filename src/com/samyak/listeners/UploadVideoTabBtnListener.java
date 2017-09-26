package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.UploadVideoDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UploadVideoTabBtnListener implements ActionListener {
    UploadVideoDialog dialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getAccountTypeComboBox().getSelectedItem().toString().equals("Teacher")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Teacher.", (Component) e.getSource());
            return;
        }

        if (dialog == null || !dialog.isDisplayable()) {
            dialog = new UploadVideoDialog();
            dialog.pack();
            dialog.setLocationRelativeTo((Component) e.getSource());
            dialog.setVisible(true);
        } else
            dialog.setVisible(true);
    }
}
