package hr.kaba.olb.codec.constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date/number formatting
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class Formatters {
    public final static DateTimeFormatter TRANSMISSION_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMddHHmmss");

    public final static String formatTransmissionDate(LocalDateTime dateTime) {
        return dateTime.format(TRANSMISSION_DATE_TIME_FORMATTER);
    }
}