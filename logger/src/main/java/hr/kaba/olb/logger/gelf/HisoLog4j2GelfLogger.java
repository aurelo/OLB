package hr.kaba.olb.logger.gelf;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.logger.HisoMessageLogger;
import org.apache.logging.log4j.CloseableThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of HISO to GELF logger, taking a HISO message and Logger doer
 * and logging a message to GELF
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class HisoLog4j2GelfLogger implements HisoMessageLogger {
    private static final Logger logger = LoggerFactory.getLogger(HisoLog4j2GelfLogger.class);

    private final HISOMessage message;

    public HisoLog4j2GelfLogger(HISOMessage message) {
        this.message = message;
    }

    @Override
    public void log(Runnable doer) {
        try (CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(HisoToGelf.adapt(message))) {
            doer.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
