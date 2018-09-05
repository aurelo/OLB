package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.host.connection.Connector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class DisconnectHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(DisconnectHandler.class);

    private final Connector connector;
    private final AtomicBoolean shouldRun;

    public DisconnectHandler(Connector connector, AtomicBoolean shouldRun) {
        this.connector = connector;
        this.shouldRun = shouldRun;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        logger.debug("should run: {}", shouldRun.get());

        if (shouldRun.get()) {

            super.channelInactive(ctx);
            logger.debug("Reconnecting");
            connector.connectOn(ctx.channel().eventLoop());

        }

    }
}
