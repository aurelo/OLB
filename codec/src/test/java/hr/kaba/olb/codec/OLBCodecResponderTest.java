package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OLBCodecResponderTest {

    @Test
    void shouldRespondToPOSAdvice() {
        String posAdviceRequestString = "ISO0260000100220B23880812AA080180000004010000006202000000000019900070415190901804217190007040704001119100177136216020202000340146=200400000100100800E0177136        NINA PARIS OBRT       KARLOVAC        HR1910162400PRO1+00000000192400PRO1000        0200000001001008070417190000070400000000001124000080000038210000000000000                    000134000000                                                                                                E0177136    0000000000010010081 ";
        String posAdviceResponseString = "ISO0260000150230322000012A80800820200000000001990007041519090180421119100177136216020202000340146=200400000100100800E0177136        1910192400PRO1000        ";

        HISOMessage posAdviceRequest = OLBCodec.decode(posAdviceRequestString);

        Map<BitmapField, String> responseFields = new HashMap<>();
        responseFields.put(PrimaryBitmapField.P39, "00");


        Map<BitmapField, String> expectedFields = new HashMap<>();

        expectedFields.put(PrimaryBitmapField.P3, "202000");
        expectedFields.put(PrimaryBitmapField.P4, "000000019900");
        expectedFields.put(PrimaryBitmapField.P7, "0704151909");
        expectedFields.put(PrimaryBitmapField.P11, "018042");
        expectedFields.put(PrimaryBitmapField.P32, "19100177136");
        expectedFields.put(PrimaryBitmapField.P35, "6020202000340146=2004");
        expectedFields.put(PrimaryBitmapField.P37, "000001001008");
        expectedFields.put(PrimaryBitmapField.P39, "00");
        expectedFields.put(PrimaryBitmapField.P41, "E0177136");
        expectedFields.put(PrimaryBitmapField.P49, "191");
        expectedFields.put(PrimaryBitmapField.P61P, "0192400PRO1000");
        expectedFields.put(SecondaryBitmapField.S127P, "000000                                                                                                E0177136    0000000000010010081");

        HISOMessage posAdviceResponse = OLBCodec.respondTo(posAdviceRequest, InitiatorType.HOST, responseFields);

        assertAll(
                () -> assertEquals(MessageType.TRX_ADVICE_RESP, posAdviceResponse.getMessageType())
                , () -> assertEquals(expectedFields, posAdviceResponse.getFields())
//                , () -> assertEquals(posAdviceResponseString, OLBCodec.encode(posAdviceResponse))
        );


    }

    @Test
    void responseFieldsOverrideOriginalValues() {
        String echoRequestString = "DISO00600005008008220000000000000040000000000000012101157270000020011";
        HISOMessage echoRequest = OLBCodec.decode(echoRequestString);


        Map<BitmapField, String> responseFields = new HashMap<>();
        responseFields.put(PrimaryBitmapField.P7, "0823143657");//mmddhhMMss
        responseFields.put(PrimaryBitmapField.P39, "00");

        HISOMessage echoResponse = OLBCodec.respondTo(echoRequest, InitiatorType.HOST, responseFields);

        assertThat(echoResponse.getFields().size(), is(echoRequest.getFields().size() + 1));
        assertThat(echoResponse.getFields().get(PrimaryBitmapField.P7), is("0823143657"));

    }

    @Test
    void shouldReturnOriginalMessageTypeWhenGivenResponse() {
        String echoResponseString = "FISO006000015081082200000020000000400000000000000090309032600000100301";

        HISOMessage echoResponse = OLBCodec.decode(echoResponseString);


        Map<BitmapField, String> responseFields = new HashMap<>();
        responseFields.put(PrimaryBitmapField.P39, "00");

        HISOMessage echoReponseResponse = OLBCodec.respondTo(echoResponse, InitiatorType.HOST, responseFields);

        assertThat(echoReponseResponse.getMessageType(), is(echoResponse.getMessageType()));

    }

}