package com.samyak;

import javax.swing.*;

public class Home {
    private JPanel homePanel;
    private JTree coursesTree;
    private JTabbedPane contentDisplayTabbedPane;
    private JButton button1;
    private JComboBox accountTypeComboBox;
    private JButton signInHomeBtn;
    private JButton signUpHomeBtn;
    private JToolBar topToolBar;

    public Home() {
        signInHomeBtn.addActionListener(new SignInHomeBtnListener(homePanel, accountTypeComboBox));
        signUpHomeBtn.addActionListener(new SignUpHomeBtnListener(homePanel, accountTypeComboBox));
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
        accountTypeComboBox = new JComboBox(accountType);
    }
}
