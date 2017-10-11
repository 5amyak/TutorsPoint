package com.samyak.listeners;

import com.samyak.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignOutListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserType().equals("student")) {
            Home.getHome().getAccountTypeComboBox().addItem("Teacher");
        } else if (Home.getHome().getUserType().equals("teacher")) {
            Home.getHome().getAccountTypeComboBox().addItem("Student");
        } else
            return;

        // removes added elements and restores top tool bar to original form
        Home.getHome().getTopToolBar().remove(Home.getHome().getSignOutBtn());

        Home.getHome().getTopToolBar().add(Home.getHome().getSignInHomeBtn());
        Home.getHome().getTopToolBar().add(new JToolBar.Separator());
        Home.getHome().getTopToolBar().add(Home.getHome().getSignUpHomeBtn());

        Home.getHome().getContentDisplayTabbedPane().removeAll();
        Home.getHome().getContentDisplayTabbedPane().revalidate();
        Home.getHome().getContentDisplayTabbedPane().repaint();

        Home.getHome().setUserName("");
        Home.getHome().setUserId(-1);
        Home.getHome().setUserType("");
    }
}
