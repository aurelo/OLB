package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestDetectorAtmTest {

    private final static Logger logger = LoggerFactory.getLogger(RequestDetectorAtmTest.class);

    @BeforeAll
    static void printTestName() {
        logger.debug(logger.getName().toUpperCase());
    }

    @Test
    void shouldRecognizeAtmBalanceInquiry() {
        String atmBalanceInquiryString = "ISO0160000100200B238820128A0801800000000100000003120000000000000000626110446024219130446062606260021119101147023376020202001869077=18092210000021391000936         A1147023        KABA DUBOVAC          KARLOVAC     HR HR1910122400PRO1+0000132400PRO10100P11240000800000";
        HISOMessage atmBalanceInquiry = OLBCodec.decode(atmBalanceInquiryString);

        assertThat(RequestDetector.determineType(atmBalanceInquiry), is(RequestType.ATM_BALANCE_INQUIRY));

    }

    @Test
    void shouldRecognizeAtmWithdrawalRequest() {
        String atmWithdrawalRequestString = "ISO0160000100200B238820128A0801800000000100000000120000000000100000626110405024212130405062606260011119101247001376020202002698137=190622100000112380005865        A1247001        KABA V. MACEKA        KARLOVAC     HR HR1910122400PRO1+0000132400PRO10100P11240000800000";
        HISOMessage atmWithdrawalRequest = OLBCodec.decode(atmWithdrawalRequestString);

        assertThat(RequestDetector.determineType(atmWithdrawalRequest), is(RequestType.ATM_WITHDRAWAL_REQUEST));
    }

    @Test
    void shouldRecognizeAtmReversal() {
        String atmReversalString = "4ISO0160000150420B23880012AA0801800000040100000000130000000000500000626132559025624152536062606261119101247032375189222400866135=190120100000000000002310        21A1247032        KABA VOJNIC           VOJNIC       HR HR1910122400PRO1+0000132400PRO13100P02002310        0626152536670626000000000011240000800000";
        HISOMessage atmReversal = OLBCodec.decode(atmReversalString);

        assertThat(RequestDetector.determineType(atmReversal), is(RequestType.ATM_REVERSAL));
    }

}