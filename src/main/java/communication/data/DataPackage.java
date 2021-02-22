package communication.data;

public class DataPackage {

    private long secNr;
    private byte[] data;

    public DataPackage(long secNr, byte[] data) {
        this.secNr = secNr;
        this.data = data;
    }

    public long getSecNr() {
        return secNr;
    }

    public void setSecNr(long secNr) {
        this.secNr = secNr;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
