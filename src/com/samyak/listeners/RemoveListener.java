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

// remove item from the list
public class RemoveListener implements ActionListener {
    private JPanel listPanel;
    private JDialog dialog;

    public RemoveListener(JPanel listPanel, JDialog dialog) {
        this.listPanel = listPanel;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = Home.getHome().getUtil().getConnection();
            if (con == null)
                return;

            String sql = "";
            if (dialog instanceof ManageSubscriptionsDialog)
                sql = "DELETE FROM `subscriptions` WHERE `student_id`=? AND `teacher_id`=?";
            else if (dialog instanceof WatchListDialog)
                sql = "DELETE FROM `watchlist` WHERE `student_id`=? AND `video_id`=?";
            else if (dialog instanceof InProgressCourseDialog)
                sql = "DELETE FROM `in_progress_courses` WHERE `student_id`=? AND `course_id`=?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            stmt.setInt(2, Integer.parseInt(((JButton) e.getSource()).getName()));
            stmt.executeUpdate();

            // data deleted successfully
            new ErrorMsgDisplay("Successfully Removed from list!!!", (Component) e.getSource());
            con.close();
            Home.getHome().getUtil().createListPanel(listPanel, dialog);

        } catch (Exception e1) {
            e1.printStackTrace();
            new ErrorMsgDisplay(e1.getMessage(), (Container) e.getSource());
        }
    }
}
