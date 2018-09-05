package hr.kaba.olb.client.host.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public Connector(Bootstrap bootstrap, ReconnectStrategy reconnectStrategy) {
        this.bootstrap = bootstrap;
        this.reconnectStrategy = reconnectStrategy;
    }

    public ChannelFuture connect() {
        ChannelFuture connectFuture = bootstrap.connect();
        connectFuture.addListener(RECONNECT_LISTENER);
        return connectFuture;
    }


    public void connectOn(EventLoop loop) {
        if (canScheduleOnEventLoop(loop)) {
            loop.schedule(this::connect, reconnectStrategy.getReconnectionInverval(), reconnectStrategy.getReconnectionTimeUnit());
        }
    }

    private boolean canScheduleOnEventLoop(EventLoop loop) {
        return (!loop.isShutdown() && !loop.isTerminated() && !loop.isShuttingDown());
    }
}
