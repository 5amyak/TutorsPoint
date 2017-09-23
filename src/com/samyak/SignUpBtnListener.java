package com.samyak;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUpBtnListener implements ActionListener {
    SignUp signUp;

    public SignUpBtnListener(SignUp signUp) {
        this.signUp = signUp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Storing Form Fields
            String name = signUp.getName().getText().trim();
            String email = signUp.getEmail().getText().trim();
            String passwd = "";
            for (char c: signUp.getPasswd().getPassword()) {
                passwd += c;
            }
            String gender = "";
            if (signUp.getMaleRadioButton().isSelected())
                gender = "Male";
            else if (signUp.getFemaleRadioButton().isSelected())
                gender = "Female";

            // Fields Checking
            if (gender == "" || name == "")
                throw new Exception("Fill Form Properly");
            String regexp = "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
            if (!passwd.matches(regexp))
                throw new Exception("Password should be at least one capital letter, one small letter, one number and 8 character length.");
            regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            if (!email.matches(regexp))
                throw new Exception("Enter a Valid E-Mail.");

            // Hashing Password
            PasswordAuthentication authentication = new PasswordAuthentication();
            passwd = authentication.hash(passwd.toCharArray());

            // SQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javaproject","root","");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO `users`(`name`, `email`, `gender`, `password`) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, gender);
            stmt.setString(4, passwd);
            stmt.executeUpdate();

            // data inserted successfully
            new ErrorMsgDisplay("Successfully Signed Up!!!", signUp.getSignUpForm());
            con.close();
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), signUp.getSignUpForm());
        }

    }
}
