package hr.kaba.olb.client;

import hr.kaba.olb.client.host.ChannelConstructor;
import hr.kaba.olb.client.host.MbuConnector;
import hr.kaba.olb.client.host.Protocol;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.util.PropertiesLoader;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class OlbClient {

    private static final Logger logger = LoggerFactory.getLogger(OlbClient.class);

    private static final String HOST_PROPERTIES_FILE = "host.properties";

    private final static EventLoopGroup hostRequestListenerLoop = new NioEventLoopGroup();
    private final static EventExecutorGroup bizLogicExecutorGroup = new DefaultEventExecutorGroup(5);


    private ChannelConstructor constructor;
    private ChannelFuture connectFuture;
    private final AtomicBoolean shouldRun;


    public OlbClient(TrxResponder trxResponder, AtomicBoolean shouldRun) throws IOException {
        this.shouldRun = shouldRun;

        Properties hostProperties = PropertiesLoader.load(HOST_PROPERTIES_FILE);
        constructor = setUpChannel(hostProperties, trxResponder);

    }

    private ChannelConstructor setUpChannel(Properties hostProperties, TrxResponder trxResponder) {
        ChannelConstructor.Builder channelBuilder = new ChannelConstructor.Builder();
        channelBuilder.withHost(hostProperties.getProperty("host"))
                      .onPort(Integer.valueOf(hostProperties.getProperty("port")))
                      .tryToReconnectEvery(Long.valueOf(hostProperties.getProperty("reconnectInterval"))
                                          ,TimeUnit.valueOf(hostProperties.getProperty("reconnectUnit")))
                      .recevieRequestsOn(hostRequestListenerLoop)
                      .handleRequestsOn(bizLogicExecutorGroup)
                      .nmmRequestHandler(Protocol.OK_NMM_RESPONDER)
                      .trxRequestHandler(trxResponder)
                      .willRunWhen(shouldRun)
        ;

        return channelBuilder.construct();
    }

    public void start() throws InterruptedException {

        logger.info("Starting OLB client");
        connectFuture = constructor.connect();
    }


    public void stop() {

        logger.info("STOPPING OLB APP");

        connectFuture.channel().close();
        constructor.stop();

        try {
            logger.info("syncing for close future");
            connectFuture.channel().closeFuture().sync();
            logger.info("close future done");

        } catch (InterruptedException e) {
            logger.warn(e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally {

            bizLogicExecutorGroup.shutdownGracefully();
            hostRequestListenerLoop.shutdownGracefully();

            logger.info("finally block for freeing common.db.db and netty resources");
        }
    }


}
