package hr.kaba.olb.codec.constants;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

class ResponseCodeTest {
    @Test
    public void shouldReturnCorrectCodesForMessegeType() {

        List<ResponseCode> nmmResponseCodeList = new ArrayList<>();
        nmmResponseCodeList.add(ResponseCode.NMM_APPROVED);
        nmmResponseCodeList.add(ResponseCode.NMM_REJECTED);
        nmmResponseCodeList.add(ResponseCode.NMM_DPC_DOWN);

        assertThat(nmmResponseCodeList, is(ResponseCode.validResponseCodes((MessageType.NMM_RESP))));
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

    @Test
    void shoudGetResponseFromString() {
        assertAll(
                 () -> assertThat(ResponseCode.from("00", MessageType.NMM_RESP), is(ResponseCode.NMM_APPROVED))
                ,() -> assertThat(ResponseCode.from("51", MessageType.TRX_RESP), is(ResponseCode.TRX_NOT_SUFFICIENT_FUNDS))
        );
    }

    @Test
    void approvedIsValidForAdviceRepeats() {
        assertTrue(ResponseCode.TRX_APPROVED.isValidFor(MessageType.TRX_ADVICE_RESP));
    }

    @Test
    void shouldGetCorrectResponseCodeForTrxAdvice() {
        String responseString = "00";
        MessageType requestType = MessageType.TRX_ADVICE_REPEAT;

        assertThat(ResponseCode.TRX_APPROVED, is(ResponseCode.from(responseString, MessageType.responseFor(requestType))));

    }


}