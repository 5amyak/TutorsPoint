package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.InProgressCourseDialog;
import com.samyak.components.ManageSubscriptionsDialog;
import com.samyak.components.WatchListDialog;

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
            if (dialog instanceof ManageSubscriptionsDialog)
                dialog = new ManageSubscriptionsDialog();
            else if (dialog instanceof WatchListDialog)
                dialog = new WatchListDialog();
            else if (dialog instanceof InProgressCourseDialog)
                dialog = new InProgressCourseDialog();
            dialog.pack();
            dialog.setLocationRelativeTo((Component) e.getSource());
            dialog.setVisible(true);
        } else
            dialog.setVisible(true);
    }
}
