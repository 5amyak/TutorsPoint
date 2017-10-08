package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.RateCourseDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RateCourseBtnListener implements ActionListener {
    private RateCourseDialog rateCourseDialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        if (rateCourseDialog == null || !rateCourseDialog.isDisplayable()) {
            rateCourseDialog = new RateCourseDialog(Integer.parseInt(((JButton) e.getSource()).getName()));
            rateCourseDialog.pack();
            rateCourseDialog.setLocationRelativeTo((Component) e.getSource());
            rateCourseDialog.setVisible(true);
        } else
            rateCourseDialog.setVisible(true);
    }
}
