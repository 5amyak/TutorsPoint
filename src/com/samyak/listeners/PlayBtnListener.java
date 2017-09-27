package com.samyak.listeners;

import com.samyak.DownloadThread;
import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.MediaPlayer;
import com.samyak.components.PlayButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PlayBtnListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        PlayButton playButton = (PlayButton) e.getSource();
        String filePath = "client_tutorials/" + playButton.getVideoName() + Integer.toString(playButton.getVideoId()) + ".mp4";
        createDownloadThread(filePath, playButton);
    }

    private synchronized void createDownloadThread(String filePath, PlayButton playButton) {
        if (playButton.getVideoPath().equals("")) {
            new ErrorMsgDisplay("Please wait while video is getting uploaded.", playButton);
        } else if (!new File(filePath).isFile()) {
            Thread downloadThread = new Thread(new DownloadThread(playButton));
            downloadThread.start();
        }
        else {
            SwingUtilities.invokeLater(() -> {
                playButton.setClientVideoPath(filePath);
                new MediaPlayer(playButton);
            });
        }

    }
}
