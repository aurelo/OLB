package hr.kaba.olb.client.host.handlers;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogger extends io.netty.channel.ChannelHandlerAdapter {

    public static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getLocalizedMessage());
        super.exceptionCaught(ctx, cause);
    }
}
