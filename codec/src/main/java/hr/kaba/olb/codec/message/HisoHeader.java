package hr.kaba.olb.codec.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Encodes and decodes message length
 * End of text message delimiter is taken into account in message length
 *
 * <p>
 *     Message length is 2 byte encoded (4 hex characters)
 *     Example:
 *       message length of 70 characters is 46 hex
 *       46 hex in ascii table is letter F
 * </p>
 *
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class HisoHeader {

    private static final Logger logger = LoggerFactory.getLogger(HisoHeader.class);

    private static final String START_OF_MESSAGE = "ISO";

    private static String encodedLength(String message) {
        return message.substring(0, message.indexOf(START_OF_MESSAGE));
    }

    /**
     *
     * @param message hiso message with ETX delimiter without encoded length at the beginning
     * @return header containing encoded message length
     */
    public static String headerFrom(String message) {
        Objects.nonNull(message);

        return hexToAscii(String.format("%04x", message.length() & 0xFFFF));
    }

    /**
     *
     * @param message full HISO message
     * @return message length encoded in message header, if exists, otherwise message length
     */
    public static int messageLength(String message) {

        String messageLengthEncoded = encodedLength(message);

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
            hex.append(String.format("%02x", (int) ch));
        }

        return hex.toString();
    }

}
