package com.samyak;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignInListener implements ActionListener {
     private SignInDialog signInDialog;

    public SignInListener(SignInDialog signInDialog) {
        this.signInDialog = signInDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Storing Form Fields
            String dbName = signInDialog.getDbName();
            String email = signInDialog.getEmail().getText().trim();
            String passwd = "";
            for (char c: signInDialog.getPasswd().getPassword()) {
                passwd += c;
            }

            // Field Validation
            String regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            if (!email.matches(regexp))
                throw new Exception("Enter a Valid E-Mail.");
            regexp = "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
            if (!passwd.matches(regexp))
                throw new Exception("Password should be at least one capital letter, one small letter, one number and 8 character length.");

            // SQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint","root","");
            PreparedStatement stmt = con.prepareStatement(String.format("SELECT name, email, gender, password FROM %s WHERE email = ?", dbName));
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            // if record found using email
            if (rs.next()) {
                // Validate Password
                PasswordAuthentication authentication = new PasswordAuthentication();
                if (!authentication.authenticate(passwd.toCharArray(), rs.getString(4)))
                    throw new Exception("Email or Password is Incorrect.");
                System.out.println("Name: " + rs.getString(1) + " E-Mail: " + rs.getString(2) + " Gender: " + rs.getString(3));
            }
            else
                throw new Exception("Email or Password is Incorrect.");

            // All OK
            new ErrorMsgDisplay("Successfully Signed In!!!", (Component)e.getSource());
            con.close();
            signInDialog.onCancel();
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Component)e.getSource());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
