package hr.kaba.olb.client.host;

import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.protocol.NmmResponder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class Protocol {
    public final static byte[] STX = {0x00};
    public final static byte[] ETX = {0x03};
    public final static Charset CHARSET = CharsetUtil.ISO_8859_1;

    public final static NmmResponder OK_NMM_RESPONDER = request -> ResponseCode.NMM_APPROVED;

    public final static Supplier<String> transmissionDate = () -> Formatters.formatTransmissionDate(LocalDateTime.now());

}
