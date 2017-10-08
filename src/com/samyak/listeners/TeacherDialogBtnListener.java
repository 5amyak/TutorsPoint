package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.AddSubtopicDialog;
import com.samyak.components.CreateCourseDialog;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.UploadVideoDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherDialogBtnListener implements ActionListener {
    private JDialog dialog;

    public TeacherDialogBtnListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("teacher")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Teacher.", (Component) e.getSource());
            return;
        }

        if (dialog == null || !dialog.isDisplayable()) {
            if (dialog instanceof AddSubtopicDialog)
                dialog = new AddSubtopicDialog();
            else if (dialog instanceof CreateCourseDialog)
                dialog = new CreateCourseDialog();
            else if (dialog instanceof UploadVideoDialog)
                dialog = new UploadVideoDialog();

            dialog.pack();
            dialog.setLocationRelativeTo((Component) e.getSource());
            dialog.setVisible(true);
        } else
            dialog.setVisible(true);
    }
}