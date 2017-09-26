package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.AddSubtopicDialog;
import com.samyak.components.ErrorMsgDisplay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubtopicBtnListener implements ActionListener {
    AddSubtopicDialog dialog;

    @Override
    public void actionPerformed(ActionEvent e) {

        if (dialog == null || !dialog.isDisplayable()) {
            dialog = new AddSubtopicDialog();
            dialog.pack();
            dialog.setLocationRelativeTo((Component)e.getSource());
            dialog.setVisible(true);
        }
        else
            dialog.setVisible(true);
    }
}
