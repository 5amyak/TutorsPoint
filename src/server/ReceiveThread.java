package server;

import com.samyak.components.ErrorMsgDisplay;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveThread implements Runnable{
    Socket socket;
    public void run(){
        DataInputStream dis = null;
        try {
            System.out.println("Receiving from server.");
            dis = new DataInputStream(socket.getInputStream());
            // Reading file and copying it into new file on client side
            String fileName = "server_tutorials\\";
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

            // SQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tutorspoint","root","");
            String sql = "INSERT INTO videos (`subtopic_id`, `name`, `path`) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 2);
            stmt.setString(2, "Video Name");
            stmt.setString(3, fileName);
            stmt.executeUpdate();

            // data inserted successfully
            System.out.println("Video Uploaded!!!");
            con.close();

            System.out.println("Receive Thread ended.");
            fout.close();
            dis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public ReceiveThread(Socket sock){
        this.socket = sock;
    }
}
