package com.samyak.components;

import com.samyak.Home;
import com.samyak.listeners.AddCommentListener;

import javax.swing.*;

public class CommentForm {
    private JButton commentButton;
    private JPanel commentsPane;
    private JPanel contentPane;
    private JTextPane commentTextPane;
    private JList commentList;
    private String videoId;

    public CommentForm(String videoId) {
        this.videoId = videoId;
        commentButton.addActionListener(new AddCommentListener(this));
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JTextPane getCommentTextPane() {
        return commentTextPane;
    }

    public void setCommentTextPane(JTextPane commentTextPane) {
        this.commentTextPane = commentTextPane;
    }

    public JButton getCommentButton() {
        return commentButton;
    }

    public void setCommentButton(JButton commentButton) {
        this.commentButton = commentButton;
    }

    public JPanel getCommentsPane() {
        return commentsPane;
    }

    public void setCommentsPane(JPanel commentsPane) {
        this.commentsPane = commentsPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    private void createUIComponents() {
        DefaultListModel<String> commentListModel = new DefaultListModel<>();
//        commentListModel.add(new CommentListItem());

        commentListModel.addElement("one");
        commentList = new JList(commentListModel);
    }
}
