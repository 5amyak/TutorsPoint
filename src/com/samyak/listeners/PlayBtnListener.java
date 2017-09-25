package com.samyak.listeners;

import com.samyak.DownloadThread;
import com.samyak.components.MediaPlayer;
import com.samyak.components.PlayButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PlayBtnListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        PlayButton playButton = (PlayButton)e.getSource();
        String filePath = "client_tutorials\\" + playButton.getVideoName() + Integer.toString(playButton.getVideoId()) + ".mp4";
        if (!new File(filePath).isFile()) {
            try {
                Thread downloadThread = new Thread(new DownloadThread(playButton));
                downloadThread.start();
                downloadThread.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
        }
        playButton.setClientVideoPath(filePath);
        SwingUtilities.invokeLater(() -> new MediaPlayer(playButton));

    }
}
