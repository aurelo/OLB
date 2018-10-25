package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestDetectorRejectsTest {

    @Test
    void shouldDetectAdviceResponseReject() {
        String adviceResponseRejectString = "ISO0260100559430B22000012A808008000000401000000400003000000000010010241103200010001119100265006215590722400899948=191200000000404300V0265006        1910192400TES103100000000010000000000404310231443420010230000000000";

        HISOMessage adviceResponseReject = OLBCodec.decode(adviceResponseRejectString);

        assertAll(
                () -> assertThat(RequestDetector.determineType(adviceResponseReject), is(not(RequestType.UNKNOWN))),
                () -> assertThat(RequestDetector.determineType(adviceResponseReject), is(RequestType.REJECT))
        );
    }

}