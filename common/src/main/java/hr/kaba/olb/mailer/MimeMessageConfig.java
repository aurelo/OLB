package hr.kaba.olb.mailer;

import javax.mail.MessagingException;

@FunctionalInterface
public interface MimeMessageConfig {
    void set() throws MessagingException;
}
