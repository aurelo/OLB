package hr.kaba.olb.responders.ora.service.proxy;

import java.sql.Connection;

public abstract class ConnectionResponder implements ResponseCallback {
    protected Connection connection;

    ConnectionResponder onConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

}
