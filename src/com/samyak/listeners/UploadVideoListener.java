package com.samyak.listeners;

import com.samyak.UploadThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UploadVideoListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog((Component)e.getSource());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println(file.getName());

            //This is where a real application would open the file.
            Thread uploadThread = new Thread(new UploadThread(file));
            uploadThread.start();
        }
    }
}
