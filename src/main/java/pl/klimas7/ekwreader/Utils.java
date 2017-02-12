package pl.klimas7.ekwreader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;
import org.apache.commons.lang3.StringUtils;

public class Utils {

    private static final String EKW_HEADER_PATTERN = "Numer księgi wieczystej\n" +
            "(.*)\n" +
            "Typ księgi wieczystej\n" +
            "(.*)\n" +
            "Oznaczenie wydziału prowadzącego księgę wieczystą\n" +
            "(.*)\n" +
            "Data zapisania księgi wieczystej\n" +
            "(.*)\n" +
            "Data zamknięcia księgi wieczystej\n" +
            "(.*)\n" +
            "Położenie\n" +
            "(.*)\n" +
            "Właściciel / użytkownik wieczysty / uprawniony\n((.*\\n)*)";

    private static final Pattern ekwHeaderPattern = Pattern.compile(EKW_HEADER_PATTERN);

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static char letterValues[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                    'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U',
                    'W', 'Y', 'Z',
            };
    private static int[] weight = new int[]{1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7};

    private static int getLetterValue(char letter) {
        int i;
        for (i = 0; i < letterValues.length; i++) {
            if (letter == letterValues[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int calculateChecksum(Court court, String number) {
        String id = court.name() + number;
        int checkSum = 0;
        for (int i = 0; i < id.length(); i++) {
            checkSum += weight[i] * getLetterValue(id.charAt(i));
        }

        checkSum %= 10;

        return checkSum;
    }

    public static EKWHeader parseEKWHeader(String wynikWyszukiwania) {
        EKWHeader ekwHeader = new EKWHeader();
        if (StringUtils.isNotBlank(wynikWyszukiwania)) {
            Matcher matcher = ekwHeaderPattern.matcher(wynikWyszukiwania);
            if (matcher.find()){
                ekwHeader.type(matcher.group(2))
                         .department(matcher.group(3))
                         .writingDate(LocalDate.parse(matcher.group(4), dtf))
                         .position(matcher.group(6))
                         .owners(matcher.group(7).split("\n"));

                String closingDate = matcher.group(5);
                if (!"---".equals(closingDate)) {
                    ekwHeader.closingDate(LocalDate.parse(closingDate, dtf));
                }
                ekwHeader.setExists();
            }
        }
        //wynikWyszukiwania
        return ekwHeader;
    }
}
