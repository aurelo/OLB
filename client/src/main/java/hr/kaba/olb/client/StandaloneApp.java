package hr.kaba.olb.client;

import hr.kaba.olb.client.host.MbuConnector;
import hr.kaba.olb.client.host.handlers.DefaultHandlers;
import hr.kaba.olb.client.performers.ResponderRegistry;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.protocol.nmm.ResponseRenderer;
import hr.kaba.olb.protocol.nmm.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hr.kaba.olb.util.PropertiesLoader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public enum StandaloneApp {

    INSTANCE;

    private final static Logger logger = LoggerFactory.getLogger(StandaloneApp.class);

    private static final String DB_PROPERTIES_FILE = "db.properties";
    private static final String HOST_PROPERTIES_FILE = "host.properties";

    private final static EventLoopGroup hostRequestListenerLoop = new NioEventLoopGroup();
    private final static EventExecutorGroup bizLogicExecutorGroup = new DefaultEventExecutorGroup(5);

//    private static DbSource dbSource;
    private MbuConnector hostConnector;
    private ChannelFuture connectFuture;

    public void start() throws Exception{
        logger.info("STARTING OLB APP");

        Properties dbProperties = PropertiesLoader.load(DB_PROPERTIES_FILE);

//        dbSource = new DbSource(dbProperties);
//        dbSource.start();

        Properties hostProperties = PropertiesLoader.load(HOST_PROPERTIES_FILE);

//        hostConnector = getHostConnector(hostProperties, new DefaultHandlers(dbSource.getDataSource(), performersRegistry()).inputToStringDecoders);

        connectFuture = hostConnector.connect();

        TimeUnit.SECONDS.sleep(25);

    }

    public void stop() {
        logger.info("STOPPING OLB APP");

        connectFuture.channel().close();
        hostConnector.stop();

        try {
            logger.info("syncing for close future");
            connectFuture.channel().closeFuture().sync();
            logger.info("close future done");

        } catch (InterruptedException e) {
            logger.warn(e.getLocalizedMessage());
            e.printStackTrace();
        }
        finally {
//            dbSource.stop();

            bizLogicExecutorGroup.shutdownGracefully();
            hostRequestListenerLoop.shutdownGracefully();

            logger.info("finally block for freeing common.db.db and netty resources");
        }

    }

    private MbuConnector getHostConnector(Properties hostProperties, Supplier<LinkedList<ChannelHandlerAdapter>> handlers) {
        final String host = hostProperties.getProperty("host");
        final int port = Integer.valueOf(hostProperties.getProperty("port"));

        final long reconnectInterval = Long.valueOf(hostProperties.getProperty("reconnectInterval"));
        final TimeUnit reconnectUnit = TimeUnit.valueOf(hostProperties.getProperty("reconnectUnit"));

        return new MbuConnector(host, port, hostRequestListenerLoop, handlers, reconnectInterval, reconnectUnit);
    }

    private ResponderRegistry performersRegistry() {
        Map<ProductIndicator, ResponseRenderer> responders = new HashMap<>();
        responders.put(ProductIndicator.NMM, new Response(InitiatorType.HOST));

        return new ResponderRegistry(responders);
    }

}
