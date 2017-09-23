package com.samyak;

import javax.swing.*;
import java.awt.event.*;

public class SignIn extends JDialog{
    private JPanel mainPane;
    private JButton buttonCancel;
    private JTextField email;
    private JPasswordField passwd;
    private JButton signInButton;
    private String dbType;

    public SignIn(String dbType) {
        this.dbType = dbType;

        setContentPane(mainPane);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);

        signInButton.addActionListener(new SignInBtnListener(this));

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    public void onCancel() {
        // add your code here if necessary
        System.out.println("Cancelling");
        JFrame frame = (JFrame)SwingUtilities.getRoot(mainPane);
        frame.dispose();
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JPanel getMainPane() {
        return mainPane;
    }

    public String getDbType() {
        return dbType;
    }
}
