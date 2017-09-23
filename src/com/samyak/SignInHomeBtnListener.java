package com.samyak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInHomeBtnListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame("SignIn");
        frame.setContentPane(new SignIn().getSignInForm());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo((Component)e.getSource());
        frame.setVisible(true);
    }
}
