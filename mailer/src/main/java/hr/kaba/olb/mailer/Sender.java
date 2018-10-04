package hr.kaba.olb.mailer;

import javax.mail.Session;
import java.util.Properties;

public class Sender {

    private final String host;
    private final String from;

    private String warningTo;
    private String errorTo;

    private static final String DEFAULT_MAIL = "zlatko.gudasic@kaba.hr";


    public Sender(Properties properties) {

        host = properties.getProperty("mail.smtp.host");
        from = properties.getProperty("from");

        warningTo = (String) properties.getOrDefault("to.warning", DEFAULT_MAIL);
        errorTo = (String) properties.getOrDefault("to.error", DEFAULT_MAIL);

    }

    public void sendWarning() {

    }


    public void sendError() {

    }


    private void sendMail() {

    }


    public Session constructSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        Session session = Session.getInstance(props, null);

        return session;
    }

}
