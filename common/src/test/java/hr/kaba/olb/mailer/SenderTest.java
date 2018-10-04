package hr.kaba.olb.mailer;

import hr.kaba.olb.util.PropertiesLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class SenderTest {

    private static Properties mailProperties;
    private static Sender sender;

    @BeforeAll
    static void propertiesLoader() throws IOException {
        mailProperties = PropertiesLoader.load("mail.properties");
        sender = new Sender(mailProperties);
    }


    @Test
    void testWarningSend() {
        Sender.MessageBuilder messageBuilder = sender.builder();

        messageBuilder.to("zlatko.gudasic@kaba.hr")
                      .withSubject("testing mail send")
                      .body("Hello, world!\n");

//        sender.sendWarning(messageBuilder);
    }


    @Test
    void testErrorSend() {
        Sender.MessageBuilder messageBuilder = sender.builder();

        messageBuilder.withSubject("ERROR")
                      .body("Sending mail for errors!\n");

//        sender.sendError(messageBuilder);
    }


}