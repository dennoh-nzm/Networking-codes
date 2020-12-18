import java.net.*;
import java.io.*;
public class PacketServer {
    public static int SERVER_PORT = 5000;
    private static int INPUT_BUFFER_LIMIT = 500;
    private int counter = 0;
    private DatagramSocket goOnline() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket (SERVER_PORT);
            System.out.println("SERVER online");
        } catch (SocketException e) {
            System.out.println("SERVER: no network connection");
            System.exit(-1);
        }
        return socket;
    }
    private void handleRequests(DatagramSocket socket) {
        while (true) {
            try {
                byte[] receiveBuffer = new byte[INPUT_BUFFER_LIMIT];
                DatagramPacket receivePacket;
                receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                InetAddress address = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("SERVER: Packet received: \"" + request + "\" from " + address + ":" + clientPort);
                byte[] sendBuffer;
                if (request.equals("What Time is it ?")) {
                    System.out.println("SERVER: sending packet with time info");
                    sendResponse(socket, address, clientPort,
                                 new java.util.Date().toString().getBytes());
                    counter++;
                }
                else if (request.equals("How many requests have you handled ?")) {
                    System.out.println("SERVER: sending packet with number requests");
                    sendResponse(socket, address, clientPort,
                            ("" + ++counter).getBytes());
                }
                else
                    System.out.println("SERVER: Unknown request: " +request);
            } catch(IOException e) {
                System.out.println("SERVER: Error receiving client requests");
            }
        }
    }

    private String sendResponse(DatagramSocket socket, InetAddress address, int clientPort, byte[] bytes) {

        return null;
    }

    private void sendRequest(DatagramSocket socket, InetAddress address,
                             int clientPort, byte[] response) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(response,
                                                 response.length, address, clientPort);
            socket.send(sendPacket);
        } catch (IOException e) {
            System.out.println("SERVER: Error sending response to client");
        }
    }
    public static void main (String args[]) {
        PacketServer s = new PacketServer();
        DatagramSocket ds = s.goOnline();
        if (ds != null)
            s.handleRequests(ds);
    }
}