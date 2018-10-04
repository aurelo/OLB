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

class RequestDetectorPosAdviceTest {

    private final static Logger logger = LoggerFactory.getLogger(RequestDetectorPosAdviceTest.class);

    @BeforeAll
    static void printTestName() {
        logger.debug(logger.getName().toUpperCase());
    }


    @Test
    void shouldRecognizePosPurchaseAdvice() {
        String posPurchaseAdviceString = "eISO0260000730220B23882812AA08018000000401000000400200000000001438907251349560224501549360725072500300043778376020202000739131=18082210000000000000228806      00KZ323101        KONZUM P-3231         KARLOVAC     HRVHR191016EUROEURO-12000000192400PRO1001000000000200228806      072515493600072500000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posPurchaseAdvice = OLBCodec.decode(posPurchaseAdviceString);

        assertThat(RequestDetector.determineType(posPurchaseAdvice), is(RequestType.POS_PURCHASE_ADVICE));
    }

    @Test
    void shouldRecognizePosWithdravalAdvice() {
        String posWithdrawalAdviceString = "eISO0260000730220B23882812AA08018000000401000000401200000000001000001051316040302101416010105010500200047936376020202002837156=20052210000000000000456932      0047200221        PU 47252 BARILOVIC    BARILOVIC    HRVHR191016EUROEURO-06000000192400PRO1001000000000200456932      010514160100010500000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posWithdrawalAdvice = OLBCodec.decode(posWithdrawalAdviceString);

        assertThat(RequestDetector.determineType(posWithdrawalAdvice), is(RequestType.POS_WITHDRAWAL_ADVICE));
    }

    @Test
    void shouldRecognizePosInstallmentsAdvice() {
        String posInstallmentsAdviceString = "ISO0260000130220B23882812AA0801800000040100000061U3200000000169962070112345800915314343607010701001001119100820087376020202001809669=1502221000000000000000000100100100E0820087        T.O. SATEL 1          KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        0200000001001001070114343600070100000000001124000080000038210000000000000                    000134000000                212000     000                                                                              0000000000010010010  ";
        HISOMessage posInstallmentsAdvice = OLBCodec.decode(posInstallmentsAdviceString);

        assertThat(RequestDetector.determineType(posInstallmentsAdvice), is(RequestType.POS_INSTALLMENT_ADVICE));
    }

    @Test
    void shouldRecognizePosPovratAdvice() {
        String posPovratAdviceString = "ISO0260000100220B23882812AA080180000004010000006202000000000015995070614504600579316504307060706001001119100822642376020202002834567=1910221000000000000000000100100400E0822642        STUDIO MODERNA TV PRODKARLOVAC        HR1910162400PRO1+00000000192400PRO1001        0200000001001004070616504300070600000000001124000080000038210000000000000                    000134000000                                                                                                            0000000000010010041  ";
        HISOMessage posPovratAdvice = OLBCodec.decode(posPovratAdviceString);

        assertThat(RequestDetector.determineType(posPovratAdvice), is(RequestType.POS_RETURN_ADVICE));
    }

    @Test
    void shouldRecognizePosPurchaseAdjustmentAdvice() {
        String posPurchaseAdjustmentAdviceString = "|ISO0260000700220B23880812AA08018000000421000000422003000000009353808140805500044522325390811081415044441215189222400867422=1902821938      0000000001        PAYPAL *SPORTSDIREC   35314369001  GBRGB191016EUROEURO-12000000192400PRO103100000000020082193800000008112125390008140000000000000000084399                              1124000080000038000000000000000000000000000000000000000";
        HISOMessage posPurchaseAdjustmentAdvice = OLBCodec.decode(posPurchaseAdjustmentAdviceString);

        assertThat(RequestDetector.determineType(posPurchaseAdjustmentAdvice), is(RequestType.POS_PURCHASE_ADJUSTMENT_ADVICE));
    }


    @Test
    void shouldRecognizePosInstallmentCancellationAdvice() {
        String posInstallmentCancellationAdviceString = "ISO0260000100220B23882812AA0801800000040100000062U3200000000050000062612435902520314414106260626005001119100822610376020202000359625=2003221000000000000000000100100700E0822610        GRAWE HRVATSKA DD     KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        0200000001001006062614414100062600000000001124000080000038210000000000000                    000134000000                111000                                                                                      0010010060010010071  ";
        HISOMessage posInstallmentCancellationAdvice = OLBCodec.decode(posInstallmentCancellationAdviceString);

        assertThat(RequestDetector.determineType(posInstallmentCancellationAdvice), is(RequestType.POS_INSTALLMENT_CANCELLATION_ADVICE));
    }

    @Test
    void shouldRecognizePosOnlineAdvice() {
        String posOnlineAdviceString = "RISO0260000700220B23880812AA08018000000401000000480003000000000192207122356270229401914350712071315044441215189222400882439=2003445011      0000000001        PAYPAL *TANSANXING    Luxembourg   LUXLU191016EUROEURO-12000000192400PRO1031000000000200445011      071219143500071300000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posOnlineAdvice = OLBCodec.decode(posOnlineAdviceString);

        assertThat(RequestDetector.determineType(posOnlineAdvice), is(RequestType.POS_ONLINE_ADVICE));
    }


}