package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import hr.kaba.olb.codec.message.field.TransactionCode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OLBCodecParsingTest {

    @Test
    void testDecodingLogonRequest() {
        String logonRequestString = "DISO0060000100800822000000000000004000000000000000619111227000001001";
        HISOMessage logonRequest = OLBCodec.decode(logonRequestString);

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111227");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(SecondaryBitmapField.P70, "001"); //NetworkManagementInformationCode.LOGON


        assertAll(
                () -> assertNotNull(logonRequest)
                , () -> assertEquals(ProductIndicator.NMM, logonRequest.getProductType())
                , () -> assertEquals(MessageType.NMM_REQ, logonRequest.getMessageType())
                , () -> assertEquals(expectedFields, logonRequest.getFields())
                , () -> assertFalse(logonRequest.getTransactionCode().isPresent())
        );

    }

    @Test
    void testDecodingLogonResponse() {
        String logonResonseString = "FISO006000051081082200000020000000400000000000000061911125100000100001";
        HISOMessage logonResponse = OLBCodec.decode(logonResonseString);

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111251");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(SecondaryBitmapField.P70, "001");


        assertAll(
                () -> assertNotNull(logonResponse)
                , () -> assertEquals(ProductIndicator.NMM, logonResponse.getProductType())
                , () -> assertEquals(MessageType.NMM_RESP, logonResponse.getMessageType())
                , () -> assertEquals(expectedFields, logonResponse.getFields())
                , () -> assertFalse(logonResponse.getTransactionCode().isPresent())
        );
    }

    @Test
    void testDecodingEchoRequest() {
        String echoRequestString = "DISO0060000100800822000000000000004000000000000000619111236000001301";

        HISOMessage echoRequest = OLBCodec.decode(echoRequestString);

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111236");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(SecondaryBitmapField.P70, "301");


        assertNotNull(echoRequest);
        assertEquals(ProductIndicator.NMM, echoRequest.getProductType());
        assertEquals(MessageType.NMM_REQ, echoRequest.getMessageType());

        assertEquals(expectedFields, echoRequest.getFields());

        assertFalse(echoRequest.getTransactionCode().isPresent());

    }

    @Test
    void testDecodingEchoResponse() {
        String echoResponseString = "FISO006000015081082200000020000000400000000000000061911125300000100301";

        Map<BitmapField, String> expectedFields = new HashMap<>();
        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111253");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(SecondaryBitmapField.P70, "301");


        HISOMessage echoResponse = OLBCodec.decode(echoResponseString);

        assertAll(
                () -> assertNotNull(echoResponse)
                , () -> assertEquals(ProductIndicator.NMM, echoResponse.getProductType())
                , () -> assertEquals(MessageType.NMM_RESP, echoResponse.getMessageType())
                , () -> assertEquals(expectedFields, echoResponse.getFields())
                , () -> assertFalse(echoResponse.getTransactionCode().isPresent())
        );

    }

    @Test
    public void testDecodingLogoffRequest() {
        String logoffRequestString = "DISO0060000400800822000000000000004000000000000000619111247000001002";

        Map<BitmapField, String> expectedFields = new HashMap<>();
        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111247");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(SecondaryBitmapField.P70, "002");

        HISOMessage logoffRequest = OLBCodec.decode(logoffRequestString);

        assertAll(
                () -> assertNotNull(logoffRequestString)
                , () -> assertEquals(ProductIndicator.NMM, logoffRequest.getProductType())
                , () -> assertEquals(MessageType.NMM_REQ, logoffRequest.getMessageType())
                , () -> assertEquals(expectedFields, logoffRequest.getFields())
                , () -> assertFalse(logoffRequest.getTransactionCode().isPresent())
        );
    }

    @Test
    public void testDecodingLogoffResponse() {
        String logoffResponseString = "FISO006000015081082200000020000000400000000000000061911125600000100002";

        Map<BitmapField, String> expectedFields = new HashMap<>();
        expectedFields.put(PrimaryBitmapField.P1, "0400000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0619111256");
        expectedFields.put(PrimaryBitmapField.P11, "000001");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(SecondaryBitmapField.P70, "002");
        HISOMessage logoffReponse = OLBCodec.decode(logoffResponseString);


        assertAll(
                () -> assertNotNull(logoffReponse)
                , () -> assertEquals(ProductIndicator.NMM, logoffReponse.getProductType())
                , () -> assertEquals(MessageType.NMM_RESP, logoffReponse.getMessageType())
                , () -> assertEquals(expectedFields, logoffReponse.getFields())
                , () -> assertFalse(logoffReponse.getTransactionCode().isPresent())
        );
    }

    @Test
    public void testDecodingATMDepositRequest() {
        String depositRequestString = "ISO0160000100200B238800128A0801800000000100000002100300000000050000628073926016875093926062806281119100055555375590722400899948=191220100000011170005961        S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P11240000000000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        HISOMessage depositRequest = OLBCodec.decode(depositRequestString);

        expectedFields.put(PrimaryBitmapField.P1, "0000000010000000");
        expectedFields.put(PrimaryBitmapField.P3, "210030");
        expectedFields.put(PrimaryBitmapField.P4, "000000005000");
        expectedFields.put(PrimaryBitmapField.P7, "0628073926");
        expectedFields.put(PrimaryBitmapField.P11, "016875");
        expectedFields.put(PrimaryBitmapField.P12, "093926");
        expectedFields.put(PrimaryBitmapField.P13, "0628");
        expectedFields.put(PrimaryBitmapField.P17, "0628");
        expectedFields.put(PrimaryBitmapField.P32, "19100055555");
        expectedFields.put(PrimaryBitmapField.P35, "5590722400899948=19122010000001117000");
        expectedFields.put(PrimaryBitmapField.P37, "5961");
        expectedFields.put(PrimaryBitmapField.P41, "S1AWNIPV");
        expectedFields.put(PrimaryBitmapField.P43, "IZ SIM                ZAGREB IZ       HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60A, "0122402TES1+000");
        expectedFields.put(PrimaryBitmapField.P61A, "0132400TES10031P");
        expectedFields.put(SecondaryBitmapField.S100, "24000000000");


        assertAll(
                () -> assertEquals(ProductIndicator.ATM, depositRequest.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, depositRequest.getMessageType())
                , () -> assertEquals(expectedFields, depositRequest.getFields())
                , () -> assertTrue(depositRequest.getTransactionCode().isPresent())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.DEPOSIT, depositRequest.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingAtmIsplataRequest() {
        String atmIsplataReqString = "ISO0160000100200B238820128A0801800000000100000000120000000000400000607091818011583111818060706070021119101247005376020202001939466=190422100000216080003347        A1247005        KABA D.RESA-STARA POSLDUGA RESA    HR HR1910122400PRO1+0000132400PRO10100P11240000800000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        HISOMessage atmIsplataReq = OLBCodec.decode(atmIsplataReqString);

        expectedFields.put(PrimaryBitmapField.P1, "0000000010000000");
        expectedFields.put(PrimaryBitmapField.P3, "012000");
        expectedFields.put(PrimaryBitmapField.P4, "000000040000");
        expectedFields.put(PrimaryBitmapField.P7, "0607091818");
        expectedFields.put(PrimaryBitmapField.P11, "011583");
        expectedFields.put(PrimaryBitmapField.P12, "111818");
        expectedFields.put(PrimaryBitmapField.P13, "0607");
        expectedFields.put(PrimaryBitmapField.P17, "0607");
        expectedFields.put(PrimaryBitmapField.P23, "002");
        expectedFields.put(PrimaryBitmapField.P32, "19101247005");
        expectedFields.put(PrimaryBitmapField.P35, "6020202001939466=19042210000021608000");
        expectedFields.put(PrimaryBitmapField.P37, "3347");
        expectedFields.put(PrimaryBitmapField.P41, "A1247005");
        expectedFields.put(PrimaryBitmapField.P43, "KABA D.RESA-STARA POSLDUGA RESA    HR HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60A, "0122400PRO1+000");
        expectedFields.put(PrimaryBitmapField.P61A, "0132400PRO10100P");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");


        assertAll(
                () -> assertEquals(ProductIndicator.ATM, atmIsplataReq.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, atmIsplataReq.getMessageType())
                , () -> assertEquals(expectedFields, atmIsplataReq.getFields())
                , () -> assertTrue(atmIsplataReq.getTransactionCode().isPresent())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.WITHDRAWAL, atmIsplataReq.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingPosProdajaRateRequest() {
        String posProdajaRateString = "ISO0260000100200B238828128A0801800000000100000061U3200000000047869060709201901160411201606070607003001119100123045376020202000880893=18102210000031179000000001001031E0123045        MULLER KARLOVAC       KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        1124000080000038210000000000000                    000134000000                20600000000002                                                                  E0123045    0000000000010010311  ";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000000010000006");
        expectedFields.put(PrimaryBitmapField.P3, "1U3200");
        expectedFields.put(PrimaryBitmapField.P4, "000000047869");
        expectedFields.put(PrimaryBitmapField.P7, "0607092019");
        expectedFields.put(PrimaryBitmapField.P11, "011604");
        expectedFields.put(PrimaryBitmapField.P12, "112016");
        expectedFields.put(PrimaryBitmapField.P13, "0607");
        expectedFields.put(PrimaryBitmapField.P17, "0607");
        expectedFields.put(PrimaryBitmapField.P23, "003");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "19100123045");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000880893=18102210000031179000");
        expectedFields.put(PrimaryBitmapField.P37, "000001001031");
        expectedFields.put(PrimaryBitmapField.P41, "E0123045");
        expectedFields.put(PrimaryBitmapField.P43, "MULLER KARLOVAC       KARLOVAC        HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "0162400PRO1+0000000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO1041");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "038210000000000000                    000");
        expectedFields.put(SecondaryBitmapField.S127P, "000000                20600000000002                                                                  E0123045    0000000000010010311");


        HISOMessage posProdajaRate = OLBCodec.decode(posProdajaRateString);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, posProdajaRate.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, posProdajaRate.getMessageType())
                , () -> assertEquals(expectedFields, posProdajaRate.getFields())
                , () -> assertTrue(posProdajaRate.getTransactionCode().isPresent())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.MBNET_INSTALMENT, posProdajaRate.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingPosProdajaRequest() {
        String posProdajaRequestString = ":ISO0260000700200B238828128A080180000000010000004002000000000004578060709182601158511182606070607003000513984376020202000998265=18122210000031825000261842      61321364        Kaufland HR 1030      Karlovac     HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000000010000004");
        expectedFields.put(PrimaryBitmapField.P3, "002000");
        expectedFields.put(PrimaryBitmapField.P4, "000000004578");
        expectedFields.put(PrimaryBitmapField.P7, "0607091826");
        expectedFields.put(PrimaryBitmapField.P11, "011585");
        expectedFields.put(PrimaryBitmapField.P12, "111826");
        expectedFields.put(PrimaryBitmapField.P13, "0607");
        expectedFields.put(PrimaryBitmapField.P17, "0607");
        expectedFields.put(PrimaryBitmapField.P23, "003");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "13984");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000998265=18122210000031825000");
        expectedFields.put(PrimaryBitmapField.P37, "261842");
        expectedFields.put(PrimaryBitmapField.P41, "61321364");
        expectedFields.put(PrimaryBitmapField.P43, "Kaufland HR 1030      Karlovac     HRVHR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "016EUROEURO-1200000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO100100000000");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "03800000000000000000000000000000000000000");

        HISOMessage posProdajaRequest = OLBCodec.decode(posProdajaRequestString);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, posProdajaRequest.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, posProdajaRequest.getMessageType())
                , () -> assertEquals(expectedFields, posProdajaRequest.getFields())
                , () -> assertTrue(posProdajaRequest.getTransactionCode().isPresent())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES, posProdajaRequest.getISOTransactionalCode().get())
        );
    }


    @Test
    public void testDecodingAtmUpitUStanje() {
        String atmUpitUStanjeReqString = "ISO0160000100200B238820128A0801800000000100000003120000000000000000607092142011619112142060706070041119101147017376020202000504436=200922100000418220009064        A1147017        KABA INTERSPAR        KARLOVAC     HR HR1910122400PRO1+0000132400PRO10100P11240000800000";

        HISOMessage atmUpitUStanjeReq = OLBCodec.decode(atmUpitUStanjeReqString);

        Map<BitmapField, String> expectedFields = new HashMap<>();


        expectedFields.put(PrimaryBitmapField.P1, "0000000010000000");
        expectedFields.put(PrimaryBitmapField.P3, "312000");
        expectedFields.put(PrimaryBitmapField.P4, "000000000000");
        expectedFields.put(PrimaryBitmapField.P7, "0607092142");
        expectedFields.put(PrimaryBitmapField.P11, "011619");
        expectedFields.put(PrimaryBitmapField.P12, "112142");
        expectedFields.put(PrimaryBitmapField.P13, "0607");
        expectedFields.put(PrimaryBitmapField.P17, "0607");
        expectedFields.put(PrimaryBitmapField.P23, "004");
        expectedFields.put(PrimaryBitmapField.P32, "19101147017");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000504436=20092210000041822000");
        expectedFields.put(PrimaryBitmapField.P37, "9064");
        expectedFields.put(PrimaryBitmapField.P41, "A1147017");
        expectedFields.put(PrimaryBitmapField.P43, "KABA INTERSPAR        KARLOVAC     HR HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60A, "0122400PRO1+000");
        expectedFields.put(PrimaryBitmapField.P61A, "0132400PRO10100P");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");


        assertAll(
                () -> assertEquals(ProductIndicator.ATM, atmUpitUStanjeReq.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, atmUpitUStanjeReq.getMessageType())
                , () -> assertEquals(expectedFields, atmUpitUStanjeReq.getFields())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.BALANCE_INQUIRY, atmUpitUStanjeReq.getISOTransactionalCode().get())
        );
    }


    @Test
    public void testDecodingPosRateStorno() {
        String posRateStornoString = "ISO0260000100220B23882812AA0801800000040100000062U3200000000050000062612435902520314414106260626005001119100822610376020202000359625=2003221000000000000000000100100700E0822610        GRAWE HRVATSKA DD     KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        0200000001001006062614414100062600000000001124000080000038210000000000000                    000134000000                111000                                                                                      0010010060010010071  ";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000004010000006");
        expectedFields.put(PrimaryBitmapField.P3, "2U3200");
        expectedFields.put(PrimaryBitmapField.P4, "000000050000");
        expectedFields.put(PrimaryBitmapField.P7, "0626124359");
        expectedFields.put(PrimaryBitmapField.P11, "025203");
        expectedFields.put(PrimaryBitmapField.P12, "144141");
        expectedFields.put(PrimaryBitmapField.P13, "0626");
        expectedFields.put(PrimaryBitmapField.P17, "0626");
        expectedFields.put(PrimaryBitmapField.P23, "005");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "19100822610");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000359625=20032210000000000000");
        expectedFields.put(PrimaryBitmapField.P37, "000001001007");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(PrimaryBitmapField.P41, "E0822610");
        expectedFields.put(PrimaryBitmapField.P43, "GRAWE HRVATSKA DD     KARLOVAC        HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "0162400PRO1+0000000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO1041");
        expectedFields.put(SecondaryBitmapField.S90, "020000000100100606261441410006260000000000");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "038210000000000000                    000");
        expectedFields.put(SecondaryBitmapField.S127P, "000000                111000                                                                                      0010010060010010071");

        HISOMessage posRateStornoReq = OLBCodec.decode(posRateStornoString);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, posRateStornoReq.getProductType())
                , () -> assertEquals(MessageType.TRX_ADVICE, posRateStornoReq.getMessageType())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.MBNET_INSTALMENT_REVERSAL, posRateStornoReq.getISOTransactionalCode().get())
                , () -> assertEquals(expectedFields, posRateStornoReq.getFields())
        );

    }

    @Test
    public void testDecodingProdajaPutemInterneta() {
        String internetProdajaString = "&ISO0260000700200B238808128A08018000000001000000480200000000000100006261115020243001315020626062615043778216020202000405444=2005211819      INT01543        Hattrick              Zagreb       HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000000010000004");
        expectedFields.put(PrimaryBitmapField.P3, "802000");
        expectedFields.put(PrimaryBitmapField.P4, "000000001000");
        expectedFields.put(PrimaryBitmapField.P7, "0626111502");
        expectedFields.put(PrimaryBitmapField.P11, "024300");
        expectedFields.put(PrimaryBitmapField.P12, "131502");
        expectedFields.put(PrimaryBitmapField.P13, "0626");
        expectedFields.put(PrimaryBitmapField.P17, "0626");
        expectedFields.put(PrimaryBitmapField.P25, "15");
        expectedFields.put(PrimaryBitmapField.P32, "3778");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000405444=2005");
        expectedFields.put(PrimaryBitmapField.P37, "211819");
        expectedFields.put(PrimaryBitmapField.P41, "INT01543");
        expectedFields.put(PrimaryBitmapField.P43, "Hattrick              Zagreb       HRVHR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "016EUROEURO-1200000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO100100000000");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "03800000000000000000000000000000000000000");


        HISOMessage internetProdajaReq = OLBCodec.decode(internetProdajaString);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, internetProdajaReq.getProductType())
                , () -> assertEquals(MessageType.TRX_REQ, internetProdajaReq.getMessageType())
                , () -> assertEquals(expectedFields, internetProdajaReq.getFields())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.MAIL_OR_PHONE_ORDER, internetProdajaReq.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingPosPovrat() {
        String posPovratString = "ISO0260000100220B23880812AA080180000004010000006202000000000019900070415190901804217190007040704001119100177136216020202000340146=200400000100100800E0177136        NINA PARIS OBRT       KARLOVAC        HR1910162400PRO1+00000000192400PRO1000        0200000001001008070417190000070400000000001124000080000038210000000000000                    000134000000                                                                                                E0177136    0000000000010010081  ";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000004010000006");
        expectedFields.put(PrimaryBitmapField.P3, "202000");
        expectedFields.put(PrimaryBitmapField.P4, "000000019900");
        expectedFields.put(PrimaryBitmapField.P7, "0704151909");
        expectedFields.put(PrimaryBitmapField.P11, "018042");
        expectedFields.put(PrimaryBitmapField.P12, "171900");
        expectedFields.put(PrimaryBitmapField.P13, "0704");
        expectedFields.put(PrimaryBitmapField.P17, "0704");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "19100177136");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000340146=2004");
        expectedFields.put(PrimaryBitmapField.P37, "000001001008");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(PrimaryBitmapField.P41, "E0177136");
        expectedFields.put(PrimaryBitmapField.P43, "NINA PARIS OBRT       KARLOVAC        HR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "0162400PRO1+0000000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO1000");
        expectedFields.put(SecondaryBitmapField.S90, "020000000100100807041719000007040000000000");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "038210000000000000                    000");
        expectedFields.put(SecondaryBitmapField.S127P, "000000                                                                                                E0177136    0000000000010010081");

        HISOMessage posPovratReq = OLBCodec.decode(posPovratString);

        assertAll(
                () -> assertEquals(ProductIndicator.POS, posPovratReq.getProductType())
                , () -> assertEquals(MessageType.TRX_ADVICE, posPovratReq.getMessageType())
                , () -> assertEquals(expectedFields, posPovratReq.getFields())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.RETURNS, posPovratReq.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingPosKorekcijaPovrataSredstava() {
        String posKorekcijaPovratSredstava = "ISO0260000750220B23880812AA080180000004210000004220030000000176431063008020502132409551606300630000512876375590722400800441=20052010000000000000041191      00NF058911        TOTAL MKT FR          NANTERRE     FRAFR191016EUROEURO-12000000192400PRO1031000000000200041191      06300955160006300000000000000000176099                              1124000080000038000000000000000000000000000000000000000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000004210000004");
        expectedFields.put(PrimaryBitmapField.P3, "220030");
        expectedFields.put(PrimaryBitmapField.P4, "000000176431");
        expectedFields.put(PrimaryBitmapField.P7, "0630080205");
        expectedFields.put(PrimaryBitmapField.P11, "021324");
        expectedFields.put(PrimaryBitmapField.P12, "095516");
        expectedFields.put(PrimaryBitmapField.P13, "0630");
        expectedFields.put(PrimaryBitmapField.P17, "0630");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "12876");
        expectedFields.put(PrimaryBitmapField.P35, "5590722400800441=20052010000000000000");
        expectedFields.put(PrimaryBitmapField.P37, "041191");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(PrimaryBitmapField.P41, "NF058911");
        expectedFields.put(PrimaryBitmapField.P43, "TOTAL MKT FR          NANTERRE     FRAFR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "016EUROEURO-1200000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO103100000000");
        expectedFields.put(SecondaryBitmapField.S90, "0200041191      06300955160006300000000000");
        expectedFields.put(SecondaryBitmapField.S95, "000000176099");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "03800000000000000000000000000000000000000");


        HISOMessage posKorekcijaPovrataReq = OLBCodec.decode(posKorekcijaPovratSredstava);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, posKorekcijaPovrataReq.getProductType())
                , () -> assertEquals(MessageType.TRX_ADVICE, posKorekcijaPovrataReq.getMessageType())
                , () -> assertEquals(expectedFields, posKorekcijaPovrataReq.getFields())
                , () -> assertEquals(TransactionCode.ISOTransactionCode.CREDIT_ADJUSTMENT, posKorekcijaPovrataReq.getISOTransactionalCode().get())
        );
    }


    @Test
    public void testDecodingPosKorekcijaPovrataResponse() {
        String posKorekcijaPovrataRespString = "ISO0260000750230322000012A80800822003000000017643106300802050213240512876375590722400800441=20052010000000000000041191      00NF058911        1910192400PRO1031000000000";

        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P1, "0000004210000004");
        expectedFields.put(PrimaryBitmapField.P3, "220030");
        expectedFields.put(PrimaryBitmapField.P4, "000000176431");
        expectedFields.put(PrimaryBitmapField.P7, "0630080205");
        expectedFields.put(PrimaryBitmapField.P11, "021324");
        expectedFields.put(PrimaryBitmapField.P12, "095516");
        expectedFields.put(PrimaryBitmapField.P13, "0630");
        expectedFields.put(PrimaryBitmapField.P17, "0630");
        expectedFields.put(PrimaryBitmapField.P25, "00");
        expectedFields.put(PrimaryBitmapField.P32, "12876");
        expectedFields.put(PrimaryBitmapField.P35, "5590722400800441=20052010000000000000");
        expectedFields.put(PrimaryBitmapField.P37, "041191");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(PrimaryBitmapField.P41, "NF058911");
        expectedFields.put(PrimaryBitmapField.P43, "TOTAL MKT FR          NANTERRE     FRAFR");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P60P, "016EUROEURO-1200000");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO103100000000");
        expectedFields.put(SecondaryBitmapField.S90, "0200041191      06300955160006300000000000");
        expectedFields.put(SecondaryBitmapField.S95, "000000176099");
        expectedFields.put(SecondaryBitmapField.S100, "24000080000");
        expectedFields.put(SecondaryBitmapField.S126P, "03800000000000000000000000000000000000000");


        HISOMessage posKorekcijaPovrataResp = OLBCodec.decode(posKorekcijaPovrataRespString);


        assertAll(
                () -> assertEquals(ProductIndicator.POS, posKorekcijaPovrataResp.getProductType())
                , () -> assertEquals(MessageType.TRX_ADVICE_RESP, posKorekcijaPovrataResp.getMessageType())
//        assertEquals(expectedFields, posKorekcijaPovrataResp.getFields());
                , () -> assertEquals(TransactionCode.ISOTransactionCode.CREDIT_ADJUSTMENT, posKorekcijaPovrataResp.getISOTransactionalCode().get())
        );
    }

    @Test
    public void testDecodingEchoResponseFromPreviousImplementation() {
        String echoResonseString = "FISO006000015081082200000020000000400000000000000082915381500000100301";

        HISOMessage echoResponse = OLBCodec.decode(echoResonseString);

        assertNotNull(OLBCodec.decode(echoResonseString));
    }

}