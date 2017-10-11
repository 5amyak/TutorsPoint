package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.components.ErrorMsgDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveCommentListener implements ActionListener {

    private JPanel commentsPanel;
    private String videoId;

    public RemoveCommentListener(JPanel commentsPanel, String videoId) {
        this.commentsPanel = commentsPanel;
        this.videoId = videoId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || Home.getHome().getUserType().equals("")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up.", (Component) e.getSource());
            return;
        }

        try {
            int commentId = Integer.parseInt(((JButton) e.getSource()).getName());
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;
            PreparedStatement stmt = con.prepareStatement("SELECT user_id, user_type FROM comments WHERE comment_id=?");
            stmt.setInt(1, commentId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) != Home.getHome().getUserId() || !rs.getString(2).equals(Home.getHome().getUserType()))
                throw new Exception("You cannot remove others comments.");

            stmt = con.prepareStatement("DELETE FROM comments WHERE comment_id=?");
            stmt.setInt(1, commentId);
            stmt.executeUpdate();

            new ErrorMsgDisplay("Successfully removed comment.", (Component) e.getSource());
            Home.getHome().getUtil().createCommentsPanel(commentsPanel, videoId);
        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), Home.getHome().getHomePanel());
        }
    }
}
