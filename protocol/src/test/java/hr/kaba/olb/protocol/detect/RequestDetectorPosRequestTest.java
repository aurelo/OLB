package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class RequestDetectorPosRequestTest {
    private final static Logger logger = LoggerFactory.getLogger(RequestDetectorPosRequestTest.class);

    @BeforeAll
    static void printTestName() {
        logger.debug(logger.getName().toUpperCase());
    }

    @Test
    void shouldRecognizePosPurchaseRequest() {
        String posPurchaseRequestString = "9ISO0260000700200B238828128A08018000000001000000400200000000015658206181205340320961405330618061800200043778376020205002230561=20102210000021489000292185      50039021        VRUTAK DOO            ZAGREB       HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posPurchaseRequest = OLBCodec.decode(posPurchaseRequestString);


        assertThat(RequestDetector.determineType(posPurchaseRequest), is(RequestType.POS_PURCHASE_REQUEST));
    }

    @Test
    void shouldRecognizePosWithdrawalRequest() {
        String posWithdrawalReqeustString = "9ISO0260000700200B238828128A08018000000001000000401200000000002211509191805520279662005520919091900300047936376020202001571475=19112210000031828000252324      47200203        PU 47104 KARLOVAC 3   KARLOVAC     HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posWithdrawalReqeust = OLBCodec.decode(posWithdrawalReqeustString);

        assertThat(RequestDetector.determineType(posWithdrawalReqeust), is(RequestType.POS_WITHDRAWAL_REQUEST));
    }

    @Test
    void shouldRecognizePosReturnRequest() {
        String posPovratRequestString = ";ISO0260000100200B238828128A0801800000000100000042020000000000194990906141824018029161806090609060040006990098376020202000512835=2010221000004177900081670110800622816701        ZOO CITY              Karlovac        HR1910162007PRO1+00000000192400PRO1001        1124000080000038000                                    ";
        HISOMessage posPovratRequest = OLBCodec.decode(posPovratRequestString);

        assertThat(RequestDetector.determineType(posPovratRequest), is(RequestType.POS_RETURN_REQUEST));
    }


    @Test
    void shouldRecognizePosInstallmentRequest() {

        String posInstallmentRequestString = "ISO0260000100200B238828128A0801800000000100000061U3200000000099999062612471802523014471706260626004001119100822478376020202000604541=21042210000041230000000001001004E0822478        INTERSPAR KARLOVAC    KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        1124000080000038210000000000000                    000134000000                104000                                                                                      0000000000010010041  ";
        HISOMessage posInstallmentRequest = OLBCodec.decode(posInstallmentRequestString);

        assertThat(RequestDetector.determineType(posInstallmentRequest), is(RequestType.POS_INSTALLMENT_REQUEST));
    }

    @Test
    void shouldRecognizePosOnlineRequest() {
        String posOnlineRequestString = "'ISO0260000700200B238808128A080180000000010000004802000000000010000091915050502632917050409190919150515554216020202002241136=1908179331      00000001        Hattrick - PSK Croatia012399200    HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posOnlineRequest = OLBCodec.decode(posOnlineRequestString);

        assertThat(RequestDetector.determineType(posOnlineRequest), is(RequestType.POS_ONLINE_REQUEST));

    }

}