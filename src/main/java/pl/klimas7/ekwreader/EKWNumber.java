package pl.klimas7.ekwreader;


import org.apache.commons.lang3.StringUtils;

public class EKWNumber {
    private Court court;
    private String number;
    private int checksum;

    public EKWNumber(Court court, Integer number) {
        this.court = court;
        this.number = StringUtils.leftPad(number.toString(), 8, '0');
        this.checksum = Utils.calculateChecksum(court, this.number);
    }

    public Court getCourt() {
        return court;
    }

    public String getNumber() {
        return number;
    }

    public String getChecksum() {
        return Integer.toString(checksum);
    }

    @Override
    public String toString() {
        return court.name() + "/" + number + "/" + checksum;
    }
}
