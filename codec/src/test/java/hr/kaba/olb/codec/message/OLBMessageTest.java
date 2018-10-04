package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.OLBCodec;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

class OLBMessageTest {

    @Test
    void testPrettyPrint() {
        String atmIsplata = "ISO0160000100200B238820128A0801800000000100000000120000000000200000921093507007472113507092109210021119101147001376020205002797338=210122100000215550009665        A1147001        KABA TRZNICA          KARLOVAC     HR HR1910122400PRO1+0000132400PRO10100P11240000800000";

        HISOMessage message = OLBCodec.decode(atmIsplata);

        String toBePrinted = message.prettyPrint();

        assertAll(
                 () -> assertThat(toBePrinted, containsString("ATM"))
                ,() -> assertThat(toBePrinted, containsString("01"))
                ,() -> assertThat(toBePrinted, containsString("P61A"))
        );

    }

}