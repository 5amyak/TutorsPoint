package com.samyak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInHomeBtnListener implements ActionListener{
    private JPanel homePanel;
    private JFrame signInFrame;
    private String dbType;

    public SignInHomeBtnListener(JPanel homePanel, String type) {
        this.homePanel = homePanel;
        this.dbType = type;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signInFrame == null) {
            this.signInFrame = new JFrame("SignIn");
            signInFrame.setContentPane(new SignIn(dbType).getMainPane());
            signInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signInFrame.pack();
            signInFrame.setLocationRelativeTo(homePanel);
            signInFrame.setVisible(true);
        }
        else {
            signInFrame.setVisible(true);
        }
    }
}
