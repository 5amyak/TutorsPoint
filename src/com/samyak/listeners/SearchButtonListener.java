package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ButtonTabComponent;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane tabbedPane = Home.getHome().getContentDisplayTabbedPane();
        String searchText = Home.getHome().getSearchTextField().getText().trim();
        if (searchText.equals("")) {
            new ErrorMsgDisplay("Enter Text to search.", (Component) e.getSource());
            return;
        }

        // if already opened, switch to it and return
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getComponentAt(i).getName().equalsIgnoreCase(searchText)) {
                tabbedPane.setSelectedIndex(i);
                return;
            }
        }

        // creating search tab from utility function
        JScrollPane scrollPane;
        scrollPane = Home.getHome().getUtil().createSearchTab(searchText);
        tabbedPane.addTab("Search", null, scrollPane, searchText);
        tabbedPane.setSelectedComponent(scrollPane);
        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollPane), new ButtonTabComponent(tabbedPane));
    }
}
