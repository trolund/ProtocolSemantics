/*
package communication.client;

import communication.data.Data;
import communication.data.MsgTypes;

import java.io.IOException;

public class AckNackSender extends BasicSender implements Runnable {

    public AckNackSender(int myPort, int serverPort) {
        super(myPort, serverPort);
    }

    public AckNackSender(int serverPort) {
        super(serverPort);

    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                // send x to Receiver;
                Data d = new Data(MsgTypes.DATA, "Some data");
                send(d.getBytes());
                // wait until receipt of msg ACK from Receiver;
                boolean isAck = waitForACK(0);

                if(isAck){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
*/
