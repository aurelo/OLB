package hr.kaba.olb.mailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Sender {

    private final static Logger logger = LoggerFactory.getLogger(Sender.class);

    private final String host;
    private final String defaultFrom;
    private final String defaultSubject;

    private String warningTo;
    private String errorTo;

    private static final String DEFAULT_MAIL = "zlatko.gudasic@kaba.hr";


    public Sender(Properties properties) {

        host = properties.getProperty("mail.smtp.host");
        defaultFrom = properties.getProperty("from");

        defaultSubject = (String) properties.getOrDefault("subject", "OLB");

        warningTo = (String) properties.getOrDefault("to.warning", DEFAULT_MAIL);
        errorTo = (String) properties.getOrDefault("to.error", DEFAULT_MAIL);

        logger.info("Mail sender properties: host: {} from: {} subject: {} warning to: {} error to: {}", host, defaultFrom, defaultSubject, warningTo, errorTo);

    }

    public MessageBuilder builder() {
        return new MessageBuilder();
    }

    public void sendWarning(MessageBuilder messageBuilder) {

        Session session = constructSession();

        try {
            MimeMessage message = new MimeMessage(session);
            messageBuilder.to = nvl(messageBuilder.to, warningTo);
            messageBuilder.build(message);

            Transport.send(message);

        } catch (MessagingException e) {
            logger.warn(e.getMessage());
        }


    }


    public void sendError(MessageBuilder messageBuilder) {
        Session session = constructSession();

        try {
            MimeMessage message = new MimeMessage(session);
            messageBuilder.to = nvl(messageBuilder.to, errorTo);
            messageBuilder.build(message);

            Transport.send(message);

        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }


    private Session constructSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        Session session = Session.getInstance(props, null);

        return session;
    }


    public class MessageBuilder {

        private String subject;
        private String body;
        private String from;
        private String to;
        private String cc;
        private String bcc;
        private Date sentOn;


        public MessageBuilder() {
        }

        public MessageBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public MessageBuilder body(String text) {
            this.body = text;
            return this;
        }

        public MessageBuilder from(String from) {
            this.from = from;
            return this;
        }

        public MessageBuilder to(String to) {
            this.to = to;
            return this;
        }

        public MessageBuilder cc(String cc) {
            this.cc = cc;
            return this;
        }

        public MessageBuilder bcc(String bcc) {
            this.bcc = bcc;
            return this;
        }

        public MessageBuilder sentOn(Date date) {
            sentOn = date;
            return this;
        }

        private void build(MimeMessage message) throws MessagingException {
            message.setFrom(nvl(defaultFrom, from));
            message.setSentDate(nvl(sentOn, new Date()));

            ifNotNull(subject, () -> message.setSubject(nvl(subject, defaultSubject)));
            ifNotNull(body, () -> message.setText(body));
            ifNotNull(to, () -> message.setRecipients(Message.RecipientType.TO, to));
            ifNotNull(cc, () -> message.setRecipients(Message.RecipientType.CC, cc));
            ifNotNull(bcc, () -> message.setRecipients(Message.RecipientType.BCC, bcc));
        }
    }

    private void ifNotNull(String value, MimeMessageConfig setter) throws MessagingException{
        if(value != null) {
            setter.set();
        }
    }


    private <T> T nvl(T primary, T alternate) {
        return primary != null ? primary : alternate;
    }


}

