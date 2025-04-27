package com.nids;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * IDS_Server is the main server part of the NIDS.
 * It listens for incoming client connections over TCP and assigns each connection
 * to a separate ClientHandler thread for processing.
 */

public class IDS_Server {

    private static final int SERVER_PORT = 15000; //port number server listens on

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("IDS Server started on port " + SERVER_PORT);
            // keeps trying to accept new client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());
                // this creates a new ClientHandler to process incoming data from this client
                ClientHandler handler = new ClientHandler(clientSocket);
                // starts ClientHandler in new thread
                new Thread(handler).start();
            }

        } catch (IOException e) {
            System.err.println("Error starting IDS Server: " + e.getMessage());
        }
    }
}
