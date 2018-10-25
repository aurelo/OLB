package hr.kaba.olb.logger;

import hr.kaba.olb.codec.message.HISOMessage;
import org.slf4j.Logger;

import java.util.function.Consumer;

@FunctionalInterface
public interface HisoMessageLogger {
    void log(Runnable doer);
}
