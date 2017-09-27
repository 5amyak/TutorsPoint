package com.samyak;

import com.samyak.components.ErrorMsgDisplay;
import com.samyak.components.UploadVideoDialog;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UploadThread implements Runnable {

    private File file;
    private UploadVideoDialog dialog;

    public UploadThread(File file, UploadVideoDialog dialog) {
        this.file = file;
        this.dialog = dialog;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 5000);

            // SQL to get video_id of next entry
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = 'videos'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int nextVideoId = rs.getInt(1);

            // SQL to insert available info about video on database
            sql = "INSERT INTO videos (video_id, subtopic_id, name, path) VALUES(?, ?, ?, \"\")";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, nextVideoId);
            stmt.setInt(2, ((Subtopic) dialog.getSubtopicsComboBox().getSelectedItem()).getSubtopicId());
            stmt.setString(3, dialog.getVideoNameField().getText().trim());
            stmt.executeUpdate();
            con.close();

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(nextVideoId);

            // uploading file and sending to server
            System.out.println("Uploading File: " + file.getName());
            FileInputStream fin = new FileInputStream(file);
            int size;
            do {
                byte b[] = new byte[1024];
                size = fin.read(b);
                System.out.println("Read: " + size);
                dos.write(b);
            } while (size > 0);
            System.out.println("File Send from client.");
            System.out.println("Upload Thread ended.");
            fin.close();
            dos.close();
            socket.close();

            new ErrorMsgDisplay(String.format("%s uploaded successfully", file.getName()), Home.getHome().getHomePanel());
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorMsgDisplay(e.getMessage(), Home.getHome().getHomePanel());
        }

        dialog.getButtonUploadVideo().setEnabled(true);
    }
}
