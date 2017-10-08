package com.samyak.listeners;

import com.samyak.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignOutListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Home.getHome().getTopToolBar().add(Home.getHome().getSignInHomeBtn());
        Home.getHome().getTopToolBar().add(new JToolBar.Separator());
        Home.getHome().getTopToolBar().add(Home.getHome().getSignUpHomeBtn());

        Home.getHome().getTopToolBar().remove(Home.getHome().getSignOutBtn());
        Home.getHome().setUserName("");
        Home.getHome().setUserId(-1);
        Home.getHome().setUserType("");
    }
}
