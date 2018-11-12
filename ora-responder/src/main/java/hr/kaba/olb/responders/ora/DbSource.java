package hr.kaba.olb.responders.ora;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Oracle database implementation of data source
 * Uses hikari cp database source pool implementation and loads config properties from a resource
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class DbSource implements Source {

    private final static Logger logger = LoggerFactory.getLogger(DbSource.class);

    private final Properties dbSourceProperties;
    private HikariDataSource dataSource;

    public DbSource(Properties dbSourceProperties) {
        this.dbSourceProperties = dbSourceProperties;
    }

    @Override
    public void start() {
        logger.info("Starting data source");
        if (dataSource == null || dataSource.isClosed()){
            dataSource = new HikariDataSource(new HikariConfig(dbSourceProperties));
        }
    }

    @Override
    public void stop() {
        if (!dataSource.isClosed()) {
            logger.debug("Closing datasource");
            dataSource.close();
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
