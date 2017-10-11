package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// same listener used to display dialogs for teachers distinguished on the basis of dialog type
public class TeacherDialogBtnListener implements ActionListener {
    private JDialog dialog;

    public TeacherDialogBtnListener(JDialog dialog) {
        this.dialog = dialog;
        dialog.dispose();
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
            else if (dialog instanceof ContentStatsDialog)
                dialog = new ContentStatsDialog();
            else if (dialog instanceof AddTagDialog)
                dialog = new AddTagDialog(Integer.parseInt(((JButton) e.getSource()).getName()));

            dialog.pack();
            dialog.setLocationRelativeTo((Component) e.getSource());
            dialog.setVisible(true);
        } else
            dialog.setVisible(true);
    }
}