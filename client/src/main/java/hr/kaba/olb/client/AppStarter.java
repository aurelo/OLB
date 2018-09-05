package hr.kaba.olb.client;

public class AppStarter {

    public static void main(String[] args) throws Exception {

        StandaloneApp.INSTANCE.start();

        StandaloneApp.INSTANCE.stop();
    }



}
