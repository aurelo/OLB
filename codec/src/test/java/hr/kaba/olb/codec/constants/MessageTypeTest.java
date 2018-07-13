package hr.kaba.olb.codec.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTypeTest {
    @Test
    public void testParsingTrxReq() {
        assertEquals(MessageType.TRX_REQ, MessageType.from("0200"));
    }

}