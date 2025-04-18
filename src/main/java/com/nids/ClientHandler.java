package com.nids;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private static final String SIGNATURE_FILE = "ThreatSignatures.txt";
    private HashSet<String> threatSignatures;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.threatSignatures = loadThreatSignatures();
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received: " + line);
                if (isThreat(line)) {
                    System.out.println("THREAT DETECTED!");
                    AlertSender.sendAlert("THREAT DETECTED from " + clientSocket.getInetAddress());
                    FirebaseLogger.logThreat(clientSocket.getInetAddress().toString(), line);
                }
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    private boolean isThreat(String line) {
        return threatSignatures.contains(line.trim().toLowerCase());
    }

    private HashSet<String> loadThreatSignatures() {
        HashSet<String> set = new HashSet<>();
        try (Scanner scanner = new Scanner(new File(SIGNATURE_FILE))) {
            while (scanner.hasNextLine()) {
                set.add(scanner.nextLine().trim().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Threat signature file not found.");
        }
        return set;
    }
}
