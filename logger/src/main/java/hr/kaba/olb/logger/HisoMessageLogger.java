package hr.kaba.olb.logger;

import hr.kaba.olb.codec.message.HISOMessage;
import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * Interface for logging in a context of closable thread context needed for passing name value map to gelf messenger
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface HisoMessageLogger {
    void log(Runnable doer);
}
