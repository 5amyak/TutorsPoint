package com.samyak.components;

import javax.swing.*;

public class PlayButton extends JButton {
    private int videoId;
    private String videoName;
    private String videoPath;

    public PlayButton(int videoId, String videoName, String videoPath) {
        super("Play");
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoPath = videoPath;
    }

    public int getVideoId() {
        return videoId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getVideoName() {
        return videoName;
    }
}
