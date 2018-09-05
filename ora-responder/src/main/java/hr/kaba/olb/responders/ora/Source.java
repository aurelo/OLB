package hr.kaba.olb.responders.ora;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface Source {
    public void start();
    public void stop();

    DataSource getDataSource();

    Connection getConnection() throws SQLException;

}