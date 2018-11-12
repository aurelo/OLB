package hr.kaba.olb.responders.ora.service.proxy;

import java.sql.Connection;

/**
 * Service that can respond to transaction id on given connection
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public abstract class ConnectionResponder implements ResponseCallback {
    protected Connection connection;

    ConnectionResponder onConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

}
