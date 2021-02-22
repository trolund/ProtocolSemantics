package communication.client;

import communication.Communicator;
import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class BasicSender extends Communicator {

    private int serverPort = 0000;
    protected String dataToSend = "";

    public BasicSender(int myPort, int serverPort, String dataToSend) {
        super(myPort);
        this.serverPort = serverPort;
        this.dataToSend = dataToSend;
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }

    public BasicSender(int serverPort, String dataToSend) {
        super();
        this.serverPort = serverPort;
        this.dataToSend = dataToSend;
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }
    public void send(byte[] data){
        try {
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

    public boolean waitForACK(long secNr) throws IOException {
        Data data;

        do{
            serverSocket.receive(inputPacket);
            data = DataUtils.parseData(new String(inputPacket.getData()));
        }while (!(data.type.equals(MsgTypes.ACK) || data.type.equals(MsgTypes.NACK)) && data.secNr == secNr);

        return MsgTypes.ACK == data.type;
    }

}
