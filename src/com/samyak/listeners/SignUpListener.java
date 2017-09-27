package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.includes.PasswordAuthentication;
import com.samyak.components.SignUpDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUpListener implements ActionListener {
    private SignUpDialog signUpDialog;

    public SignUpListener(SignUpDialog signUpDialog) {
        this.signUpDialog = signUpDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Storing Form Fields
            String dbName = signUpDialog.getDbName();
            String name = signUpDialog.getUserName().getText().trim();
            String email = signUpDialog.getEmail().getText().trim();
            StringBuilder passwdBuilder = new StringBuilder();
            for (char c: signUpDialog.getPasswd().getPassword()) {
                passwdBuilder.append(c);
            }
            String passwd = passwdBuilder.toString();
            String gender = "";
            if (signUpDialog.getMaleRadioButton().isSelected())
                gender = "Male";
            else if (signUpDialog.getFemaleRadioButton().isSelected())
                gender = "Female";

            // Fields Checking
            if (gender.equals("") || name.equals(""))
                throw new Exception("Fill Form Completely");
            String regexp = "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
            if (!passwd.matches(regexp))
                throw new Exception("Password should be at least one capital letter, one small letter, one number and 8 character length.");
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            if (!email.matches(regexp))
                throw new Exception("Enter a Valid E-Mail.");

            // Hashing Password
            PasswordAuthentication authentication = new PasswordAuthentication();
            passwd = authentication.hash(passwd.toCharArray());

            // SQL to store data of new user
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint","root","");
            String sql = "INSERT INTO " + dbName + " (`name`, `email`, `gender`, `password`) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, gender);
            stmt.setString(4, passwd);
            stmt.executeUpdate();

            String idType;
            if (dbName.equals("students"))
                idType = "student_id";
            else
                idType = "teacher_id";
            stmt = con.prepareStatement(String.format("SELECT %s FROM %s WHERE email = ?", idType, dbName));
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Home.getHome().setUserId(rs.getInt(1));
            Home.getHome().setUserName(name);

            if (dbName.equals("students"))
                Home.getHome().getAccountTypeComboBox().removeItemAt(1);
            else
                Home.getHome().getAccountTypeComboBox().removeItemAt(0);
            Home.getHome().getTopToolBar().remove(Home.getHome().getSignInHomeBtn());
            Home.getHome().getTopToolBar().remove(Home.getHome().getSignUpHomeBtn());

            // data inserted successfully
            new ErrorMsgDisplay(String.format("%s Successfully Signed Up!!!", name), signUpDialog.getSignUpPanel());
            con.close();
            signUpDialog.onCancel();
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), signUpDialog.getSignUpPanel());
        }

    }
}
