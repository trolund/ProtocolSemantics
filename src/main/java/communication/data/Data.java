package communication.data;

import java.nio.charset.StandardCharsets;

public class Data {

    public int secNr = 0;
    public MsgTypes type;
    public byte[] data;

    public Data(int secNr, MsgTypes type, byte[] data) {
        this.secNr = secNr;
        this.type = type;
        this.data = data;
    }

    public Data() {
    }

    public Data(MsgTypes type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        if(data != null){
            return  type + ":" + secNr + ":" + new String(data);
        }
        return  type + ":" + secNr + ":NULL" ;
    }

    public byte[] getBytes() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }
}
