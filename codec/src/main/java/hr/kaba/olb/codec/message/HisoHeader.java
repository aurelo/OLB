package hr.kaba.olb.codec.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Example of coding:
 * message length = 318
 * to hex: 13E
 * code: 01 3E
 * decimal numbers: 1 62
 * from ascii table: SOH - start of heading (1)  >(62)
 *
 * message of 318 characters coded as 'SOH>' = '0x01>'
 */
public class HisoHeader {

    private static final Logger logger = LoggerFactory.getLogger(HisoHeader.class);

    private static final String START_OF_MESSAGE = "ISO";

    private static String encodedLength(String message) {
        return message.substring(0, message.indexOf(START_OF_MESSAGE));
    }

    /**
     *
     * @param message
     * @return header containing encoded message length
     */
    public static String headerFrom(String message) {
        Objects.nonNull(message);

//        return Character.toString((char) message.length());
        return hexToAscii(String.format("%04x", message.length() & 0xFFFF));
    }
    /*
    OLD IMPLEMENTATION
    public static String headerFrom(String message) {
        Objects.nonNull(message);

        int messageLength = message.length();

        byte[] payload = new byte[2];
        payload[0] = (byte) ((messageLength & 0xFF00) >> 8);
        payload[1] = (byte) (messageLength & 0x00FF);

        return String.format("%s%s", Character.toString((char) payload[0]), Character.toString((char) payload[1]));
    }
    */

    /**
     *
     * @param message
     * @return message length encoded in message header, if exists, otherwise message length
     */
    public static int messageLength(String message) {

        String messageLengthEncoded = encodedLength(message);

//        String hexLengthRepresentation = messageLengthEncoded.chars()
//                                                             .mapToObj(Integer::toHexString)
//                                                             .collect(Collectors.joining());

        String hexLengthRepresentation = asciiToHex(messageLengthEncoded);

        logger.debug("Decoding length from: {} - hex representation: {}", messageLengthEncoded, hexLengthRepresentation);

        return hexLengthRepresentation.length() > 0
                ? Integer.parseInt(hexLengthRepresentation, 16)
                : message.length();
    }


    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    private static String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
//            hex.append(Integer.toHexString((int) ch));
            hex.append(String.format("%02x", (int) ch));
        }

        return hex.toString();
    }

}
