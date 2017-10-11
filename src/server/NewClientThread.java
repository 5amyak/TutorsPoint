package server;

import java.io.*;
import java.sql.*;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewClientThread implements Runnable {
    Socket socket;

    public NewClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Client connected on Port: " + socket.getPort());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int nextVideoId = dis.readInt();
            String fileExtension = dis.readUTF();
            System.out.println(fileExtension);
            System.out.println(nextVideoId);

//            if (nextVideoId == -1) {
//                // repeatedly taking input and sending to server
//                System.out.println("Sending File from Server to Client:");
//
//                String fileName = dis.readUTF();
//                FileInputStream fin = new FileInputStream(new File(fileName));
//                int size;
//                do {
//                    byte b[] = new byte[1024];
//                    size  = fin.read(b);
//                    System.out.println("Read: " + size);
//                    dos.write(b);
//                }while (size > 0);
//
//                fin.close();
//            }

            //Starting thread that will continuosly listen for any input from server
//            Thread receivingThread = new Thread(new ReceiveThread(socket, nextVideoId));
//            receivingThread.start();
//            receivingThread.join();

            System.out.println("Receiving from server.");
            dis = new DataInputStream(socket.getInputStream());
            // Reading file and copying it into new file on client side
            String path = "server_tutorials/";
            path += new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            path += fileExtension;

            // SQL to update path for video on server
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "UPDATE videos SET path=? WHERE video_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, path);
            stmt.setInt(2, nextVideoId);
            stmt.executeUpdate();

            con.close();

            String fileName = "E://xampp/htdocs/";
            fileName += path;
            File myfile = new File(fileName);

            File parentDir = myfile.getParentFile();
            if (!parentDir.exists())
                parentDir.mkdirs();
            FileOutputStream fout = new FileOutputStream(fileName);
            int size;
            do {
                byte b[] = new byte[1024];
                size = dis.read(b);
                fout.write(b);
//                System.out.println("Received:" + size);
            } while (size > 0);

            // data inserted successfully
            System.out.println("Video Uploaded!!!");

            fout.close();
            dis.close();
            System.out.println("Receive Thread ended.");
            dos.close();
            socket.close();
            System.out.println("New Client Thread on Server ended");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
                String sql = "DELETE FROM videos WHERE path=''";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.executeUpdate();
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}