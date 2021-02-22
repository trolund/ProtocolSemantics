package communication.server;

import communication.data.Data;
import communication.data.DataUtils;

import java.io.IOException;

public class AckNackReceiver extends BasicReceiver implements Runnable  {

    public AckNackReceiver() {
        super();
    }

    public AckNackReceiver(int port) {
        super(port);
    }

    @Override
    public void run() {
        try {

        while(true){
            // wait until receipt of msg x from Sender;
            Data receivedData = WaitForPackageWithData();

            if(DataUtils.getCheckSum(receivedData) == receivedData.checksum){
                // send ACK to Sender;
                ACKMsg(receivedData);
                // output x;
                System.out.println("Receiver: " + receivedData);
            }else {
                // send NACK to Sender;
                NACKMsg(receivedData);
            }
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
