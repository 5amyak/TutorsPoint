package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

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
            System.out.println(nextVideoId);

            if (nextVideoId == -1) {
                // repeatedly taking input and sending to server
                System.out.println("Sending File from Server to Client:");

                String fileName = dis.readUTF();
                FileInputStream fin = new FileInputStream(new File(fileName));
                int size;
                do {
                    byte b[] = new byte[1024];
                    size  = fin.read(b);
                    System.out.println("Read: " + size);
                    dos.write(b);
                }while (size > 0);

                fin.close();
            }

            else {
                //Starting thread that will continuosly listen for any input from server
                Thread receivingThread = new Thread(new ReceiveThread(socket, nextVideoId));
                receivingThread.start();
                receivingThread.join();
            }

            dos.close();
            dis.close();
            socket.close();
            System.out.println("New Client Thread on Server ended");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
