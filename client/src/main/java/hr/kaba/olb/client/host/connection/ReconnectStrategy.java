package hr.kaba.olb.client.host.connection;

import java.util.concurrent.TimeUnit;


/**
 * Represents interval after which reconnection should be attempted
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class ReconnectStrategy {
    private final long reconnectionInterval;
    private final TimeUnit reconnectionTimeUnit;

    public ReconnectStrategy(long reconnectionInterval, TimeUnit reconnectionTimeUnit) {
        this.reconnectionInterval = reconnectionInterval;
        this.reconnectionTimeUnit = reconnectionTimeUnit;
    }

    long getReconnectionInterval() {
        return reconnectionInterval;
    }

    TimeUnit getReconnectionTimeUnit() {
        return reconnectionTimeUnit;
    }
}
