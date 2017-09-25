package com.samyak.components;

import javax.swing.*;

public class PlayButton extends JButton {
    private int videoId;
    private String videoPath;

    public PlayButton(int videoId, String videoPath) {
        super("Play");
        this.videoId = videoId;
        this.videoPath = videoPath;
    }

    public int getVideoId() {
        return videoId;
    }

    public String getVideoPath() {
        return videoPath;
    }
}
