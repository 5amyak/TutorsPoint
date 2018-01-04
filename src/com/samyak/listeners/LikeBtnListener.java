package com.samyak.listeners;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LikeBtnListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("student")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Student.", (Component) e.getSource());
            return;
        }

        // SQL to store user_id and video_id of liked video
        try {
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            if (((JButton) e.getSource()).getText().equals("Like")) {
                String sql = "INSERT INTO `video_likes`(`student_id`, `video_id`) VALUES (?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                sql = "UPDATE `videos` SET `likes`= `likes`+1 WHERE `video_id`=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data inserted successfully
                new ErrorMsgDisplay("Successfully Liked Video!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Unlike");
            } else {
                String sql = "DELETE FROM `video_likes` WHERE `student_id`=? AND `video_id`=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, Home.getHome().getUserId());
                stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                sql = "UPDATE `videos` SET `likes`= `likes`-1 WHERE `video_id`=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
                stmt.executeUpdate();

                // data deleted successfully
                new ErrorMsgDisplay("Successfully UnLiked Video!!!", (Component) e.getSource());
                ((JButton) e.getSource()).setText("Like");
            }
            con.close();

        } catch (MySQLIntegrityConstraintViolationException e1) {
            new ErrorMsgDisplay("You've already liked the video. Press Again to unlike.", (Component) e.getSource());
            ((JButton) e.getSource()).setText("Unlike");
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Component) e.getSource());
        }
    }

}