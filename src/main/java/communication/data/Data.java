package communication.data;

import java.nio.charset.StandardCharsets;

public class Data {

    public int secNr = 0;
    public MsgTypes type;
    public String data;

    public Data(int secNr, MsgTypes type, String data) {
        this.secNr = secNr;
        this.type = type;
        this.data = data;
    }

    public Data() {
    }

    public Data(MsgTypes type, String data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return  type + ":" + secNr + ":" + data;
    }

    public byte[] getBytes() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }
}
