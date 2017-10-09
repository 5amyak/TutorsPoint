package com.samyak.listeners;

import java.sql.Connection;

import com.samyak.Home;
import com.samyak.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteVideoListener implements ActionListener {
    private JPanel statisticsPanel;
    private JDialog dialog;

    public DeleteVideoListener(JPanel statisticsPanel, JDialog dialog) {
        this.statisticsPanel = statisticsPanel;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");

            String sql = "DELETE FROM videos WHERE video_id = ?;";
//            sql = "DELETE\n" +
//                    "    videos,\n" +
//                    "    video_likes,\n" +
//                    "    video_tags,\n" +
//                    "    comments\n" +
//                    "FROM\n" +
//                    "    videos\n" +
//                    "JOIN video_likes ON video_likes.video_id = videos.video_id\n" +
//                    "JOIN video_tags ON videos.video_id = video_tags.video_id\n" +
//                    "JOIN comments ON comments.video_id = videos.video_id\n" +
//                    "WHERE\n" +
//                    "    videos.video_id = 1";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            sql = "DELETE FROM video_tags WHERE video_id = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            sql = "DELETE FROM video_likes WHERE video_id = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            sql = "DELETE FROM comments WHERE video_id = ?;";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            // data deleted successfully
            new ErrorMsgDisplay("Successfully Removed from list!!!", (Component) e.getSource());
            con.close();
            Home.getHome().getUtil().createStatisticsPanel(statisticsPanel, dialog);

        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Container) e.getSource());
        }
    }
}
