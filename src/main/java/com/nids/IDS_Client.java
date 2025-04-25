package com.nids;

import io.github.pixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

public class IDS_Client {

    private static final String SERVER_IP = "127.0.0.1"; // you must update if server is remote
    private static final int SERVER_PORT = 15000;
    private static final int UDP_PORT = 15001;

    public static void main(String[] args) {
        // Start UDP listener in a separate thread
        Thread udpListener = new Thread(IDS_Client::listenForUDP);
        udpListener.start();

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to IDS Server. Type messages to simulate network packets:");
            String userInput;

            while ((userInput = BoundedLineReader.readLine(input, 5_000_000)) != null) {
                out.println(userInput);
            }

        } catch (Exception e) {
            System.err.println("TCP Client Error: " + e.getMessage());
        }
    }

    private static void listenForUDP() {
        try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String alert = new String(packet.getData(), 0, packet.getLength());
                System.out.println("ALERT: " + alert);
            }

        } catch (Exception e) {
            System.err.println("UDP Listener Error: " + e.getMessage());
        }
    }
}
