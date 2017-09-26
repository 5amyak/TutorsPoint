package com.samyak.includes;

import com.samyak.Course;
import com.samyak.Subtopic;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.PlayButton;
import com.samyak.listeners.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.*;

public class Utilities {

    public JScrollPane createVideoTab(Subtopic subtopic) {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab if already opened
        // add tabPanel on scrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setName(Integer.toString(subtopic.getSubtopicId()));
        tabPanel.setLayout(new GridBagLayout());

        // info about position, padding, look of component
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        // SQL to set info about video on its play button and like button
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT video_id, name, path FROM videos WHERE subtopic_id=?");
            stmt.setInt(1, subtopic.getSubtopicId());
            ResultSet rs = stmt.executeQuery();

            // creating video panel for each video in a subtopic
            // like and watch later buttons have setName() to get video id later
            while (rs.next()) {
                JPanel videoPanel = new JPanel();
                GridBagConstraints c = new GridBagConstraints();

                videoPanel.setLayout(new GridBagLayout());
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 2;
                c.insets = new Insets(5, 3, 5, 3);
                JLabel videoNameLabel = new JLabel(rs.getString(2));
                videoPanel.add(videoNameLabel, c);
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                JButton playBtn = new PlayButton(rs.getInt(1), rs.getString(2), rs.getString(3));
                playBtn.addActionListener(new PlayBtnListener());
                videoPanel.add(playBtn, c);
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 1;
                JButton likeBtn = new JButton("Like");
                likeBtn.addActionListener(new LikeBtnListener());
                likeBtn.setName(Integer.toString(rs.getInt(1)));
                videoPanel.add(likeBtn, c);
                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 2;
                JButton watchLaterBtn = new JButton("Watch Later");
                watchLaterBtn.addActionListener(new WatchLaterBtnListener());
                watchLaterBtn.setName(Integer.toString(rs.getInt(1)));
                videoPanel.add(watchLaterBtn, c);

                tabPanel.add(videoPanel, gbc);
                gbc.gridx++;
                if (gbc.gridx == 5) {
                    gbc.gridx = 0;
                    gbc.gridy++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }

        return scrollPane;
    }

    public JScrollPane createTeacherSettingsTab() {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab if already opened
        // add tabPanel on scrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setName("Settings");
        tabPanel.setLayout(new GridBagLayout());

        // info about position, padding, look of component
        // adding buttons on panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        JButton createCourseBtn = new JButton("Create Course");
        createCourseBtn.addActionListener(new CreateCourseBtnListener());
        tabPanel.add(createCourseBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton addSubtopicsBtn = new JButton("Add Subtopics");
        addSubtopicsBtn.addActionListener(new AddSubtopicBtnListener());
        tabPanel.add(addSubtopicsBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton getStatisticsBtn = new JButton("Get Statistics");
        tabPanel.add(getStatisticsBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton manageContentBtn = new JButton("Manage Content");
        tabPanel.add(manageContentBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton uploadVideoBtn = new JButton("Upload Video");
        uploadVideoBtn.addActionListener(new UploadVideoTabBtnListener());
        tabPanel.add(uploadVideoBtn, gbc);

        return scrollPane;
    }

    public void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode course;
        DefaultMutableTreeNode subtopic;

        // SQL to further create nodes of courses Tree
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT course_id, teacher_id, name, avg_rating FROM courses");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int course_id = rs.getInt(1);
                course = new DefaultMutableTreeNode(new Course(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
                top.add(course);
                stmt = con.prepareStatement("SELECT subtopic_id, name, description FROM subtopics WHERE course_id = ?");
                stmt.setInt(1, course_id);
                ResultSet nrs = stmt.executeQuery();
                while (nrs.next()) {
                    subtopic = new DefaultMutableTreeNode(new Subtopic(nrs.getInt(1), nrs.getString(2), nrs.getString(3)));
                    course.add(subtopic);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }
    }


}
