package hr.kaba.olb.client.host.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logic for connecting and scheduling reconnect tries
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class Connector {

    private final static Logger logger = LoggerFactory.getLogger(Connector.class);

    private final Bootstrap bootstrap;
    private final ReconnectStrategy reconnectStrategy;

    private final ChannelFutureListener RECONNECT_LISTENER = (ChannelFuture channelFuture) -> {
        if (channelFuture.isSuccess()) {
            logger.info("Connected to host");
        } else {
            logger.info("Trying to reconnect to host");
            this.connectOn(channelFuture.channel().eventLoop());
        }
    };


    /**
     *
     * @param bootstrap netty bootstrap on basis of which pipeline is constructed
     * @param reconnectStrategy at what intervals should reconnect be tried
     */
    public Connector(Bootstrap bootstrap, ReconnectStrategy reconnectStrategy) {
        this.bootstrap = bootstrap;
        this.reconnectStrategy = reconnectStrategy;
    }

    /**
     * tried to connect to OLB server and adds reconnect service to pipeline
     *
     * @return connect channel future listener
     */
    public ChannelFuture connect() {
        ChannelFuture connectFuture = bootstrap.connect();
        connectFuture.addListener(RECONNECT_LISTENER);
        return connectFuture;
    }


    /**
     * schedules connect try on given event loop
     *
     * @param loop event loop on which reconnection will be tried in case of disconnect
     */
    public void connectOn(EventLoop loop) {

        logger.debug("try to connectOn loop: {}", loop.toString());

        if (canScheduleOnEventLoop(loop)) {

            logger.debug("scheduling connect: {}", loop.toString());

            loop.schedule(this::connect, reconnectStrategy.getReconnectionInterval(), reconnectStrategy.getReconnectionTimeUnit());
        }
    }

    private boolean canScheduleOnEventLoop(EventLoop loop) {
        return (!loop.isShutdown() && !loop.isTerminated() && !loop.isShuttingDown());
    }
}
