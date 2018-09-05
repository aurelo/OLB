package hr.kaba.olb.server;

import hr.kaba.olb.codec.message.HISOMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.Highlighter;

public class HisoMessagePrinter extends  ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(Highlighter.HighlightPainter.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HISOMessage message = (HISOMessage) msg;

        logger.debug("received: {} - fields: {}", message, message.getFields());

    }
}
