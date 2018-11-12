package hr.kaba.olb.responders.ora;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for data source that can be started and stopped
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public interface Source {
    void start();
    void stop();

    DataSource getDataSource();

    Connection getConnection() throws SQLException;

}