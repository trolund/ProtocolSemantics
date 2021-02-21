package communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Communicator {

    public final static int SERVICE_PORT=50001;

    protected DatagramSocket serverSocket;

    // Buffersizes
    protected byte[] receivingDataBuffer = new byte[1024];
    protected byte[] sendingDataBuffer = new byte[1024];

    protected DatagramPacket sendingPacket;
    protected DatagramPacket inputPacket;

    public Communicator() {
        setup(SERVICE_PORT);
    }

    public Communicator(int port) {
        setup(port);
    }

    protected void setup(int port) {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.println("Failed to create socket on port: " + SERVICE_PORT);
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        serverSocket.close();
    }
}
