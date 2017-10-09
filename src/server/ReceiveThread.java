package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveThread implements Runnable {
    private Socket socket;
    private int nextVideoId;

    public ReceiveThread(Socket socket, int nextVideoId) {
        this.socket = socket;
        this.nextVideoId = nextVideoId;
    }

    public void run() {
        DataInputStream dis;
        Connection con;
        try {
            System.out.println("Receiving from server.");
            dis = new DataInputStream(socket.getInputStream());
            // Reading file and copying it into new file on client side
            String path = "server_tutorials\\";
            path += new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            path += ".mp4";
            String fileName = "E:\\xampp\\htdocs\\";
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
                System.out.println("Received:" + size);
            } while (size > 0);

            // data inserted successfully
            System.out.println("Video Uploaded!!!");

            fout.close();
            dis.close();

            // SQL to update path for video on server
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "UPDATE videos SET path=? WHERE video_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, path);
            stmt.setInt(2, nextVideoId);
            stmt.executeUpdate();

            System.out.println("Receive Thread ended.");
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
                String sql = "DELETE FROM videos WHERE path=''";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.executeUpdate();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
