package com.samyak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpHomeBtnListener implements ActionListener {
    private JPanel homePanel;
    private JFrame signUpFrame;
    private JComboBox typeComboBox;

    public SignUpHomeBtnListener(JPanel homePanel, JComboBox typeComboBox) {
        this.homePanel = homePanel;
        this.typeComboBox =  typeComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (signUpFrame == null) {
            this.signUpFrame = new JFrame("SignUpDialog");
            signUpFrame.setContentPane(new SignUpDialog(typeComboBox.getSelectedItem().toString()).getSignUpForm());
            signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            signUpFrame.pack();
            signUpFrame.setLocationRelativeTo(homePanel);
            signUpFrame.setVisible(true);
        }
        else {
            signUpFrame.setVisible(true);
        }
    }
}
