package hr.kaba.olb.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)


    private final static Logger logger = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)

        logger.debug("discarding message: {}", msg.toString());

//        // Discard the received data silently.
//        m.release(); // (3)

        final ByteBuf outMessage = ctx.alloc().buffer(256);
        outMessage.writeCharSequence("004449534f3030363030303031303038303038323230303030303030303030303030303430303030303030303030303030303038323931313038313930303030303133303103", Charset.defaultCharset());

        ChannelFuture future = ctx.writeAndFlush(outMessage);

//        future.addListener(ChannelFutureListener.CLOSE);

        //ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("CHANNEL INACTIVE");

        ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
        ctx.channel().parent().close();

        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
