package hr.kaba.olb.codec.message;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HisoHeaderTest {

    @Test
    void correctlyEncodeLoginRequestHeader() {

        String loginRequestMessage = "ISO00600001008008220000000000000040000000000000009101433030000010011";

        // length = 68
        String expectedPayload = String.format("%s%s", (char) 0, (char) 68);

        assertThat(HisoHeader.headerFrom(loginRequestMessage), is(expectedPayload));
    }

    @Test
    void correctlyEncodeAtmDepositAdvice() {
        String almDepositAdviceMessage = "ISO0160000130221B23880012EA0801800000040100000102100300000000100000910124705011175100624062806281119100055555375590722400899963=191220100000000000005964        75542200S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P02005964        0628100624250628000000000011240000000000014 ";

        // length = 318 - 013E (hex) -> 01 62
        String expectedPayload = String.format("%s%s", (char) 1, (char) 62);

        assertThat(HisoHeader.headerFrom(almDepositAdviceMessage), is(expectedPayload));
    }

    @Test
    void correctlyEncodeAtmIsplataReq() {
        String atmIslataReq = "ISO0160000100200B238820128A0801800000000100000000120000000000400000607091818011583111818060706070021119101247005376020202001939466=190422100000216080003347        A1247005        KABA D.RESA-STARA POSLDUGA RESA    HR HR1910122400PRO1+0000132400PRO10100P1124000080000";

        // length = 266 - 10A (hex) -> 1 10
        String expectedPayload = String.format("%s%s", (char) 1, (char) 10);

        assertThat(HisoHeader.headerFrom(atmIslataReq), is(expectedPayload));

    }

    @Test
    void dedcodeLoginRequestMessageLength() {
        String loginRequestMessage = "DISO00600001008008220000000000000040000000000000009101529100000010011";

        assertThat(HisoHeader.messageLength(loginRequestMessage), is(68));
    }


    @Test
    void dedcodeAtmDepositAdviceMessageLength() {
        String atmDepositAdviceMessage = "\u0001>ISO0160000130221B23880012EA0801800000040100000102100300000000100000910124705011175100624062806281119100055555375590722400899963=191220100000000000005964        75542200S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P02005964        0628100624250628000000000011240000000000014";

        assertThat(HisoHeader.messageLength(atmDepositAdviceMessage), is(318));
    }

    @Test
    void decodeNonExistantMessageLength() {
        String nonExistantLength = "ISO00600001008008220000000000000040000000000000009101529100000010011";

        assertThat(HisoHeader.messageLength(nonExistantLength), is(68));
    }

}