package communication.client;

import communication.data.Data;
import communication.data.DataUtils;
import communication.data.MsgTypes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AtMostOnceSender extends BasicSender implements Runnable {

    private int value = 0;

    public AtMostOnceSender(int myPort, int serverPort, String data) {
        super(myPort, serverPort, data);
    }

    public AtMostOnceSender(int serverPort, String data) {
        super(serverPort, data);
    }

    public synchronized int increment() {
        return value++;
    }

    @Override
    public void run() {
        byte[][] allDataToSend = DataUtils.divideData(dataToSend.getBytes(StandardCharsets.UTF_8));

        // Send all data
        for (int i = 0; i < allDataToSend.length; i++) {
                try {
                    AtMostOnceSemantics(allDataToSend[i], increment());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void AtMostOnceSemantics(byte[] packageData, long secNr) throws IOException {
        // send x to Receiver;
        Data d = new Data(secNr, MsgTypes.DATA, packageData);
        send(d.getBytes());

        // start timer(s);
        // wait until (receipt of msg y from Receiver OR timeout);
        try {
            boolean isAck = CompletableFuture.supplyAsync(() -> {
                try {
                    return waitForACK(secNr);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }).get(350, TimeUnit.MICROSECONDS);

            if(!isAck){
                System.out.println("NACK, i resend the package....");
                AtMostOnceSemantics(packageData, secNr); // resend
            }
        } catch (TimeoutException e) {
            System.out.println("Time out has occurred, i resend the package....");
            AtMostOnceSemantics(packageData, secNr); // resend

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error, i resend the package....");
            AtMostOnceSemantics(packageData, secNr); // resend
        }
    }

}
