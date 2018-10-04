package hr.kaba.olb.protocol;

import com.sun.javafx.binding.StringFormatter;
import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.InitiatorType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public final class Sys {

    private final static AtomicInteger traceNumber = new AtomicInteger(0);

    public static String auditTraceNumber() {
        return String.format("%06d", traceNumber.incrementAndGet());
    }

    public final static Supplier<String> transmissionDate = () -> Formatters.formatTransmissionDate(LocalDateTime.now());


}
