package hr.kaba.olb.client.host.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ErrorLogger extends ChannelHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogger.class);
    private static final Marker ERROR_MARKER = MarkerFactory.getMarker("EXCEPTION");

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(ERROR_MARKER, cause.getMessage());
        cause.printStackTrace();
//        super.exceptionCaught(ctx, cause);
    }
}
