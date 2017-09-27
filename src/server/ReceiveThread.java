package server;

import com.samyak.Subtopic;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveThread implements Runnable{
    private Socket socket;
    private int nextVideoId;

    public ReceiveThread(Socket socket, int nextVideoId) {
        this.socket = socket;
        this.nextVideoId = nextVideoId;
    }

    public void run(){
        DataInputStream dis;
        try {
            System.out.println("Receiving from server.");
            dis = new DataInputStream(socket.getInputStream());
            // Reading file and copying it into new file on client side
            String fileName = "server_tutorials/";
            fileName += new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            fileName += ".mp4";
            FileOutputStream fout = new FileOutputStream(fileName);
            int size;
            do {
                byte b[] = new byte[1024];
                size = dis.read(b);
                fout.write(b);
                System.out.println("Received:" + size);
            }while (size > 0);

            // data inserted successfully
            System.out.println("Video Uploaded!!!");

            System.out.println("Receive Thread ended.");
            fout.close();
            dis.close();

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint", "root", "");
            String sql = "UPDATE videos SET path = ? WHERE video_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, fileName);
            stmt.setInt(2, nextVideoId);
            stmt.executeUpdate();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
