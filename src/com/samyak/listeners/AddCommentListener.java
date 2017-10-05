package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.CommentForm;
import com.samyak.components.ErrorMsgDisplay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddCommentListener implements ActionListener {
    private CommentForm commentForm;

    public AddCommentListener(CommentForm commentForm) {
        this.commentForm = commentForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String comment = commentForm.getCommentTextPane().getText().trim();
            int userId = Home.getHome().getUserId();
            if (comment.equals(""))
                throw new Exception("Comment Empty.");
            if (userId == -1)
                throw new Exception("SignIn/Up to comment.");

            // SQL to store comment of new user in database
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "INSERT INTO `comments` (`parent_id`, `video_id`, `comment`, `user_id`, `user_type`) VALUES (-1, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(commentForm.getVideoId()));
            stmt.setString(2, comment);
            stmt.setInt(3, userId);
            stmt.setString(4, Home.getHome().getUserType());
            stmt.executeUpdate();

            // comment inserted successfully
            new ErrorMsgDisplay("Commented Successfully!!!", (Component) e.getSource());
            con.close();

            commentForm.getCommentTextPane().setText("");
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Component) e.getSource());
        }

    }
}
