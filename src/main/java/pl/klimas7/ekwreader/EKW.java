package pl.klimas7.ekwreader;

/**
 * Created by bklimas on 05.02.17.
 */
public class EKW {
    private EKWNumber ekwNumber;
    private EKWHeader ekwHeader;

    public void setEkwNumber(EKWNumber ekwNumber) {
        this.ekwNumber = ekwNumber;
    }

    public void setEkwHeader(EKWHeader ekwHeader) {
        this.ekwHeader = ekwHeader;
    }

    public EKWNumber getEkwNumber() {
        return ekwNumber;
    }

    public EKWHeader getEkwHeader() {
        return ekwHeader;
    }
}
