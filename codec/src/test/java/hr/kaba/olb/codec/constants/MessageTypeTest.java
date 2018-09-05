package hr.kaba.olb.codec.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

class MessageTypeTest {
    @Test
    public void testParsingTrxReq() {
        assertThat(MessageType.from("0200"), is(MessageType.TRX_REQ));
    }

}