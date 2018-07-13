package hr.kaba.olb.codec.constants;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseCodeTest {
    @Test
    public void shouldReturnCorrectCodesForMessegeType() {

        List<ResponseCode> nmmResponseCodeList = new ArrayList<>();
        nmmResponseCodeList.add(ResponseCode.NMM_APPROVED);
        nmmResponseCodeList.add(ResponseCode.NMM_REJECTED);
        nmmResponseCodeList.add(ResponseCode.NMM_DPC_DOWN);

        assertEquals(nmmResponseCodeList, ResponseCode.validResponseCodes(MessageType.NMM_RESP));
    }

    @Test
    public void testValidResponses() {
        assertTrue(ResponseCode.isValidResponseForMessage(ResponseCode.NMM_APPROVED, MessageType.NMM_RESP));
    }

    @Test
    public void testIsResponseValidForMessage() {
        assertTrue(ResponseCode.ADVICE_APPROVED.isValidFor(MessageType.AUTHORIZATION_ADVICE_RESP));
        assertTrue(ResponseCode.REVERSAL_COMPLETED_PARTIALLY.isValidFor(MessageType.TRX_REVERSAL_RESP));

        assertFalse(ResponseCode.ADVICE_DUPLICATE_TRANSMISSION.isValidFor(MessageType.FHM_RESP));

        assertTrue(ResponseCode.TRX_APPROVED.isValidFor(MessageType.TRX_RESP));
    }


}