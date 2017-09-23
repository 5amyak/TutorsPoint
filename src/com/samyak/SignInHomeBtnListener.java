package com.samyak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInHomeBtnListener implements ActionListener{
    private JPanel homePanel;
    private JFrame signInFrame;
    private JComboBox typeComboBox;

    public SignInHomeBtnListener(JPanel homePanel, JComboBox typeComboBox) {
        this.homePanel = homePanel;
        this.typeComboBox =  typeComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signInFrame == null || !signInFrame.isDisplayable()) {
            this.signInFrame = new JFrame("SignInDialog");
            signInFrame.setContentPane(new SignInDialog(typeComboBox.getSelectedItem().toString()).getSignInFrame());
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
