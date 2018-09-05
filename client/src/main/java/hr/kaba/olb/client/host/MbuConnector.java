package hr.kaba.olb.client.host;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class MbuConnector {

    private final static Logger logger = LoggerFactory.getLogger(MbuConnector.class);

    private static AtomicBoolean shouldRun = new AtomicBoolean(false);

    private final Reconnector reconnector;
    private final Bootstrap bootstrap;

    public MbuConnector(String host, int port, EventLoopGroup loop, Supplier<LinkedList<ChannelHandlerAdapter>> inputHandlerSupplier, long reconnectionInterval, TimeUnit reconnectionTimeUnit) {
        this.reconnector = new Reconnector(reconnectionInterval, reconnectionTimeUnit);


        bootstrap = new Bootstrap();

        bootstrap.group(loop);
        bootstrap.remoteAddress(new InetSocketAddress(host, port));
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel socketChannel) {

                for (ChannelHandlerAdapter adapter : inputHandlerSupplier.get()) {
                    socketChannel.pipeline().addLast(adapter.toString(), adapter);
                }

                socketChannel.pipeline().addFirst(new ReconnectHandler(reconnector));
            }
        });

    }


    public ChannelFuture connect() {
        shouldRun.set(true);
        return reconnector.connect();
    }

    public void stop() {
        shouldRun.set(false);
    }


    /**
     *
     */
    private final class Reconnector {
        private final long reconnectionInterval;
        private final TimeUnit reconnectionTimeUnit;

        private final ChannelFutureListener reconnectListener;

        public Reconnector(long reconnectionInterval, TimeUnit reconnTimeUnit) {
            this.reconnectionInterval = reconnectionInterval;
            this.reconnectionTimeUnit = reconnTimeUnit;

            this.reconnectListener = channelFuture -> {
                if (channelFuture.isSuccess()) {
                    logger.info("Connected to host");
                } else {
                    logger.info("Trying to reconnect to host");
                    connectOn(channelFuture.channel().eventLoop());
                }
            };

        }


        public void connectOn(EventLoop loop) {
            if (canScheduleOnEventLoop(loop)) {
                loop.schedule(this::connect, reconnectionInterval, reconnectionTimeUnit);
            }
        }

        public ChannelFuture connect() {
            ChannelFuture connectFuture = bootstrap.connect();
            connectFuture.addListener(reconnectListener);
            return connectFuture;
        }

        private boolean canScheduleOnEventLoop(EventLoop loop) {
//            logger.info("Event loop: is shutdown: {} is terminated: {} is shutting down: {}", loop.isShutdown(), loop.isTerminated(), loop.isShuttingDown());
            return (!loop.isShutdown() && !loop.isTerminated() && !loop.isShuttingDown());
        }
    }

    /**
     *
     */
    private class ReconnectHandler extends ChannelInboundHandlerAdapter {

        private final Reconnector reconnector;

        public ReconnectHandler(Reconnector reconnector) {
            this.reconnector = reconnector;
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);

            if (!shouldRun.get()) {
                logger.info("removing reconnect handler");
                ctx.pipeline().remove(this);
                return;
            }

            logger.debug("Reconnecting");
            reconnector.connectOn(ctx.channel().eventLoop());
        }

    }

}
