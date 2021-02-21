package communication.data;

public class DataUtils {

    public static Data parseData(String dataString) {
        String[] dataParts = dataString.split(":");

        Data data = new Data();

        data.type = getMsgType(dataParts[0]);
        data.secNr = getSecNr(dataParts[1]);
        data.data = dataParts[2];

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

}
