package br.edu.unifor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class app {
    public static void main(String[] args) {

        int port = 99;
        String host = "224.224.224.224";
        InetAddress groupAddress = null;
        MulticastSocket socket = null;

        try {
            groupAddress = InetAddress.getByName(host);
            socket = new MulticastSocket(port);
            socket.joinGroup(groupAddress);
            byte[] buffer = new byte[1024];
            byte[] message = "teste".getBytes();
            socket.send(new DatagramPacket(message, message.length, groupAddress, port));
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println(new String(packet.getData()));
                socket.send(packet);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}