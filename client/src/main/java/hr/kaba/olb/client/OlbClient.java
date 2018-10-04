package hr.kaba.olb.client;

import hr.kaba.olb.client.host.ChannelConstructor;
import hr.kaba.olb.protocol.NmmResponder;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.util.PropertiesLoader;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class OlbClient {

    private static final Logger logger = LoggerFactory.getLogger(OlbClient.class);

    private static final String HOST_PROPERTIES_FILE = "host.properties";

    private final static EventLoopGroup hostRequestListenerLoop = new OioEventLoopGroup();
    private final static EventExecutorGroup bizLogicExecutorGroup = new DefaultEventExecutorGroup(5);


    private ChannelConstructor constructor;
    private ChannelFuture connectFuture;
    private final AtomicBoolean shouldRun;


    public OlbClient(TrxResponder trxResponder, AtomicBoolean shouldRun) throws IOException {
        this.shouldRun = shouldRun;

        Properties hostProperties = PropertiesLoader.load(HOST_PROPERTIES_FILE);
        constructor = setUpChannel(hostProperties, trxResponder);

    }

    /**
     *
     */
    public void start() {

        logger.info("Starting OLB client");
        connectFuture = constructor.connect();
    }

    /**
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {

        logger.info("STOPPING OLB APP");

        connectFuture.channel().close();

        try {
            logger.info("syncing for close future");
            connectFuture.channel().closeFuture().sync();
            logger.info("close future done");

        } catch (InterruptedException e) {
            logger.warn(e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally {


            logger.debug("shutting down connect loop");
            hostRequestListenerLoop.shutdownGracefully().await();

            logger.debug("shutting down biz logic group");
            bizLogicExecutorGroup.shutdownGracefully().await();

        }
    }


    /**
     *
     * @param hostProperties properties for clien connection - ip, port...
     * @param trxResponder business implementation responder for transaction message requests
     * @return
     */
    private ChannelConstructor setUpChannel(Properties hostProperties, TrxResponder trxResponder) {
        ChannelConstructor.Builder channelBuilder = new ChannelConstructor.Builder();
        channelBuilder.withHost(hostProperties.getProperty("host"))
                      .onPort(Integer.valueOf(hostProperties.getProperty("port")))
                      .tryToReconnectEvery(Long.valueOf(hostProperties.getProperty("reconnectInterval"))
                              ,TimeUnit.valueOf(hostProperties.getProperty("reconnectUnit")))
                      .recevieRequestsOn(hostRequestListenerLoop)
                      .handleRequestsOn(bizLogicExecutorGroup)
                      .nmmRequestHandler(NmmResponder.OK_NMM_RESPONDER)
                      .trxRequestHandler(trxResponder)
                      .willRunWhen(shouldRun)
        ;

        return channelBuilder.construct();
    }


}
