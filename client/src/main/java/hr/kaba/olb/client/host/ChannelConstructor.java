package hr.kaba.olb.client.host;

import hr.kaba.olb.client.host.connection.Connector;
import hr.kaba.olb.client.host.connection.ReconnectStrategy;
import hr.kaba.olb.client.host.handlers.*;
import hr.kaba.olb.codec.Protocol;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.protocol.NmmResponder;
import hr.kaba.olb.protocol.TrxResponder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service capable of constructing netty channel and pipeline
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class ChannelConstructor {

    private final static Logger logger = LoggerFactory.getLogger(ChannelConstructor.class);

    private final Connector connector;

    /**
     *
     * @param host ip address of OLB server host
     * @param port server port client will connect on
     * @param reconnectStrategy how frequently will client try to reconnect in case of disconnect
     * @param channelLoop pool accepting connection
     * @param workerLoop worker pool on which transaction responding service will run
     * @param nmmResponder responder for nmm (Network Management Messages) messages
     *                     nmm messages administer connection status between client and server (host)
     * @param trxResponder service for responding to transaction requests (ATM/POS request)
     * @param shouldRun flag signifying should service run
     */
    private ChannelConstructor(String host, int port, ReconnectStrategy reconnectStrategy, EventLoopGroup channelLoop, EventExecutorGroup workerLoop, NmmResponder nmmResponder, TrxResponder trxResponder, AtomicBoolean shouldRun) {

        logger.info("Constructing channel with: ip: '{}' on port: '{}'", host, port);

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(channelLoop);
        bootstrap.remoteAddress(new InetSocketAddress(host, port));
        bootstrap.channel(OioSocketChannel.class);

        connector = new Connector(bootstrap, reconnectStrategy);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel socketChannel) {

                socketChannel.pipeline()
                             .addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer(Protocol.ETX)))
//                             .addLast(new MinLengthDelimiterDecoder(5, Protocol.ETX_DELIMITER, true))
                             .addLast(new StringDecoder(Protocol.HISO_CHARSET))
                             .addLast(new HisoMessageDecoder())
                             .addLast(workerLoop, new HisoProtocolHandler(InitiatorType.HOST, nmmResponder, trxResponder))
                             .addLast(new DisconnectHandler(connector, shouldRun))
                             .addLast(new ErrorLogger())
                             ;

            }
        });

    }

    /**
     * tries to connect to OLB server
     *
     * @return connect channel future signifying result of connect attempt
     */
    public ChannelFuture connect() {
        return connector.connect();
    }


    /**
     * Builder class for easier creation of channel constructor
     * Used because of large number of constructor parameters
     */
    public static class Builder{
        private String host;
        private int port;
        private ReconnectStrategy reconnectStrategy;
        private EventLoopGroup requestReceiverLoop;
        private EventExecutorGroup requestHandlerLoop;
        private NmmResponder nmmResponder;
        private TrxResponder trxResponder;
        private AtomicBoolean shouldRun;


        public Builder() {
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public Builder onPort(int port) {
            this.port = port;
            return this;
        }

        public Builder tryToReconnectEvery(long reconnectionInterval, TimeUnit reconnectionTimeUnit) {
            this.reconnectStrategy = new ReconnectStrategy(reconnectionInterval, reconnectionTimeUnit);
            return this;
        }

        public Builder recevieRequestsOn(EventLoopGroup loop) {
            this.requestReceiverLoop = loop;
            return this;
        }

        public Builder handleRequestsOn(EventExecutorGroup loop) {
            this.requestHandlerLoop = loop;
            return this;
        }

        public Builder nmmRequestHandler(NmmResponder nmmResponder) {
            this.nmmResponder = nmmResponder;
            return this;
        }

        public Builder trxRequestHandler(TrxResponder trxResponder) {
            this.trxResponder = trxResponder;
            return this;
        }

        public Builder willRunWhen(AtomicBoolean shouldRun) {
            this.shouldRun = shouldRun;
            return this;
        }

        public ChannelConstructor construct() {
            return new ChannelConstructor(host, port, reconnectStrategy, requestReceiverLoop, requestHandlerLoop, nmmResponder, trxResponder, shouldRun);
        }

    }


}
