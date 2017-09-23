package com.samyak;

import javax.swing.*;

public class Home {
    private JPanel homePanel;
    private JTree tree1;
    private JTabbedPane tabbedPane1;
    private JButton button1;
    private JComboBox typeComboBox;
    private JButton signInHomeButton;
    private JButton signUpHomeButton;

    public Home() {
        signInHomeButton.addActionListener(new SignInHomeBtnListener(homePanel, typeComboBox.getSelectedItem().toString()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        String accountType[]={"Student", "Teacher"};
        typeComboBox = new JComboBox(accountType);
    }
}
