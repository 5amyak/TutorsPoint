package com.samyak.components;

import com.samyak.Home;

import javax.swing.*;
import java.awt.event.*;

public class ManageSubscriptionsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel subscriptionsPanel;

    public ManageSubscriptionsDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        subscriptionsPanel = new JPanel();
        Home.getHome().getUtil().createSubscriptionsPanel(this, subscriptionsPanel);
    }

    public JPanel getSubscriptionsPanel() {
        return subscriptionsPanel;
    }

    public void setSubscriptionsPanel(JPanel subscriptionsPanel) {
        this.subscriptionsPanel = subscriptionsPanel;
    }
}
