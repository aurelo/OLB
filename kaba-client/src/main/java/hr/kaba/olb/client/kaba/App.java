package hr.kaba.olb.client.kaba;

import hr.kaba.olb.client.OlbClient;
import hr.kaba.olb.responders.ora.DbSource;
import hr.kaba.olb.responders.ora.OraResponder;
import hr.kaba.olb.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public enum App {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private DbSource dbSource;
    private AtomicBoolean shouldRun;

    private OlbClient olbClient;


    public static void start(String [] args)  {
        try {
            INSTANCE.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stop(String[] args) {
        INSTANCE.stop();
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

    }

    public void stop() {

        shouldRun.set(false);

        olbClient.stop();

        dbSource.stop();
    }

}
