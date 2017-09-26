package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.Subtopic;
import com.samyak.components.ButtonTabComponent;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodeSelectListener implements TreeSelectionListener {

    Home home;

    public TreeNodeSelectListener(Home home) {
        this.home = home;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        // Returns the last path element of the selection.
        // This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                home.getCoursesTree().getLastSelectedPathComponent();

        // Nothing is selected
        if (node == null)
            return;

        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof Subtopic) {
            // typecast tree node Object to original Object
            Subtopic subtopic = (Subtopic) nodeInfo;
            JTabbedPane tabbedPane = home.getContentDisplayTabbedPane();

            // if already opened, switch to it and return
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getComponentAt(i).getName().equals(Integer.toString(subtopic.getSubtopicId()))) {
                    tabbedPane.setSelectedIndex(i);
                    return;
                }
            }

            // create tab from createVideoTab() method in Utilities class
            // switch to newly created tab
            // modify tab button to allow closing of tab
            JScrollPane scrollPane = home.getUtil().createVideoTab(subtopic);
            tabbedPane.addTab(subtopic.getSubtopicName(), null, scrollPane, subtopic.getSubtopicDescription());
            tabbedPane.setSelectedComponent(scrollPane);
            tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollPane), new ButtonTabComponent(tabbedPane, home.getCoursesTree()));
        }
    }
}

