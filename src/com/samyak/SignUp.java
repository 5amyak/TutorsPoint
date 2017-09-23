package com.samyak;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    private JPanel signUpForm;
    private JTextField name;
    private JTextField email;
    private JPasswordField passwd;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JButton signUpButton;

    public SignUp() {
        signUpButton.addActionListener(new SignUpBtnListener(this));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SignUp");
        frame.setContentPane(new SignUp().signUpForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getSignUpForm() {
        return signUpForm;
    }

    public JTextField getName() {
        return name;
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JRadioButton getMaleRadioButton() {
        return maleRadioButton;
    }

    public JRadioButton getFemaleRadioButton() {
        return femaleRadioButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }
}
