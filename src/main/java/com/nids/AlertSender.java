package com.nids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AlertSender {

    private static final String ALERT_DEST_IP = "127.0.0.1"; // change if using another machine
    private static final int ALERT_PORT = 15001;

    public static void sendAlert(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(ALERT_DEST_IP);
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, ALERT_PORT);
            socket.send(packet);
            System.out.println("Alert sent via UDP.");
        } catch (Exception e) {
            System.err.println("Failed to send UDP alert: " + e.getMessage());
        }
    }
}
