package communication.client;

import communication.Communicator;
import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class BasicSender extends Communicator {

    private int serverPort = 0000;

    public BasicSender(int myPort, int serverPort) {
        super(myPort);
        this.serverPort = serverPort;
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }

    public BasicSender(int serverPort) {
        super();
        this.serverPort = serverPort;
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }
    public void send(byte[] data){
        try {
            // TODO split data in 1024 datagrams
            sendingDataBuffer = data;

            // Obtain communication.client's IP address and the port
            InetAddress senderAddress = InetAddress.getLocalHost();

            // Create new UDP packet with communication.data to send to the communication.client
            DatagramPacket outputPacket = new DatagramPacket(
                    sendingDataBuffer, sendingDataBuffer.length,
                    senderAddress,serverPort
            );

            // Send the created packet to communication.client
            serverSocket.send(outputPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForACK(int secNr) throws IOException {
        Data data;

        do{
            serverSocket.receive(inputPacket);
            data = DataUtils.parseData(new String(inputPacket.getData()));
        }while (data.type != MsgTypes.ACK && data.secNr == secNr);
    }

}
