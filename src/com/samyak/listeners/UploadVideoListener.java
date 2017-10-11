package com.samyak.listeners;

import com.samyak.Home;
import com.samyak.UploadThread;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.UploadVideoDialog;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UploadVideoListener implements ActionListener {
    private UploadVideoDialog dialog;

    public UploadVideoListener(UploadVideoDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Home.getHome().getUserId() == -1 || Home.getHome().getUserName().equals("") || !Home.getHome().getUserType().equals("teacher")) {
            new ErrorMsgDisplay("Not Signed in. Sign in or Sign up as a Teacher.", (Component) e.getSource());
            return;
        }

        String videoName = dialog.getVideoNameField().getText().trim();
        if (dialog.getCoursesComboBox().getItemCount() == 0) {
            new ErrorMsgDisplay("Add courses before uploading videos.", dialog.getContentPane());
            return;
        }
        if (dialog.getSubtopicsComboBox().getItemCount() == 0) {
            new ErrorMsgDisplay("Add subtopics to your courses before uploading videos.", dialog.getContentPane());
            return;
        }
        if (videoName.equals("")) {
            new ErrorMsgDisplay("* marked fields are mandatory.", dialog.getContentPane());
            return;
        }


        JFileChooser fc = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("Video files", "mp4", "mkv", "mp3");
        fc.setFileFilter(fileFilter);
        int returnVal = fc.showOpenDialog((Component)e.getSource());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println(file.getName());
            if (!fileFilter.accept(file)) {
                new ErrorMsgDisplay("Please upload video files only.", (Component) e.getSource());
                return;
            }

            //This is where a real application would open the file.
            Thread uploadThread = new Thread(new UploadThread(file, dialog));
            uploadThread.start();

            dialog.dispose();
        }
    }
}
