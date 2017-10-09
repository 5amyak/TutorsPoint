package com.samyak.includes;

import com.samyak.Course;
import com.samyak.Home;
import com.samyak.Subtopic;
import com.samyak.components.*;
import com.samyak.listeners.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Utilities {

    private void createVideoPanels(ArrayList<PlayButton> playButtons, JPanel tabPanel) {
        // creating video panel for each video in a subtopic
        // like and watch later buttons have setName() to get video id later
        // info about position, padding, look of component
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        for (int i = 0; i < playButtons.size(); i++) {

            JPanel videoPanel = new JPanel();
            GridBagConstraints c = new GridBagConstraints();

            videoPanel.setLayout(new GridBagLayout());
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.insets = new Insets(5, 3, 5, 3);
            JLabel videoNameLabel = new JLabel(playButtons.get(i).getVideoName());
            videoPanel.add(videoNameLabel, c);
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            JButton playBtn = playButtons.get(i);
            playBtn.addActionListener(new PlayBtnListener());
            videoPanel.add(playBtn, c);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            JButton likeBtn = new JButton("Like");
            likeBtn.addActionListener(new LikeBtnListener());
            likeBtn.setName(Integer.toString(playButtons.get(i).getVideoId()));
            videoPanel.add(likeBtn, c);
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 2;
            JButton watchLaterBtn = new JButton("Add to WatchList");
            watchLaterBtn.addActionListener(new WatchLaterBtnListener());
            watchLaterBtn.setName(Integer.toString(playButtons.get(i).getVideoId()));
            videoPanel.add(watchLaterBtn, c);
            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 2;
            JButton commentBtn = new JButton("Comment");
            commentBtn.addActionListener(new CommentBtnListener());
            commentBtn.setName(Integer.toString(playButtons.get(i).getVideoId()));
            videoPanel.add(commentBtn, c);

            tabPanel.add(videoPanel, gbc);
            gbc.gridx++;
            if (gbc.gridx == 3) {
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
    }

    public JScrollPane createVideoTab(Subtopic subtopic) {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab if already opened
        // add tabPanel on scrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setName(Integer.toString(subtopic.getSubtopicId() * -1));
        tabPanel.setLayout(new GridBagLayout());

        // all play buttons corresponding to the video tab
        ArrayList<PlayButton> playButtons = new ArrayList<>();

        // SQL to set info about video on its play button and like button
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT video_id, name, path FROM videos WHERE subtopic_id=?");
            stmt.setInt(1, subtopic.getSubtopicId());
            ResultSet rs = stmt.executeQuery();

            // creating play buttons for each video in a subtopic
            while (rs.next()) {
                playButtons.add(new PlayButton(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            con.close();

            createVideoPanels(playButtons, tabPanel);

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }

        return scrollPane;
    }

    public JScrollPane createCourseTab(Course course) {
        JPanel tabPanel = new JPanel();
        // setName on scrollPane to uniquely identify the tab if already opened
        // add tabPanel on scrollPane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(tabPanel);
        scrollPane.setName(Integer.toString(course.getCourseId()));

        // SQL to set info about video on its play button and like button
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            PreparedStatement stmt = con.prepareStatement("SELECT name, email FROM teachers WHERE teacher_id=?");
            stmt.setInt(1, course.getTeacherId());
            ResultSet rs = stmt.executeQuery();

            // creating video panel for each video in a subtopic
            // like and watch later buttons have setName() to get video id later
            rs.next();

            // info about position, padding, look of component
            tabPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(3, 5, 3, 5);
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridwidth = 2;
            JLabel courseNameLabel = new JLabel(course.getCourseName());
            courseNameLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
            tabPanel.add(courseNameLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            JLabel aboutCourseLabel = new JLabel(String.format("Course Content is provided by %s. E-mail:  %s", rs.getString(1), rs.getString(2)));
            tabPanel.add(aboutCourseLabel, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            JButton rateCourseBtn = new JButton("Rate Course");
            rateCourseBtn.addActionListener(new StudentDialogBtnListener(new RateCourseDialog(course.getCourseId())));
            rateCourseBtn.setName(Integer.toString(course.getCourseId()));
            tabPanel.add(rateCourseBtn, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            JButton subscribeBtn = new JButton("Subscribe");
            subscribeBtn.addActionListener(new SubscribeBtnListener());
            subscribeBtn.setName(Integer.toString(course.getTeacherId()));
            tabPanel.add(subscribeBtn, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            JButton inProgressCourseBtn = new JButton("Continue Later");
            inProgressCourseBtn.addActionListener(new InProgressCourseBtnListener());
            inProgressCourseBtn.setName(Integer.toString(course.getCourseId()));
            tabPanel.add(inProgressCourseBtn, gbc);

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
        createCourseBtn.addActionListener(new TeacherDialogBtnListener(new CreateCourseDialog()));
        tabPanel.add(createCourseBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton addSubtopicsBtn = new JButton("Add Subtopics");
        addSubtopicsBtn.addActionListener(new TeacherDialogBtnListener(new AddSubtopicDialog()));
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
        uploadVideoBtn.addActionListener(new TeacherDialogBtnListener(new UploadVideoDialog()));
        tabPanel.add(uploadVideoBtn, gbc);

        return scrollPane;
    }

    public JScrollPane createStudentSettingsTab() {
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
        JButton manageSubscriptionsBtn = new JButton("Manage Subscriptions");
        manageSubscriptionsBtn.addActionListener(new StudentDialogBtnListener(new ManageSubscriptionsDialog()));
        tabPanel.add(manageSubscriptionsBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton watchListBtn = new JButton("Watch List");
        watchListBtn.addActionListener(new StudentDialogBtnListener(new WatchListDialog()));
        tabPanel.add(watchListBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton inProgressCoursesBtn = new JButton("In Progress Courses");
        inProgressCoursesBtn.addActionListener(new StudentDialogBtnListener(new InProgressCourseDialog()));
        tabPanel.add(inProgressCoursesBtn, gbc);

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
            PreparedStatement stmt = con.prepareStatement("SELECT course_id, teacher_id, name FROM courses");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int course_id = rs.getInt(1);
                course = new DefaultMutableTreeNode(new Course(rs.getInt(1), rs.getInt(2), rs.getString(3)));
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

    public void createListPanel(JPanel listPanel, JDialog dialog) {
        listPanel.removeAll();
        listPanel.revalidate();
        listPanel.repaint();

        listPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 5, 3, 5);
        c.gridx = 0;
        c.gridy = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "";
            if (dialog instanceof ManageSubscriptionsDialog)
                sql = "SELECT teachers.name, subscriptions.teacher_id\n" +
                        "FROM subscriptions\n" +
                        "INNER JOIN teachers ON teachers.teacher_id=subscriptions.teacher_id\n" +
                        "WHERE subscriptions.student_id=?";
            else if (dialog instanceof WatchListDialog)
                sql = "SELECT\n" +
                        "videos.name,\n" +
                        "watchlist.video_id,\n" +
                        "subtopics.name,\n" +
                        "courses.name\n" +
                        "FROM\n" +
                        "watchlist\n" +
                        "INNER JOIN videos ON videos.video_id = watchlist.video_id\n" +
                        "INNER JOIN subtopics ON subtopics.subtopic_id = videos.subtopic_id\n" +
                        "INNER JOIN courses ON subtopics.course_id = courses.course_id\n" +
                        "WHERE\n" +
                        "watchlist.student_id = ?";
            else if (dialog instanceof InProgressCourseDialog)
                sql = "SELECT courses.name, in_progress_courses.course_id\n" +
                        "FROM in_progress_courses\n" +
                        "INNER JOIN courses ON in_progress_courses.course_id = courses.course_id\n" +
                        "WHERE in_progress_courses.student_id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Home.getHome().getUserId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                c.gridx = 0;
                c.gridwidth = 2;
                c.anchor = GridBagConstraints.WEST;
                JLabel listName;
                if (dialog instanceof WatchListDialog)
                    listName = new JLabel(rs.getString(1).toUpperCase() + "   " + rs.getString(3).toUpperCase() + "   " + rs.getString(4).toUpperCase());
                else
                    listName = new JLabel(rs.getString(1).toUpperCase());
                listPanel.add(listName, c);
                c.gridx = 2;
                c.gridwidth = 1;
                c.anchor = GridBagConstraints.EAST;
                JButton removeBtn = new JButton("Remove");
                removeBtn.setName(Integer.toString(rs.getInt(2)));
                removeBtn.addActionListener(new RemoveListener(listPanel, dialog));
                listPanel.add(removeBtn, c);

                c.gridy++;
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }

    }

//    public void createCommentsTree(DefaultMutableTreeNode commentsTreeTop) {
//
//        // SQL to further create nodes of courses Tree
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
//            PreparedStatement stmt = con.prepareStatement("SELECT * FROM comments");
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                int course_id = rs.getInt(1);
//                course = new DefaultMutableTreeNode(new Course(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4)));
//                top.add(course);
//                stmt = con.prepareStatement("SELECT subtopic_id, name, description FROM subtopics WHERE course_id = ?");
//                stmt.setInt(1, course_id);
//                ResultSet nrs = stmt.executeQuery();
//                while (nrs.next()) {
//                    subtopic = new DefaultMutableTreeNode(new Subtopic(nrs.getInt(1), nrs.getString(2), nrs.getString(3)));
//                    course.add(subtopic);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            new ErrorMsgDisplay(e.getMessage(), null);
//        }
//    }
}
