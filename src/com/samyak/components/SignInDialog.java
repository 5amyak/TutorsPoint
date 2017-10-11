package com.samyak.components;

import com.samyak.listeners.SignInListener;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignInDialog extends JDialog{
    private JScrollPane scrollPane;
    private JPanel signInPanel;
    private JButton cancelButton;
    private JTextField email;
    private JPasswordField passwd;
    private JButton signInButton;
    private String dbName;

    public SignInDialog(String dbType) {
        scrollPane = new JScrollPane(signInPanel);

        setContentPane(scrollPane);
        setModal(true);
        getRootPane().setDefaultButton(signInButton);


        // Choosing Correct DB
        if (dbType.equals("Student"))
            dbName = "students";
        else
            dbName = "teachers";

        signInButton.addActionListener(new SignInListener(this));
        cancelButton.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

    }

    public void onCancel() {
        // closing JDialog
        this.dispose();
    }

    public JTextField getEmail() {
        return email;
    }

    public JPasswordField getPasswd() {
        return passwd;
    }

    public JPanel getSignInPanel() {
        return signInPanel;
    }

    public String getDbName() {
        return dbName;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
