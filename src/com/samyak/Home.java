package com.samyak;

import com.samyak.includes.Utilities;
import com.samyak.listeners.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Home {
    private JPanel homePanel;
    private JTree coursesTree;
    private JTabbedPane contentDisplayTabbedPane;
    private JButton searchButton;
    private JComboBox accountTypeComboBox;
    private JButton signInHomeBtn;
    private JButton signUpHomeBtn;
    private JButton signOutBtn;
    private JToolBar topToolBar;
    private JTextField searchTextField;
    private JButton settingsButton;
    private Utilities util;
    private String userType;
    private String userName;
    private int userId;
    private static Home home;

    // other objects cannot create the instance of this class
    // only one instance created through main and accessible to all classes through static method getHome()
    // private constructor
    private Home() {
        this.userId = -1;
        this.userName = "";
        this.home = this;

        // add listeners on buttons to display corresponding dialog to allow signIn\signUp
        signInHomeBtn.addActionListener(new SignInUpHomeBtnListener());
        signUpHomeBtn.addActionListener(new SignInUpHomeBtnListener());

        // Listen for when the selection changes in coursesTree.
        coursesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        coursesTree.addTreeSelectionListener(new TreeNodeSelectListener(this));
        coursesTree.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                coursesTree.clearSelection();
            }
        });

        // add listeners to allow features of corresponding buttons
        settingsButton.addActionListener(new SettingsBtnListener(this));
        searchButton.addActionListener(new SearchButtonListener());
    }

    public static void main(String[] args) {
        // creating frame from Home class, its only instance in JVM
        JFrame frame = new JFrame("Home");
        frame.setContentPane(new Home().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        util = new Utilities();
        // creating combo box on coursesTreeTop panel to select account type before signIn/Up
        String accountType[]={"Student", "Teacher"};
        accountTypeComboBox = new JComboBox(accountType);

        // creating coursesTree on side panel using utility function
        DefaultMutableTreeNode coursesTreeTop = new DefaultMutableTreeNode("Courses");
        util.createNodes(coursesTreeTop);
        coursesTree = new JTree(coursesTreeTop);

        // creating signOut button to be displayed when a student or teacher signs in
        signOutBtn = new JButton("Sign Out");
        signOutBtn.addActionListener(new SignOutListener());
    }

    // getters
    public JPanel getHomePanel() {
        return homePanel;
    }

    public JTree getCoursesTree() {
        return coursesTree;
    }

    public JTabbedPane getContentDisplayTabbedPane() {
        return contentDisplayTabbedPane;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JComboBox getAccountTypeComboBox() {
        return accountTypeComboBox;
    }

    public JButton getSignInHomeBtn() {
        return signInHomeBtn;
    }

    public JButton getSignUpHomeBtn() {
        return signUpHomeBtn;
    }

    public JButton getSignOutBtn() {
        return signOutBtn;
    }

    public JToolBar getTopToolBar() {
        return topToolBar;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public Utilities getUtil() {
        return util;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setAccountTypeComboBox(JComboBox accountTypeComboBox) {
        this.accountTypeComboBox = accountTypeComboBox;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public static Home getHome() {
        return home;
    }

}
