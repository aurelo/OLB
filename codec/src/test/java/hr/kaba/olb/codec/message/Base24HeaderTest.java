package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base24HeaderTest {

    @Test
    void testParsingNullString() {
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse(null));

    }

    @Test
    void testParsingNonSensicalString() {
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse("12356"));
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse("12356ASDASFAFWEDEAWSDW"));
    }


    @Test
    void testParsingLogonRequest() {

        String logonRequestString = "006000010";
        Base24Header logonRequest = Base24Header.parse(logonRequestString);
        assertNotNull(logonRequest);

        assertEquals(logonRequestString, logonRequest.toString());
        assertEquals(ProductIndicator.NMM, logonRequest.getProductIndicator());
        assertEquals("60", logonRequest.getReleaseNumber());
        assertEquals("000", logonRequest.getStatus());
        assertEquals(InitiatorType.BASE24_TERMINAL, logonRequest.getOriginatorCode());
        assertEquals(InitiatorType.UNKNOWN, logonRequest.getResponderCode());
    }

    @Test
    void testParsingLogonResponse() {
        String logonResponseString = "006000051";

        Base24Header logonResponse = Base24Header.parse(logonResponseString);
        assertNotNull(logonResponse);

        assertEquals(logonResponseString, logonResponse.toString());
        assertEquals(ProductIndicator.NMM, logonResponse.getProductIndicator());
        assertEquals("60", logonResponse.getReleaseNumber());
        assertEquals("000", logonResponse.getStatus());
        assertEquals(InitiatorType.HOST, logonResponse.getOriginatorCode());
        assertEquals(InitiatorType.BASE24_TERMINAL, logonResponse.getResponderCode());
    }

    @Test
    void testParsingEchoRequest() {
        String echoRequestString = "006000010";

        Base24Header echoRequest = Base24Header.parse(echoRequestString);
        assertNotNull(echoRequest);

        assertEquals(echoRequestString, echoRequest.toString());
        assertEquals(ProductIndicator.NMM, echoRequest.getProductIndicator());
        assertEquals("60", echoRequest.getReleaseNumber());
        assertEquals("000", echoRequest.getStatus());
        assertEquals(InitiatorType.BASE24_TERMINAL, echoRequest.getOriginatorCode());
        assertEquals(InitiatorType.UNKNOWN, echoRequest.getResponderCode());
    }

    @Test
    void testParsingEchoResponse() {
        String echoResponseString = "006000015";

        Base24Header echoResponse = Base24Header.parse(echoResponseString);
        assertNotNull(echoResponse);

        assertEquals(echoResponseString, echoResponse.toString());
        assertEquals(ProductIndicator.NMM, echoResponse.getProductIndicator());
        assertEquals("60", echoResponse.getReleaseNumber());
        assertEquals("000", echoResponse.getStatus());
        assertEquals(InitiatorType.BASE24_TERMINAL, echoResponse.getOriginatorCode());
        assertEquals(InitiatorType.HOST, echoResponse.getResponderCode());
    }

    @Test
    void testParsingLogoffRequest() {
        String logoffRequestString = "006000040";

        Base24Header logoffRequest = Base24Header.parse(logoffRequestString);
        assertNotNull(logoffRequest);

        assertEquals(logoffRequestString, logoffRequest.toString());
        assertEquals(ProductIndicator.NMM, logoffRequest.getProductIndicator());
        assertEquals("60", logoffRequest.getReleaseNumber());
        assertEquals("000", logoffRequest.getStatus());
        assertEquals(InitiatorType.HISO_PROCESS, logoffRequest.getOriginatorCode());
        assertEquals(InitiatorType.UNKNOWN, logoffRequest.getResponderCode());
    }

    @Test
    void testParsingLogoffResponse() {
        String logoffResponseString = "006000051";

        Base24Header logoffResponse = Base24Header.parse(logoffResponseString);
        assertNotNull(logoffResponse);

        assertEquals(logoffResponseString, logoffResponse.toString());
        assertEquals(ProductIndicator.NMM, logoffResponse.getProductIndicator());
        assertEquals("60", logoffResponse.getReleaseNumber());
        assertEquals("000", logoffResponse.getStatus());
        assertEquals(InitiatorType.HOST, logoffResponse.getOriginatorCode());
        assertEquals(InitiatorType.BASE24_TERMINAL, logoffResponse.getResponderCode());

        assertFalse(logoffResponse.isATM());
        assertFalse(logoffResponse.isPOS());
    }

    @Test
    void testParsingATMRequest() {
        String atmRequestString = "016000010";

        Base24Header atmRequest = Base24Header.parse(atmRequestString);
        assertNotNull(atmRequest);

        assertEquals(atmRequestString, atmRequest.toString());
        assertEquals(ProductIndicator.ATM, atmRequest.getProductIndicator());
        assertEquals("60", atmRequest.getReleaseNumber());
        assertEquals("000", atmRequest.getStatus());
        assertEquals(InitiatorType.BASE24_TERMINAL, atmRequest.getOriginatorCode());
        assertEquals(InitiatorType.UNKNOWN, atmRequest.getResponderCode());

        assertTrue(atmRequest.isATM());
        assertFalse(atmRequest.isPOS());

    }

}