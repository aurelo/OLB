package hr.kaba.olb.client.kaba;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Wrapper for starting local client for testing purposes
 * Idea is to leave connection open for half a minute to test one or two transactions
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class AppWrapper {
    public static void main(String[] args) throws IOException, InterruptedException {
        App.INSTANCE.start();

        TimeUnit.SECONDS.sleep(35);

        App.INSTANCE.stop();
    }
}
