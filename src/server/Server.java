package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String args[]) {
        try {
            ServerSocket listenSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Server Started and listening for client");
                Socket socket = listenSocket.accept();
                Thread newClientThread = new Thread(new NewClientThread(socket));
                newClientThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
