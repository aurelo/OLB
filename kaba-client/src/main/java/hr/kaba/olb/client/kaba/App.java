package hr.kaba.olb.client.kaba;

import hr.kaba.olb.client.OlbClient;
import hr.kaba.olb.responders.ora.DbSource;
import hr.kaba.olb.responders.ora.OraResponder;
import hr.kaba.olb.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Client that starts olb client and registers oracle responder as transaction responding service
 * Start and stop are expected to be called outside from windows service or as linux process
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public enum App {

    INSTANCE,
    ;

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final Marker ADMIN = MarkerFactory.getMarker("ADMIN");

    private DbSource dbSource;
    private AtomicBoolean shouldRun;

    private OlbClient olbClient;


    /**
     * wrapper for start method
     *
     * @param args array of service starting arguments
     */
    public static void start(String[] args) {
        try {
            INSTANCE.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error(ADMIN, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * wrapper for stop method
     *
     * @param args array of service stopping arguments
     */
    public static void stop(String[] args) {
        try {
            INSTANCE.stop();
        } catch (InterruptedException e) {
            logger.error(ADMIN, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * # Loads properties for Oracle database connection
     * # starts database
     * # registers oracle responder with olb client
     * # starts network client to listen for connection
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void start() throws IOException, InterruptedException {

        shouldRun = new AtomicBoolean(false);

        Properties properties = PropertiesLoader.load("db.properties");

        logger.debug(properties.toString());

        dbSource = new DbSource(properties);

        OraResponder trxResponder = new OraResponder(dbSource);

        dbSource.start();

        olbClient = new OlbClient(trxResponder, shouldRun);

        shouldRun.set(true);

        olbClient.start();

        logger.info(ADMIN, "STARTED OLB");

    }

    /**
     * # Marks flag signifying that service should not run anymore
     * # stops network client
     * # closes database connection
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {

        shouldRun.set(false);

        olbClient.stop();

        dbSource.stop();

        logger.info(ADMIN, "STOPPED OLB");
    }

}
