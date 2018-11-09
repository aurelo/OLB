package hr.kaba.olb.client.host;

import hr.kaba.olb.codec.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class MessageWriter {

    private static final Logger logger = LoggerFactory.getLogger(MessageWriter.class);

    /**
     * Writes strings to netty channel
     *
     * @param ctx channel context on which message should be written to
     * @param message message to be written
     */
    public static void write(ChannelHandlerContext ctx, String message) {

            logger.debug("writing message: [{}]", message);

            final ByteBuf msg = ctx.alloc().buffer();

            msg.writeCharSequence(message, Protocol.HISO_CHARSET);


            logger.debug("flushing");

            final ChannelFuture f = ctx.writeAndFlush(msg);

            logger.debug("wrote hex message: [{}]", ByteBufUtil.hexDump(msg));

    }
}
