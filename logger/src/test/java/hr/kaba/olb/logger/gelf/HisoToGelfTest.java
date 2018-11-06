package hr.kaba.olb.logger.gelf;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class HisoToGelfTest {

    @Test
    void correctlyMapsAtmDepositRequest() {
        String atmDepositString = "ISO0160000130221B23880012EA0801800000040100000102100300000000100000911131707011396100624062806281119100055555375590722400899963=191220100000000000005964        75542200S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P02005964        0628100624250628000000000011240000000000014";
        HISOMessage atmDeposit = OLBCodec.decode(atmDepositString);

        Map<String, String> actualData = HisoToGelf.adapt(atmDeposit);

        Map<String, String> expected = new HashMap<>();
        expected.put("protocol", "ATM");
        expected.put("type", "0221");
        expected.put("code", "21");
        expected.put("amount", "000000010000");
        expected.put("transmission_date", "0911131707");
        expected.put("card", "5590722400899963");
        expected.put("rrn", "5964");
        expected.put("response", "00");



        assertThat(actualData, is(expected));

    }


    @Test
    void correctlyMapsPosProdajaRequest() {
        String posProdajaString = "9ISO0260000700200B238828128A08018000000001000000400200000000003300610040732270002710932271004100400400042760376020202000821392=21092210000041863000205518      12887917        KORDUN, 2004469005    SLUNJ        HRVHR191016EUROEURO-12000000192400PRO1001000000001124000080000038000000000000000000000000000000000000000";
        HISOMessage posProdaja = OLBCodec.decode(posProdajaString);

        Map<String, String> actualData = HisoToGelf.adapt(posProdaja);

        Map<String, String> expected = new HashMap<>();
        expected.put("protocol", "POS");
        expected.put("type", "0200");
        expected.put("code", "00");
        expected.put("amount", "000000033006");
        expected.put("transmission_date", "1004073227");
        expected.put("card", "6020202000821392");
        expected.put("rrn", "205518");
        expected.put("response", "XX");

        assertThat(actualData, is(expected));

    }

    @Test
    void correctlyMapsAtmNotSufficientFundsResponse() {
        String atmResponseString = "ISO0160000150210B23880012A90801800000000100000000120000000002000001004074312000396094312100410041119101147001376020206000779351=210822100000118510003518        51A1147001        2530000001995460000001995461910122400PRO1+0000132400PRO10100P11240000800000";
        HISOMessage atmResponse = OLBCodec.decode(atmResponseString);

        Map<String, String> actualData = HisoToGelf.adapt(atmResponse);

        Map<String, String> expected = new HashMap<>();
        expected.put("protocol", "ATM");
        expected.put("type", "0210");
        expected.put("code", "01");
        expected.put("amount", "000000200000");
        expected.put("transmission_date", "1004074312");
        expected.put("card", "6020206000779351");
        expected.put("rrn", "3518");
        expected.put("response", "51");

        assertThat(actualData, is(expected));

    }

    @Test
    void canMapNmmMessage() {
        String nmmRequestString = "DISO0060000400800822000000000000004000000000000001102115000006852301";
        HISOMessage nmmRequest = OLBCodec.decode(nmmRequestString);

        Map<String, String> actualData = HisoToGelf.adapt(nmmRequest);

        Map<String, String> expected = new HashMap<>();
        expected.put("protocol", "NMM");

        assertThat(actualData, is(expected));

    }


}