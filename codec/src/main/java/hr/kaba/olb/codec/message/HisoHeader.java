package hr.kaba.olb.codec.message;

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

        int messageLength = message.length();

        byte[] payload = new byte[2];
        payload[0] = (byte) ((messageLength & 0xFF00) >> 8);
        payload[1] = (byte) (messageLength & 0x00FF);

        return String.format("%s%s", Character.toString((char) payload[0]), Character.toString((char) payload[1]));
    }

    /**
     *
     * @param message
     * @return message length encoded in message header, if exists, otherwise message length
     */
    public static int messageLength(String message) {

        String messageLengthEncoded = encodedLength(message);

        String hexLengthRepresentation = messageLengthEncoded.chars()
                                                             .mapToObj(Integer::toHexString)
                                                             .collect(Collectors.joining());

        return hexLengthRepresentation.length() > 0
                ? Integer.parseInt(hexLengthRepresentation, 16)
                : message.length();

    }

}
