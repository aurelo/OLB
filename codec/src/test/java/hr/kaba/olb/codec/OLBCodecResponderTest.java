package hr.kaba.olb.codec;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OLBCodecResponderTest {

    @Test
    public void shouldRespondToPOSAdvice() {
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

        HISOMessage posAdviceResponse = OLBCodec.respondTo(posAdviceRequest, responseFields);

        assertEquals(MessageType.TRX_ADVICE_RESP, posAdviceResponse.getMessageType());
        assertEquals(expectedFields, posAdviceResponse.getFields());
        assertEquals(posAdviceResponseString, OLBCodec.encode(posAdviceResponse));


    }

}