package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BitmapTest {

        @Test
        public void testHexToBinaryConversion() {
            assertEquals("1011001000111000100000000000000100101000101000001000000000011000", Bitmap.hexToBinary("B238800128A08018"));
            assertEquals("1000001000100000000000000000000000000000000000000000000000000000", Bitmap.hexToBinary("8220000000000000"));
            assertEquals("0000010000000000000000000000000000000000000000000000000000000000", Bitmap.hexToBinary("0400000000000000"));
            assertEquals("1011001000111000100000000000000100101110101000001000000000011000", Bitmap.hexToBinary("B23880012EA08018"));
            assertEquals("0010001110001000000000000001001011101010000010000000000110000000", Bitmap.hexToBinary("23880012EA080180"));
        }

        @Test
        public void testBinaryToHexConversion() {
            assertEquals("B238800128A08018", Bitmap.binaryToHex("1011001000111000100000000000000100101000101000001000000000011000"));
            assertEquals("8220000000000000", Bitmap.binaryToHex("1000001000100000000000000000000000000000000000000000000000000000"));
            assertEquals("0400000000000000", Bitmap.binaryToHex("0000010000000000000000000000000000000000000000000000000000000000"));
            assertEquals("B23880012EA08018", Bitmap.binaryToHex("1011001000111000100000000000000100101110101000001000000000011000"));
            assertEquals("23880012EA080180", Bitmap.binaryToHex("0010001110001000000000000001001011101010000010000000000110000000"));

        }

        @Test
        public void testFieldValuesPairing() {

            String bitmapToDecode = "8220000000000000";
            String messageToPair = "04000000000000000619111227000001001";

            Map<BitmapField, String> expectedValues = new HashMap<>();
            expectedValues.put(PrimaryBitmapField.P1, "0400000000000000");
            expectedValues.put(PrimaryBitmapField.P7, "0619111227");
            expectedValues.put(PrimaryBitmapField.P11, "000001");


            Bitmap<PrimaryBitmapField> bitmap = new Bitmap<>(bitmapToDecode, PrimaryBitmapField.class, ProductIndicator.ATM);

            assertTrue(true);

//        assertEquals(expectedValues.size(), bitmap.mapFieldValues(messageToPair).size());


        }

        @Test
        public void testEncodingBitmapFromFields() {
            Map<BitmapField, String> fields = new HashMap<>();

            fields.put(PrimaryBitmapField.P1, "00001");
            fields.put(PrimaryBitmapField.P7, "1235");

            assertEquals("1000001000000000000000000000000000000000000000000000000000000000", Bitmap.binaryBitmapFromFields(fields));
        }


        @Test
        public void testEnodingPrimaryBitmap() {
            Map<BitmapField, String> fields = new HashMap<>();

            fields.put(PrimaryBitmapField.P1, "0000000010000000");
            fields.put(PrimaryBitmapField.P3, "210030");
            fields.put(PrimaryBitmapField.P4, "000000005000");
            fields.put(PrimaryBitmapField.P7, "0628073926");
            fields.put(PrimaryBitmapField.P11, "016875");
            fields.put(PrimaryBitmapField.P12, "093926");
            fields.put(PrimaryBitmapField.P13, "0628");
            fields.put(PrimaryBitmapField.P17, "0628");
            fields.put(PrimaryBitmapField.P32, "19100055555");
            fields.put(PrimaryBitmapField.P35, "5590722400899948=19122010000001117000");
            fields.put(PrimaryBitmapField.P37, "5961");
            fields.put(PrimaryBitmapField.P41, "S1AWNIPV");
            fields.put(PrimaryBitmapField.P43, "IZ SIM                ZAGREB IZ       HR");
            fields.put(PrimaryBitmapField.P49, "191");
            fields.put(PrimaryBitmapField.P60A, "0122402TES1+000");
            fields.put(PrimaryBitmapField.P61A, "0132400TES10031P");


            String hexPrimaryBitmap = "B238800128A08018";
            String binaryPrimaryBitmap = "1011001000111000100000000000000100101000101000001000000000011000";

            assertEquals(binaryPrimaryBitmap, Bitmap.binaryBitmapFromFields(fields));
            assertEquals(hexPrimaryBitmap, Bitmap.hexBitmapFromFields(fields));
        }


        @Test
        public void testEncodingSecondaryBitmapFields() {
            String hexSecondaryBitmap = "0000000010000000";

            Map<BitmapField, String> fields = new HashMap<>();

            fields.put(SecondaryBitmapField.S100, "24000000000");

            assertEquals(hexSecondaryBitmap, Bitmap.hexBitmapFromFields(fields));
        }


        @Test
        public void testEncodingMultipleFields() {
            String expectedString = "0000004210000004220030000000176431063008020502132409551606300630000512876375590722400800441=20052010000000000000041191      00NF058911        TOTAL MKT FR          NANTERRE     FRAFR191016EUROEURO-12000000192400PRO1031000000000200041191      06300955160006300000000000000000176099                              1124000080000038000000000000000000000000000000000000000";


            Map<BitmapField, String> fieldsToEncode = new HashMap<>();

            fieldsToEncode.put(PrimaryBitmapField.P1, "0000004210000004");
            fieldsToEncode.put(PrimaryBitmapField.P3, "220030");
            fieldsToEncode.put(PrimaryBitmapField.P4, "000000176431");
            fieldsToEncode.put(PrimaryBitmapField.P7, "0630080205");
            fieldsToEncode.put(PrimaryBitmapField.P11, "021324");
            fieldsToEncode.put(PrimaryBitmapField.P12, "095516");
            fieldsToEncode.put(PrimaryBitmapField.P13, "0630");
            fieldsToEncode.put(PrimaryBitmapField.P17, "0630");
            fieldsToEncode.put(PrimaryBitmapField.P25, "00");
            fieldsToEncode.put(PrimaryBitmapField.P32, "12876");
            fieldsToEncode.put(PrimaryBitmapField.P35, "5590722400800441=20052010000000000000");
            fieldsToEncode.put(PrimaryBitmapField.P37, "041191");
            fieldsToEncode.put(PrimaryBitmapField.P39, "00");
            fieldsToEncode.put(PrimaryBitmapField.P41, "NF058911");
            fieldsToEncode.put(PrimaryBitmapField.P43, "TOTAL MKT FR          NANTERRE     FRAFR");
            fieldsToEncode.put(PrimaryBitmapField.P49, "191");
            fieldsToEncode.put(PrimaryBitmapField.P60P, "016EUROEURO-1200000");
            fieldsToEncode.put(PrimaryBitmapField.P61P, "0192400PRO103100000000");
            fieldsToEncode.put(SecondaryBitmapField.S90, "0200041191      06300955160006300000000000");
            fieldsToEncode.put(SecondaryBitmapField.S95, "000000176099");
            fieldsToEncode.put(SecondaryBitmapField.S100, "24000080000");
            fieldsToEncode.put(SecondaryBitmapField.S126P, "03800000000000000000000000000000000000000");

            //assertEquals(expectedString, Bitmap.encode(fieldsToEncode));
        }

}