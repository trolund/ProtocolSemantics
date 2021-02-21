package communication.data;

import java.nio.charset.StandardCharsets;

public class DataUtils {

    public static Data parseData(String dataString) {
        String[] dataParts = dataString.split(":");

        Data data = new Data();

        data.type = getMsgType(dataParts[0]);
        data.secNr = getSecNr(dataParts[1]);
        data.data = dataParts[2].getBytes(StandardCharsets.UTF_8);

        return data;
    }

    public static MsgTypes getMsgType(String t){
        if (t.equalsIgnoreCase(MsgTypes.DATA.name())){
            return MsgTypes.DATA;
        }
        else if (t.equalsIgnoreCase(MsgTypes.ACK.name())){
            return MsgTypes.ACK;
        }
        else if (t.equalsIgnoreCase(MsgTypes.DATA_ID.name())){
            return MsgTypes.DATA_ID;
        }
        else if (t.equalsIgnoreCase(MsgTypes.NACK.name())){
            return MsgTypes.NACK;
        }
        else {
            return MsgTypes.UNO;
        }
    }

    private static int getSecNr(String secNrS){
        return Integer.parseInt(secNrS);
    }

    public static byte[][] divideData(byte[] source, int chunksize) {
        return splitData(source, chunksize);
    }

    public static byte[][] divideData(byte[] source) {
        return splitData(source, 1);
    }

    private static byte[][] splitData(byte[] source, int chunksize){

        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) chunksize)][chunksize];

        int start = 0;

        int parts = 0;


        for (int i = 0; i < ret.length; i++) {
            if (start + chunksize > source.length) {
                System.arraycopy(source, start, ret[i], 0, source.length - start);
            } else {
                System.arraycopy(source, start, ret[i], 0, chunksize);
            }
            start += chunksize;
            parts++;
        }

        return ret;
    }

}
