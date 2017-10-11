package com.samyak.listeners;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class WatchLaterBtnListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        // SQL to store user_id and video_id of liked video
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            if (((JButton) e.getSource()).getText().equals("Add to WatchList")) {
                String sql = "INSERT INTO `watchlist`(`student_id`, `video_id`) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data inserted successfully
                new ErrorMsgDisplay("Successfully Added to Watch List!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Remove from WatchList");
            } else {
                String sql = "DELETE FROM `watchlist` WHERE `student_id`=? AND `video_id`=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data deleted successfully
                new ErrorMsgDisplay("Successfully Removed from Watchlist!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Add to WatchList");
            }


            con.close();

        } catch (MySQLIntegrityConstraintViolationException e1) {
            new ErrorMsgDisplay("Already added to WatchList. Press again to remove.", (Container) e.getSource());
            ((JButton) e.getSource()).setText("Remove from WatchList");
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Container) e.getSource());
        }
    }
}
