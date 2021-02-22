package communication.server;

import communication.data.Data;
import communication.data.DataUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AtMostOnceReceiver extends BasicReceiver implements Runnable  {

    private final List<Long> ReceviedPackages = new ArrayList<>();

    public AtMostOnceReceiver() {
        super();
    }

    public AtMostOnceReceiver(int port) {
        super(port);
    }

    @Override
    public void run() {
        try {

        while(true){
            // wait until receipt of msg x from Sender;
            Data receivedData = WaitForPackageWithData();

            // if x ∈ M then
            if(DataUtils.getCheckSum(receivedData) == receivedData.checksum){
                // send ACK to Sender;
                ACKMsg(receivedData);

                // have i already received this package?
                if (!ReceviedPackages.contains(receivedData.secNr)){
                    ReceviedPackages.add(receivedData.secNr);
                    // output x;
                    System.out.println("Receiver: " + receivedData);
                    System.out.println(ReceviedPackages);
                }
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
