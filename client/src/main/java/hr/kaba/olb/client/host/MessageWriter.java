package hr.kaba.olb.client.host;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageWriter {

    private static final Logger logger = LoggerFactory.getLogger(MessageWriter.class);

    private final static String TCP_IP_OPTIONAL_2_BYTE_HEADER = "0";

    public static void write(ChannelHandlerContext ctx, String message) {

            logger.debug("writing messsage: {}", message);

            final ByteBuf msg = ctx.alloc().buffer();

            msg.writeBytes(Protocol.STX);
            msg.writeCharSequence(TCP_IP_OPTIONAL_2_BYTE_HEADER, Protocol.CHARSET);
            msg.writeCharSequence(message, Protocol.CHARSET);
            msg.writeBytes(Protocol.ETX);


            logger.debug("flushing");

            final ChannelFuture f = ctx.writeAndFlush(msg);

            logger.debug("done writing message: {}", message);

    }
}
