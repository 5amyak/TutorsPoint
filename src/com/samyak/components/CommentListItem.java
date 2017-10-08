package com.samyak.components;

import java.sql.*;

public class CommentListItem {
    private int comment_id;
    private int user_id;
    private int video_id;
    private String comment;
    private String user_type;
    private Timestamp timestamp;

    public CommentListItem(int comment_id, int user_id, int video_id, String comment, String user_type, Timestamp timestamp) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.video_id = video_id;
        this.comment = comment;
        this.user_type = user_type;
        this.timestamp = timestamp;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        String name = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "";
            if (user_type.equals("student"))
                sql = "SELECT name FROM students WHERE student_id = ?";
            else
                sql = "SELECT name FROM teachers WHERE teacher_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            name = rs.getString(1);
            name.toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), null);
        }
        return (name + "\n    " + getComment());
    }
}
