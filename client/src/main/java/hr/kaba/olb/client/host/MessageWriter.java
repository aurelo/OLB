package hr.kaba.olb.client.host;

import hr.kaba.olb.codec.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageWriter {

    private static final Logger logger = LoggerFactory.getLogger(MessageWriter.class);

    public static void write(ChannelHandlerContext ctx, String message) {

            logger.debug("writing messsage: [{}]", message);

            final ByteBuf msg = ctx.alloc().buffer();

            msg.writeCharSequence(message, Protocol.HISO_CHARSET);


            logger.debug("flushing");

            final ChannelFuture f = ctx.writeAndFlush(msg);

            logger.debug("wrote hex message: [{}]", ByteBufUtil.hexDump(msg));

    }
}
