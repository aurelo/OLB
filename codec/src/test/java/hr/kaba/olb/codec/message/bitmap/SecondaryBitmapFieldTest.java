package hr.kaba.olb.codec.message.bitmap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecondaryBitmapFieldTest {

    @Test
    public void testEncodingVariableField() {
//        String p100 = "000000                                                                                                E0177136    0000000000010010081";
//        String p100Encoded = "133000000                                                                                                E0177136    0000000000010010081";
//        assertEquals(p100Encoded, SecondaryBitmapField.S100.encoded(p100));

        String s127 = "000000                                                                                                E0123063    0000000000010010131 ";
        String s127Encoded = "134000000                                                                                                E0123063    0000000000010010131 ";
        assertEquals(s127Encoded, SecondaryBitmapField.S127P.encoded(s127));

    }

}