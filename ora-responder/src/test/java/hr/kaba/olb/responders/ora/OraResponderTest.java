package hr.kaba.olb.responders.ora;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.util.PropertiesLoader;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


@Ignore
class OraResponderTest {

    private final static Logger logger = LoggerFactory.getLogger(OraResponderTest.class);

    private static DbSource source;
    private static Properties properties;

    @BeforeAll
    static void startUp() throws IOException {

        properties = PropertiesLoader.load("db.properties");

        logger.debug(properties.toString());

        source = new DbSource(properties);

//        source.start();
    }

    @AfterAll
    static void shutDown() {
//        source.stop();
    }

    @Test
    void respondsShouldInsertAppropriateDbRows() throws SQLException {

        OraResponder responder = new OraResponder(source);

        String atmDepositString = "ISO0160000100200B238800128A0801800000000100000002100300000000100000628080624000002100624062806281119100055555375590722400899963=191220100000010960005964        S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P11240000000000";
        HISOMessage atmDeposit = OLBCodec.decode(atmDepositString);

//        responder.logRequest(atmDeposit, ResponseCode.ADVICE_APPROVED, Formatters.formatTransmissionDate(LocalDateTime.now()));
//        responder.logRequest(atmDeposit);




    }

}