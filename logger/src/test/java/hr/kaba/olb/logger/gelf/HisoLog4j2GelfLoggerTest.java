package hr.kaba.olb.logger.gelf;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HisoLog4j2GelfLoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(HisoLog4j2GelfLoggerTest.class);

    @Test
    void localhostIntegrationTest() {
        String atmDepositString = "ISO0160000130221B23880012EA0801800000040100000102100300000000100000911131707011396100624062806281119100055555375590722400899963=191220100000000000005964        75542200S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P02005964        0628100624250628000000000011240000000000014";
        HISOMessage atmDeposit = OLBCodec.decode(atmDepositString);


        HisoLog4j2GelfLogger gelfLogger = new HisoLog4j2GelfLogger(atmDeposit);

        gelfLogger.log(() -> logger.info("requesting ATM deposit"));


    }


}