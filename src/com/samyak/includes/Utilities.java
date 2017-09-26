package com.samyak.includes;

import com.samyak.Subtopic;
import com.samyak.components.PlayButton;
import com.samyak.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Utilities {

    public JScrollPane createVideoTab(Subtopic subtopic) {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab
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

        // SQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT video_id, name, path FROM videos WHERE subtopic_id=?");
            stmt.setInt(1, subtopic.getSubtopicId());
            ResultSet rs = stmt.executeQuery();

            // if record found using email
            while (rs.next()) {
                GridBagConstraints c = new GridBagConstraints();
                JPanel videoPanel = new JPanel();

                videoPanel.setLayout(new GridBagLayout());
                c.gridx = 0;
                c.gridy = 0;
                c.insets = new Insets(5, 3, 5, 3);
                JLabel videoNameLabel = new JLabel(rs.getString(2));
                videoPanel.add(videoNameLabel, c);
                c.gridx = 0;
                c.gridy = GridBagConstraints.RELATIVE;
                JButton playBtn = new PlayButton(rs.getInt(1), rs.getString(2), rs.getString(3));
                playBtn.addActionListener(new PlayBtnListener());
                videoPanel.add(playBtn, c);

                tabPanel.add(videoPanel, gbc);
                gbc.gridy++;
                if (gbc.gridy == 4) {
                    gbc.gridy = 0;
                    gbc.gridx++;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scrollPane;
    }

    public JScrollPane createTeacherSettingsTab() {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab
        // add tabPanel on scrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setName("settings");
        tabPanel.setLayout(new GridBagLayout());

        // info about position, padding, look of component
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
        JButton updateProfileBtn = new JButton("Update Profile");
        tabPanel.add(updateProfileBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton manageContentBtn = new JButton("Manage Content");
        tabPanel.add(manageContentBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton uploadVideoBtn = new JButton("Upload Video");
        uploadVideoBtn.addActionListener(new UploadVideoListener());
        tabPanel.add(uploadVideoBtn, gbc);

        return scrollPane;
    }

}
