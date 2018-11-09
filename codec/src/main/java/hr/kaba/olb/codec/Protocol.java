package hr.kaba.olb.codec;

import java.nio.charset.Charset;

/**
 * Utility class containing end of message delimiter and encoding charset
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class Protocol {
    public static final Charset HISO_CHARSET = Charset.forName("ISO-8859-1");
    public static final byte[] ETX = {0x03};
    public static final byte ETX_DELIMITER = 0x03;
    public static final String MESSAGE_TERMINATOR = new String(ETX, HISO_CHARSET);
}
