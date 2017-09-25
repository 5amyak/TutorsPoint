package com.samyak;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class UploadThread implements Runnable {

    File file;

    public UploadThread(File file) {
        this.file = file;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
