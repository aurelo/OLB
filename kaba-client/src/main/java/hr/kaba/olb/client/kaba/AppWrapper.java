package hr.kaba.olb.client.kaba;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppWrapper {
    public static void main(String[] args) throws IOException, InterruptedException {
        App.INSTANCE.start();

        TimeUnit.SECONDS.sleep(35);

        App.INSTANCE.stop();
    }
}
