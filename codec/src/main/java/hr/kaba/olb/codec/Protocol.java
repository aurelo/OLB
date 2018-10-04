package hr.kaba.olb.codec;

import java.nio.charset.Charset;

public class Protocol {
    public static final Charset HISO_CHARSET = Charset.forName("ISO-8859-1");
    public static final byte[] ETX = {0x03};
    public static final String MESSAGE_TERMINATOR = new String(ETX, HISO_CHARSET);
}
