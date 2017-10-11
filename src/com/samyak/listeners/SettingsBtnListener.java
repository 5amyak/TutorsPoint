package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ButtonTabComponent;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsBtnListener implements ActionListener {
    private Home home;

    public SettingsBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = home.getContentDisplayTabbedPane();

        // if user already signed in, then return
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up.", (Component) e.getSource());
            return;
        }

        // if already opened, switch to it and return
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getComponentAt(i).getName().equals("Settings")) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        // creating settings tab from utility function
        JScrollPane scrollPane;
        if (Home.getHome().getUserType().equals("teacher"))
            scrollPane = home.getUtil().createTeacherSettingsTab();
        else
            scrollPane = home.getUtil().createStudentSettingsTab();
        tabbedPane.addTab("Settings", scrollPane);
        tabbedPane.setSelectedComponent(scrollPane);
        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollPane), new ButtonTabComponent(tabbedPane));
    }
}
