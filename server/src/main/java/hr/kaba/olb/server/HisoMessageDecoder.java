package hr.kaba.olb.server;

import hr.kaba.olb.codec.OLBCodec;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HisoMessageDecoder extends MessageToMessageDecoder<String> {

    private final static Logger logger = LoggerFactory.getLogger(HisoMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String msg, List<Object> out) throws Exception {

        logger.debug("received: {}", msg);

        out.add(OLBCodec.decode(msg));
    }
}
