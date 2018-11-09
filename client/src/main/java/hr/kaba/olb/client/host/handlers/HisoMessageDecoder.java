package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.codec.OLBCodec;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handler that decodes String message to instances of domain messages
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class HisoMessageDecoder extends MessageToMessageDecoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(HisoMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String message, List<Object> out) {

        logger.debug("decoding: '{}'", message);

        out.add(OLBCodec.decode(message));
    }
}
