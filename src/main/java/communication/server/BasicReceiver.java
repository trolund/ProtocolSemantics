package communication.server;

import communication.Communicator;
import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class BasicReceiver extends Communicator {

    DatagramPacket inputPacket;

    public BasicReceiver() {
        setup(SERVICE_PORT);
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }

    public BasicReceiver(int port) {
        setup(port);
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
    }

    public void sendResponse(byte[] data){
        try {
            // TODO split data in 1024 datagrams
            sendingDataBuffer = data;

            // Obtain client's IP address and the port
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();

            // Create new UDP packet with the data to send
            DatagramPacket outputPacket = new DatagramPacket(
                    sendingDataBuffer, sendingDataBuffer.length,
                    senderAddress,senderPort
            );

            // Send the created packet
            serverSocket.send(outputPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getDataAsString(){
        return new String(inputPacket.getData());
    }

    protected void WaitForPackage() throws IOException {
        serverSocket.receive(inputPacket);
    }

    protected Data WaitForPackageWithData() throws IOException {
        serverSocket.receive(inputPacket);
        return DataUtils.parseData(new String(inputPacket.getData()));
    }

    protected void ACKMsg(Data data){
        Data ack = new Data();
        // Set type
        ack.type = MsgTypes.ACK;
        // Set sec nr
        ack.secNr = data.secNr;
        sendResponse(ack.getBytes());
    }

}
