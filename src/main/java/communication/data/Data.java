package communication.data;

import java.nio.charset.StandardCharsets;

public class Data {

    public long secNr = 0;
    public MsgTypes type;
    public byte[] data;
    public long checksum;

    public Data(long secNr, MsgTypes type, byte[] data) {
        this.secNr = secNr;
        this.type = type;
        this.data = data;
        this.checksum = DataUtils.getCheckSum(this);
    }

    public Data(MsgTypes type, byte[] data) {
        this.type = type;
        this.data = data;
        this.checksum = DataUtils.getCheckSum(this);
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
