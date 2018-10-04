package hr.kaba.olb.mailer;

import hr.kaba.olb.util.PropertiesLoader;

import java.io.IOException;

public enum AppMailer {
    INSTANCE;

    private Sender sender;

    AppMailer() {
        try {
            sender = new Sender(PropertiesLoader.load("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sender.MessageBuilder getBuilder() {
        return sender.builder();
    }

    public void sendWarning(String subject, String body) {
        sender.sendWarning(getBuilder().withSubject(subject).body(body));
    }

    public void sendError(String subject, String body) {
        sender.sendError(getBuilder().withSubject(subject).body(body));
    }


}
