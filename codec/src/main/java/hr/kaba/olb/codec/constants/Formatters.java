package hr.kaba.olb.codec.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatters {
    public final static DateTimeFormatter TRANSMISSION_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMddHHmmss");

    public final static String formatTransmissionDate(LocalDateTime dateTime) {
        return dateTime.format(TRANSMISSION_DATE_TIME_FORMATTER);
    }
}