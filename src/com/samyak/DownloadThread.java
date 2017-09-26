package com.samyak;

import com.samyak.components.MediaPlayer;
import com.samyak.components.PlayButton;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadThread implements Runnable {

    PlayButton playButton;

    public DownloadThread(PlayButton playButton) {
        this.playButton = playButton;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 5000);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeChar(68);
            dos.writeUTF(playButton.getVideoPath());
            System.out.println("File Name Send from client.");

            // downloading file from server
            System.out.println("Downloading File: " + playButton.getVideoName());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            File checkDir = new File("client_tutorials");
            if (!checkDir.exists())
                checkDir.mkdir();
            String filePath = "client_tutorials/" + playButton.getVideoName() + Integer.toString(playButton.getVideoId()) + ".mp4";
            FileOutputStream fout = new FileOutputStream(filePath);
            int size;
            do {
                byte b[] = new byte[1024];
                size = dis.read(b);
                fout.write(b);
                System.out.println("Received:" + size);
            }while (size > 0);
            System.out.println("Download Thread in Client ended.");
            fout.close();
            dos.close();
            dis.close();
            socket.close();

            SwingUtilities.invokeLater(() -> {
                playButton.setClientVideoPath(filePath);
                new MediaPlayer(playButton);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
