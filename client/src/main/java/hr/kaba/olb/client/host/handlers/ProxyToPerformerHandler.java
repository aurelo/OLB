package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.host.MessageWriter;
import hr.kaba.olb.client.performers.ResponderRegistry;
import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ProxyToPerformerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ProxyToPerformerHandler.class);

    private final ResponderRegistry responderRegistry;

    public ProxyToPerformerHandler(ResponderRegistry responderRegistry) {
        this.responderRegistry = responderRegistry;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        try {
            logger.debug("to PROXY: {}", msg);

            HISOMessage message = (HISOMessage) msg;

            HISOMessage response = responderRegistry.respond(message, LocalDateTime.now());

            MessageWriter.write(ctx, OLBCodec.encode(response));


        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Exception: {}", e.getMessage());
        }


    }
}
