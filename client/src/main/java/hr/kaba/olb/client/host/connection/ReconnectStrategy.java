package hr.kaba.olb.client.host.connection;

import java.util.concurrent.TimeUnit;

public class ReconnectStrategy {
    private final long reconnectionInverval;
    private final TimeUnit reconnectionTimeUnit;

    public ReconnectStrategy(long reconnectionInverval, TimeUnit reconnectionTimeUnit) {
        this.reconnectionInverval = reconnectionInverval;
        this.reconnectionTimeUnit = reconnectionTimeUnit;
    }

    public long getReconnectionInverval() {
        return reconnectionInverval;
    }

    public TimeUnit getReconnectionTimeUnit() {
        return reconnectionTimeUnit;
    }
}
