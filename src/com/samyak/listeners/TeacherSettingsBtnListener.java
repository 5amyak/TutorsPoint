package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ButtonTabComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherSettingsBtnListener implements ActionListener {
    Home home;

    public TeacherSettingsBtnListener(Home home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = home.getContentDisplayTabbedPane();

        // if already opened, switch to it and return
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getComponentAt(i).getName().equals("settings")) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        JScrollPane scrollPane = home.getUtil().createTeacherSettingsTab();
        tabbedPane.addTab("Settings", scrollPane);
        tabbedPane.setSelectedComponent(scrollPane);
        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollPane), new ButtonTabComponent(tabbedPane, home.getCoursesTree()));
    }
}
