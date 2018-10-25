package hr.kaba.olb.codec;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.*;


class OLBCodecParsingMalformedTest {


    @Test
    void shouldParseMessageWithMissingFields() {
        String malformedPosString = "ISO0260100559430B22000012A808008000000401000000400003000000000010010241113510010021119100265006215590722400899948=191200000000404300V0265006        1910192400TES103100000000010000000000404310231443420010230000000000";

        HISOMessage malformedPosMessage = OLBCodec.decode(malformedPosString);


        // should have S100 and S126P fields, but the message is malformed
        assertAll(
                () -> assertThat(malformedPosMessage.getFields(), hasKey(SecondaryBitmapField.S90)),
                () -> assertThat(malformedPosMessage.getFields(), not(hasKey(SecondaryBitmapField.S100))),
                () -> assertThat(malformedPosMessage.getFields(), not(hasKey(SecondaryBitmapField.S126P)))
        );

    }

    @Test
    void shouldParseETXinHeader() {
        String etxInHeaderString = "\u0003?";

        assertThrows(MalformedHisoMessageException.class, () -> OLBCodec.decode(etxInHeaderString));
    }

}