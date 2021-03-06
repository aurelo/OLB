package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.host.connection.Connector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Handler that will try to reconnect in case of disconnect
 * Tries to reconnect only in case client service is designated to run
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class DisconnectHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(DisconnectHandler.class);

    private final Connector connector;
    private final AtomicBoolean shouldRun;

    public DisconnectHandler(Connector connector, AtomicBoolean shouldRun) {
        this.connector = connector;
        this.shouldRun = shouldRun;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        logger.debug("should run: {} channel: isActive: {} isOpen: {} isRegistered: {} ctx.channel: {} ctx.channel.eventLoop: {}", shouldRun.get(), ctx.channel().isActive(), ctx.channel().isOpen(), ctx.channel().isRegistered(), ctx.channel(), ctx.channel().eventLoop());

        if (shouldRun.get()) {

            logger.debug("Reconnecting");
            connector.connectOn(ctx.channel().eventLoop());

        }


    }
}
