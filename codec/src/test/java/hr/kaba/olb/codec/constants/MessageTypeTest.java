package hr.kaba.olb.codec.constants;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.NotNull;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.notNull;

class MessageTypeTest {
    @Test
    public void testParsingTrxReq() {
        assertThat(MessageType.from("0200"), is(MessageType.TRX_REQ));
    }


    @Test
    void returnsCorrectResponseForTrxAdvice() {
        assertThat(MessageType.TRX_ADVICE_RESP, is(MessageType.responseFor(MessageType.TRX_ADVICE_REPEAT)));
    }

    @Test
    void shouldRecognizeTrxReversalResponseReject() {
        assertThat(MessageType.from("9430"), is(MessageType.TRX_REVERSAL_RESP_REJECT));
    }

    @Test
    void shouldRecognizeRejects() {

        assertAll(
                () -> assertTrue(MessageType.TRX_ADVICE_REJECT.isReject()),
                () -> assertTrue(MessageType.AUTHORIZATION_ADVICE_REJECT.isReject()),
                () -> assertFalse(MessageType.TRX_ADVICE.isReject())
        );

    }
}

