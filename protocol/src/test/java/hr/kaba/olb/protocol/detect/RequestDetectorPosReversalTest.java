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

class RequestDetectorPosReversalTest {

    private static final Logger logger = LoggerFactory.getLogger(RequestDetectorPosReversalTest.class);

    @BeforeAll
    static void printTestName() {
        logger.debug(logger.getName().toUpperCase());
    }

    @Test
    void shouldRecognizePosPurchaseReversal() {
        String posPurchaseReversalString = "eISO0260000750420B23882812AA08018000000401000000400200000000000100009191720520275731858280919091900100042760376020206000478855=19122210000000000000384898      6812953256        MCDONALD'S KIOSK, 2046KARLOVAC     HRVHR191016EUROEURO-12000000192400PRO1001000000000200384898      091918582800091900000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posPurchaseReversal = OLBCodec.decode(posPurchaseReversalString);

        assertThat(RequestDetector.determineType(posPurchaseReversal), is(RequestType.POS_PURCHASE_REVERSAL));
    }

    @Test
    void shouldRecognizePosKupovinaReversal() {
        String posTrxReversalString = "bISO0260000750420B23880812AA08018000000401000000400003000000000977006261148380245921347310626062600042760375189222400876753=19102010000000000000220665      6812941213        DM - DROGERIE MARKT, PKARLOVAC     HRVHR191016EUROEURO-12000000192400PRO1031000000000200220665      062613473100062600000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posTrxReversal = OLBCodec.decode(posTrxReversalString);

        assertThat(RequestDetector.determineType(posTrxReversal), is(RequestType.POS_PURCHASE_REVERSAL));
    }

    @Test
    void shouldRecognizePosWithdrawalReversal() {
        String posWithdrawalReversalString = "eISO0260000750420B23882812AA08018000000401000000401200000000051510407260531370252930721240726072600100047936376020202003331704=21062210000000000000313419      9647200201        PU 47000 KARLOVAC 1   KARLOVAC     HRVHR191016EUROEURO-12000000192400PRO1001000000000200313419      072607212400072600000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posWithdrawalReversal = OLBCodec.decode(posWithdrawalReversalString);

        assertThat(RequestDetector.determineType(posWithdrawalReversal), is(RequestType.POS_WITHDRAWAL_REVERSAL));
    }

    @Test
    void shouldRecognizePosInstallmentReversal() {
        String posInstallmentReversalString = "lISO0260000100420B23882812AA0801800000040100000041U3200000000099900081307594502858909480008110813004001119100822634376020202000572847=2011221000000000000000000100100368E0822634        KTC DD RC-8A KARLOVAC KARLOVAC        HR1910162400PRO1+00000000192400PRO104100 000000200000001001003081109480000081300000000001124000080000038210000000000000                    0000";
        HISOMessage posInstallmentReversal = OLBCodec.decode(posInstallmentReversalString);

        assertThat(RequestDetector.determineType(posInstallmentReversal), is(RequestType.POS_INSTALLMENT_REVERSAL));
    }

    @Test
    void shouldRecognizePosInstallmentCancellationReversal() {
        String posInstallmentCancellationReversalString = "lISO0260000100420B23882812AA0801800000040100000042U3200000000029900112613143002992914052411261126003001119100221482376020202000331566=1604221000000000000000000100100268E0221482        T.O. MONI             KARLOVAC        HR1910162400PRO1+00000000192400PRO104100 000000220000001001001112614052400112600000000001124000080000038210000000000000                    0000";
        HISOMessage posInstallmentCancellationReversal = OLBCodec.decode(posInstallmentCancellationReversalString);

        assertThat(RequestDetector.determineType(posInstallmentCancellationReversal), is(RequestType.POS_INSTALLMENT_CANCELLATION_REVERSAL));
    }

    @Test
    void shouldRecognizePosOnlineReversal() {
        String posOnlineReversalString = "RISO0260000700420B23880812AA08018000000401000000480003000000000063509191417420259101617380919091915044441215189222400878478=1912351322      1700000001        PP* EBAY              4029357733   LUXLU191016EUROEURO-12000000192400PRO1031000000000200351322000000091914173800091900000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posOnlineReversal = OLBCodec.decode(posOnlineReversalString);

        assertThat(RequestDetector.determineType(posOnlineReversal), is(RequestType.POS_ONLINE_REVERSAL));
    }

}