package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryBitmapFieldTest {

    @Test
    public void testValidFieldsForNTM() {
        List<BitmapField> NTMfields = PrimaryBitmapField.fieldsForProduct(ProductIndicator.NMM);
        List<BitmapField> NTMshouldContain = Arrays.asList(PrimaryBitmapField.P1, PrimaryBitmapField.P7, PrimaryBitmapField.P11, PrimaryBitmapField.P39, PrimaryBitmapField.P53, PrimaryBitmapField.P64);

        assertArrayEquals(NTMfields.toArray(), NTMshouldContain.toArray());
    }

    @Test
    public void testCorrectP1() {
        assertTrue(PrimaryBitmapField.P1.isValidValue("0800822000000000"));
    }

    @Test
    public void testNullP1() {
        assertFalse(PrimaryBitmapField.P1.isValidValue(null));
    }

    @Test
    public void testFieldFromPosition() {
        assertEquals(PrimaryBitmapField.P1, PrimaryBitmapField.fromPosition(1));
        assertEquals(PrimaryBitmapField.P64, PrimaryBitmapField.fromPosition(64));
    }

    @Test
    public void testOutOfRangePosition() {
        assertThrows(IllegalArgumentException.class, () -> PrimaryBitmapField.fromPosition(0));
        assertThrows(IllegalArgumentException.class, () -> PrimaryBitmapField.fromPosition(125));
        assertThrows(IllegalArgumentException.class, () -> PrimaryBitmapField.fromPosition(35808));
        assertThrows(IllegalArgumentException.class, () -> PrimaryBitmapField.fromPosition(-1561));
    }

    @Test
    public void testEncodingFixedValue() {
        String p1 = "0000000010000000";
        assertEquals(p1, PrimaryBitmapField.P1.encoded(p1));


    }

    @Test
    public void testEncodingVariableValue() {
        String p35 = "5590722400899948=19122010000001117000";
        String lengthPrefix = Integer.valueOf(p35.length()).toString();
        assertEquals(lengthPrefix + p35, PrimaryBitmapField.P35.encoded(p35));
    }

    @Test
    public void testEncodingP32() {
        String p32 = "12876";
        String p32Encoded = "0512876";

        assertEquals(p32Encoded, PrimaryBitmapField.P32.encoded(p32));
    }

}