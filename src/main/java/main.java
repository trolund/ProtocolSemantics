import communication.client.*;
import communication.server.*;

public class main {

    public static void main(String[] args) {

        int serverPort = 5002;
        int clientPort = 5006;

        // start the server
        Runnable r1 = new MaybeReceiver(serverPort);
        Thread receiverThread = new Thread(r1);
        receiverThread.start();

        // start the client
        Runnable r2 = new MaybeSender(clientPort, serverPort, "Hello world");
        Thread senderThread = new Thread(r2);

        senderThread.start();
    }
}
