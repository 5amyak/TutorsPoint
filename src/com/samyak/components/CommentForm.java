package com.samyak.components;

import com.samyak.Home;
import com.samyak.listeners.AddCommentListener;

import javax.swing.*;

public class CommentForm {
    private JButton commentButton;
    private JPanel commentsPanel;
    private JPanel contentPane;
    private JTextPane commentTextPane;
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

    public JPanel getCommentsPanel() {
        return commentsPanel;
    }

    public void setCommentsPanel(JPanel commentsPanel) {
        this.commentsPanel = commentsPanel;
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
        commentsPanel = new JPanel();

        Home.getHome().getUtil().createCommentsPanel(commentsPanel, videoId);
    }
}
