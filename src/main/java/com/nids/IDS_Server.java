package com.nids;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IDS_Server {

    private static final int SERVER_PORT = 15000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("IDS Server started on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.err.println("Error starting IDS Server: " + e.getMessage());
        }
    }
}
