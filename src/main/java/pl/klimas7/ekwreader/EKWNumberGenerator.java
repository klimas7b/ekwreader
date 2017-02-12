package pl.klimas7.ekwreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class EKWNumberGenerator {
    private Court court;
    private Integer begin;
    private Integer end;

    public EKWNumberGenerator withCourt(Court court) {
        Validate.notNull(court);
        this.court = court;
        return this;
    }

    public EKWNumberGenerator withBegine(Integer begin) {
        Validate.notNull(begin);
        this.begin = begin;
        return this;
    }

    public EKWNumberGenerator withEnd(Integer end) {
        Validate.notNull(end);
        this.end = end;
        return this;
    }

    public List<EKWNumber> generate() {
        validate();

        List<EKWNumber> ekwNumbers = new ArrayList<>();
        for (int i = begin; i <= end; i++) {
            ekwNumbers.add(new EKWNumber(court, i));
        }

        return ekwNumbers;
    }

    private void validate() {
        Validate.notNull(court);
        Validate.notNull(begin);
        Validate.notNull(end);
    }

}
