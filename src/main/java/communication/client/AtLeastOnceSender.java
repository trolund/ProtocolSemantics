package communication.client;

import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class AtLeastOnceSender extends BasicSender implements Runnable {

    public AtLeastOnceSender(int myPort, int serverPort, String data) {
        super(myPort, serverPort, data);
    }

    public AtLeastOnceSender(int serverPort, String data) {
        super(serverPort, data);

    }

    @Override
    public void run() {
        byte[][] allDataToSend = DataUtils.divideData(dataToSend.getBytes(StandardCharsets.UTF_8));

        // Send all data
        for (byte[] packageData: allDataToSend) {
            if(packageData != null){
                try {
                    AtLeastOnceSemantics(packageData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void AtLeastOnceSemantics(byte[] packageData) throws IOException {
        // send x to Receiver;
        Data d = new Data(MsgTypes.DATA, packageData);
        send(d.getBytes());

        // start timer(s);
        // wait until (receipt of msg y from Receiver OR timeout);
        try {
            boolean isAck = CompletableFuture.supplyAsync(() -> {
                try {
                    return waitForACK(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }).get(350, TimeUnit.MICROSECONDS);

            if(!isAck){
                AtLeastOnceSemantics(packageData); // resend
            }
        } catch (TimeoutException e) {
            System.out.println("Time out has occurred, i resend the package....");
            AtLeastOnceSemantics(packageData); // resend

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error, i resend the package....");
            AtLeastOnceSemantics(packageData); // resend
        }
    }

}
