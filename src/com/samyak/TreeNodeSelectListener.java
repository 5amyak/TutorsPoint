package com.samyak;

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
        //Returns the last path element of the selection.
        //This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                home.getCoursesTree().getLastSelectedPathComponent();

        //Nothing is selected or leaf is not selected.
        if (node == null || !node.isLeaf())
            return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            Subtopic subtopic = (Subtopic) nodeInfo;
        }
    }
}

