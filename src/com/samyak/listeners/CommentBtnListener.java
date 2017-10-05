package com.samyak.listeners;

import com.samyak.components.CommentForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentBtnListener implements ActionListener {
    private CommentForm commentForm;
    private JFrame frame;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame == null || commentForm == null || !frame.isDisplayable()) {
            this.commentForm = new CommentForm(((JButton) e.getSource()).getName());

            frame = new JFrame("Comment");
            frame.setContentPane(commentForm.getContentPane());
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else
            frame.setVisible(true);
    }
}
