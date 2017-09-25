package com.samyak;

import com.samyak.includes.Utilities;
import com.samyak.listeners.SignInHomeBtnListener;
import com.samyak.listeners.SignUpHomeBtnListener;
import com.samyak.listeners.TreeNodeSelectListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Home {
    private JPanel homePanel;
    private JTree coursesTree;
    private JTabbedPane contentDisplayTabbedPane;
    private JButton searchButton;
    private JComboBox accountTypeComboBox;
    private JButton signInHomeBtn;
    private JButton signUpHomeBtn;
    private JToolBar topToolBar;
    private JTextField searchTextField;
    private JButton settingsButton;
    private Utilities util;
    private static String userName;
    private static int userId;

    public Home() {
        util = new Utilities();

        signInHomeBtn.addActionListener(new SignInHomeBtnListener(this));
        signUpHomeBtn.addActionListener(new SignUpHomeBtnListener(this));

        // Listen for when the selection changes in coursesTree.
        coursesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        coursesTree.addTreeSelectionListener(new TreeNodeSelectListener(this));
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

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Courses");
        createNodes(top);
        coursesTree = new JTree(top);
    }

    public void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode course;
        DefaultMutableTreeNode subtopic;

        // SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT course_id, name FROM courses");
            ResultSet rs = stmt.executeQuery();

            // if record found using email
            while (rs.next()) {
                int course_id = rs.getInt(1);
                course = new DefaultMutableTreeNode(rs.getString(2));
                top.add(course);
                stmt = con.prepareStatement("SELECT subtopic_id, name, description FROM subtopics WHERE course_id = ?");
                stmt.setInt(1, course_id);
                ResultSet nrs = stmt.executeQuery();
                while(nrs.next()) {
                    subtopic = new DefaultMutableTreeNode(new Subtopic(nrs.getInt(1), nrs.getString(2), nrs.getString(3)));
                    course.add(subtopic);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static String getUserName() {
        return userName;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserName(String userName) {
        Home.userName = userName;
    }

    public static void setUserId(int userId) {
        Home.userId = userId;
    }
}
