package communication.server;

import communication.data.Data;

import java.io.IOException;

public class MaybeReceiver extends BasicReceiver implements Runnable  {

    public MaybeReceiver() {
        super();
    }

    public MaybeReceiver(int port) {
        super(port);
    }

    @Override
    public void run() {
        try {

        while(true){
            // wait until receipt of msg x from Sender;
            Data receivedData = WaitForPackageWithData();
            // send ACK to Sender;
            ACKMsg(receivedData);
            // output x;
            System.out.println("Receiver: " + receivedData);
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
