package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.Base24Header;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class OLBCodecEncodingTest {

    @Test
    void shouldEncodeLoginRequest() {
        String logonRequestString = "ISO0060000100800822000000000000004000000000000000619111227000001001";

        Map<BitmapField, String> presentFields = new HashMap<>();

        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111227");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(SecondaryBitmapField.P70, "001"); //NetworkManagementInformationCode.LOGON

        HISOMessage logonRequest = new OLBMessage(Base24Header.parse("006000010"), MessageType.NMM_REQ, presentFields);

        assertThat(OLBCodec.encode(logonRequest), is(logonRequestString));
    }

    @Test
    void shouldEncodeLoginResponse() {
        String logonResonseString = "ISO006000051081082200000020000000400000000000000061911125100000100001";

        Map<BitmapField, String> presentFields = new HashMap<>();

        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111251");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(PrimaryBitmapField.P39, "00");
        presentFields.put(SecondaryBitmapField.P70, "001");

        HISOMessage logonResponse = new OLBMessage(Base24Header.parse("006000051"), MessageType.NMM_RESP, presentFields);

        assertThat(OLBCodec.encode(logonResponse), is(logonResonseString));
    }


    @Test
    void shouldEncodeEchoRequest() {
        String echoRequestString = "ISO0060000100800822000000000000004000000000000000619111236000001301";

        Map<BitmapField, String> presentFields = new HashMap<>();

        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111236");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(SecondaryBitmapField.P70, "301");

        HISOMessage echoRequest = new OLBMessage(Base24Header.parse("006000010"), MessageType.NMM_REQ, presentFields);

        assertThat(OLBCodec.encode(echoRequest), is(echoRequestString));

    }

    @Test
    void shouldEncodeEchoResponse() {
        String echoResponseString = "ISO006000015081082200000020000000400000000000000061911125300000100301";

        Map<BitmapField, String> presentFields = new HashMap<>();
        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111253");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(PrimaryBitmapField.P39, "00");
        presentFields.put(SecondaryBitmapField.P70, "301");


        HISOMessage echoResponse = new OLBMessage(Base24Header.parse("006000015"), MessageType.NMM_RESP, presentFields);

        assertThat(OLBCodec.encode(echoResponse), is(echoResponseString));
    }


    @Test
    void shouldEncodeLogoffRequest() {
        String logoffRequestString = "ISO0060000400800822000000000000004000000000000000619111247000001002";

        Map<BitmapField, String> presentFields = new HashMap<>();
        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111247");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(SecondaryBitmapField.P70, "002");

        HISOMessage logoffRequest = new OLBMessage(Base24Header.parse("006000040"), MessageType.NMM_REQ, presentFields);

        assertThat(OLBCodec.encode(logoffRequest), is(logoffRequestString));

    }

    @Test
    void shouldEncodeLogoffResponse() {
        String logoffResponseString = "ISO006000015081082200000020000000400000000000000061911125600000100002";

        Map<BitmapField, String> presentFields = new HashMap<>();
        presentFields.put(PrimaryBitmapField.P1, "0400000000000000");
        presentFields.put(PrimaryBitmapField.P7, "0619111256");
        presentFields.put(PrimaryBitmapField.P11, "000001");
        presentFields.put(PrimaryBitmapField.P39, "00");
        presentFields.put(SecondaryBitmapField.P70, "002");

        HISOMessage logoffReponse = new OLBMessage(Base24Header.parse("006000015"), MessageType.NMM_RESP, presentFields);

        assertThat(OLBCodec.encode(logoffReponse), is(logoffResponseString));
    }

    @Test
    void shouldEncodeATMDepositRequest() {
        String depositRequestString = "ISO0160000100200B238800128A0801800000000100000002100300000000050000628073926016875093926062806281119100055555375590722400899948=191220100000011170005961        S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P1124000000000";

        Map<BitmapField, String> presentFields = new HashMap<>();


        presentFields.put(PrimaryBitmapField.P1, "0000000010000000");
        presentFields.put(PrimaryBitmapField.P3, "210030");
        presentFields.put(PrimaryBitmapField.P4, "000000005000");
        presentFields.put(PrimaryBitmapField.P7, "0628073926");
        presentFields.put(PrimaryBitmapField.P11, "016875");
        presentFields.put(PrimaryBitmapField.P12, "093926");
        presentFields.put(PrimaryBitmapField.P13, "0628");
        presentFields.put(PrimaryBitmapField.P17, "0628");
        presentFields.put(PrimaryBitmapField.P32, "19100055555");
        presentFields.put(PrimaryBitmapField.P35, "5590722400899948=19122010000001117000");
        presentFields.put(PrimaryBitmapField.P37, "5961");
        presentFields.put(PrimaryBitmapField.P41, "S1AWNIPV");
        presentFields.put(PrimaryBitmapField.P43, "IZ SIM                ZAGREB IZ       HR");
        presentFields.put(PrimaryBitmapField.P49, "191");
        presentFields.put(PrimaryBitmapField.P60A, "0122402TES1+000");
        presentFields.put(PrimaryBitmapField.P61A, "0132400TES10031P");
        presentFields.put(SecondaryBitmapField.S100, "24000000000");


        HISOMessage depositRequest = new OLBMessage(Base24Header.parse("016000010"), MessageType.TRX_REQ, presentFields);

        assertThat(OLBCodec.encode(depositRequest), is(depositRequestString));

    }

    @Test
    void shouldEncodeATMisplataRequest() {
        String atmIsplataReqString = "ISO0160000100200B238820128A0801800000000100000000120000000000400000607091818011583111818060706070021119101247005376020202001939466=190422100000216080003347        A1247005        KABA D.RESA-STARA POSLDUGA RESA    HR HR1910122400PRO1+0000132400PRO10100P1124000080000";

        Map<BitmapField, String> presentFields = new HashMap<>();


        presentFields.put(PrimaryBitmapField.P1, "0000000010000000");
        presentFields.put(PrimaryBitmapField.P3, "012000");
        presentFields.put(PrimaryBitmapField.P4, "000000040000");
        presentFields.put(PrimaryBitmapField.P7, "0607091818");
        presentFields.put(PrimaryBitmapField.P11, "011583");
        presentFields.put(PrimaryBitmapField.P12, "111818");
        presentFields.put(PrimaryBitmapField.P13, "0607");
        presentFields.put(PrimaryBitmapField.P17, "0607");
        presentFields.put(PrimaryBitmapField.P23, "002");
        presentFields.put(PrimaryBitmapField.P32, "19101247005");
        presentFields.put(PrimaryBitmapField.P35, "6020202001939466=19042210000021608000");
        presentFields.put(PrimaryBitmapField.P37, "3347");
        presentFields.put(PrimaryBitmapField.P41, "A1247005");
        presentFields.put(PrimaryBitmapField.P43, "KABA D.RESA-STARA POSLDUGA RESA    HR HR");
        presentFields.put(PrimaryBitmapField.P49, "191");
        presentFields.put(PrimaryBitmapField.P60A, "0122400PRO1+000");
        presentFields.put(PrimaryBitmapField.P61A, "0132400PRO10100P");
        presentFields.put(SecondaryBitmapField.S100, "24000080000");

        HISOMessage atmIsplataReq = new OLBMessage(Base24Header.parse("016000010"), MessageType.TRX_REQ, presentFields);

        assertThat(OLBCodec.encode(atmIsplataReq), is(atmIsplataReqString));
    }

    @Test
    void shouldEncodePOSProdajaRateRequest() {
        String posProdajaRateString = "ISO0260000100200B238828128A0801800000000100000061U3200000000047869060709201901160411201606070607003001119100123045376020202000880893=18102210000031179000000001001031E0123045        MULLER KARLOVAC       KARLOVAC        HR1910162400PRO1+00000000192400PRO1041        1124000080000038210000000000000                    000134000000                20600000000002                                                                  E0123045    0000000000010010311 ";

        Map<BitmapField, String> presentFields = new HashMap<>();

        presentFields.put(PrimaryBitmapField.P1, "0000000010000006");
        presentFields.put(PrimaryBitmapField.P3, "1U3200");
        presentFields.put(PrimaryBitmapField.P4, "000000047869");
        presentFields.put(PrimaryBitmapField.P7, "0607092019");
        presentFields.put(PrimaryBitmapField.P11, "011604");
        presentFields.put(PrimaryBitmapField.P12, "112016");
        presentFields.put(PrimaryBitmapField.P13, "0607");
        presentFields.put(PrimaryBitmapField.P17, "0607");
        presentFields.put(PrimaryBitmapField.P23, "003");
        presentFields.put(PrimaryBitmapField.P25, "00");
        presentFields.put(PrimaryBitmapField.P32, "19100123045");
        presentFields.put(PrimaryBitmapField.P35, "6020202000880893=18102210000031179000");
        presentFields.put(PrimaryBitmapField.P37, "000001001031");
        presentFields.put(PrimaryBitmapField.P41, "E0123045");
        presentFields.put(PrimaryBitmapField.P43, "MULLER KARLOVAC       KARLOVAC        HR");
        presentFields.put(PrimaryBitmapField.P49, "191");
        presentFields.put(PrimaryBitmapField.P60P, "0162400PRO1+0000000");
        presentFields.put(PrimaryBitmapField.P61P, "0192400PRO1041");
        presentFields.put(SecondaryBitmapField.S100, "24000080000");
        presentFields.put(SecondaryBitmapField.S126P, "038210000000000000                    000");
        presentFields.put(SecondaryBitmapField.S127P, "000000                20600000000002                                                                  E0123045    0000000000010010311 ");


        HISOMessage posProdajaRate = new OLBMessage(Base24Header.parse("026000010"), MessageType.TRX_REQ, presentFields);

        assertThat(OLBCodec.encode(posProdajaRate), is(posProdajaRateString));

    }

}