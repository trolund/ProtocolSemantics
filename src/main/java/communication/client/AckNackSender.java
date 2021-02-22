package communication.client;

import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AckNackSender extends BasicSender implements Runnable {

    public AckNackSender(int myPort, int serverPort, String data) {
        super(myPort, serverPort, data);
    }

    public AckNackSender(int serverPort, String data) {
        super(serverPort, data);

    }

    @Override
    public void run() {
        byte[][] allDataToSend = DataUtils.divideData(dataToSend.getBytes(StandardCharsets.UTF_8));

        // Send all data
        for (byte[] packageData: allDataToSend) {
            if(packageData != null){
                try {
                    AckNackSemantics(packageData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void AckNackSemantics(byte[] packageData) throws IOException {
        // send x to Receiver;
        Data d = new Data(MsgTypes.DATA, packageData);
        send(d.getBytes());
        // wait until receipt of msg ACK from Receiver;
        boolean isAck = waitForACK(0);
        if(!isAck){
            AckNackSemantics(packageData);
        }
    }

}
