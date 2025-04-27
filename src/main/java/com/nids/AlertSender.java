package com.nids;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * AlertSender is for sending UDP allerts to a already defined location this alert happens when a threat is detected
 */

public class AlertSender {
    // ip address is where alerts is sent
    private static final String ALERT_DEST_IP = "127.0.0.1"; // change if using another machine
    // this is just the port number where the client is listening
    private static final int ALERT_PORT = 15001;

    public static void sendAlert(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(ALERT_DEST_IP);
            byte[] buffer = message.getBytes();
            // udp packet creation with alert message
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, ALERT_PORT);
            // sends udp packet
            socket.send(packet);
            System.out.println("Alert sent via UDP.");
        } catch (Exception e) {
            System.err.println("Failed to send UDP alert: " + e.getMessage());
        }
    }
}
