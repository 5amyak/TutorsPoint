package com.samyak.listeners;

import com.samyak.UploadThread;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.UploadVideoDialog;

import javax.swing.*;
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
        String videoName = dialog.getVideoNameField().getText().trim();
        if (videoName.equals("")) {
            new ErrorMsgDisplay("* marked fields are mandatory.", dialog.getContentPane());
            return;
        }
        if (dialog.getSubtopicsComboBox().getItemCount() == 0) {
            new ErrorMsgDisplay("Add subtopics to your courses before uploading videos.", dialog.getContentPane());
            return;
        }


        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog((Component)e.getSource());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println(file.getName());

            //This is where a real application would open the file.
            Thread uploadThread = new Thread(new UploadThread(file, dialog));
            uploadThread.start();

            dialog.dispose();
        }
    }
}
