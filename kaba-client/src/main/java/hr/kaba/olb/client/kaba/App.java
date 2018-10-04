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

public enum App {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final Marker ADMIN = MarkerFactory.getMarker("ADMIN");

    private DbSource dbSource;
    private AtomicBoolean shouldRun;

    private OlbClient olbClient;


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

    public static void stop(String[] args) {
        try {
            INSTANCE.stop();
        } catch (InterruptedException e) {
            logger.error(ADMIN, e.getMessage());
            e.printStackTrace();
        }
    }


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

    public void stop() throws InterruptedException {

        shouldRun.set(false);

        olbClient.stop();

        dbSource.stop();

        logger.info(ADMIN, "STOPPED OLB");
    }

}
