package com.samyak;

import com.samyak.components.UploadVideoDialog;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeChar(85);

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

                // SQL
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
                String sql = "INSERT INTO videos (`subtopic_id`, `name`, `path`) VALUES (?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, ((Subtopic) dialog.getSubtopicsComboBox().getSelectedItem()).getSubtopicId());
                stmt.setString(2, dialog.getVideoNameField().getText().trim());
                stmt.setString(3, file.getName());
                stmt.executeUpdate();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
