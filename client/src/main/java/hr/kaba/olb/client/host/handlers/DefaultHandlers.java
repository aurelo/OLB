package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.performers.ResponderRegistry;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.function.Supplier;

public class DefaultHandlers {

    private final static byte[] ETX = {0x03};
    private final static StringDecoder stringDecoder = new StringDecoder(CharsetUtil.ISO_8859_1);

    //** private final HisoMessageHandler hisoMessageHandler;
    private final ProxyToPerformerHandler proxyToPerformerHandler;


    public DefaultHandlers(DataSource dataSource, ResponderRegistry productIndicatorResponderMap) {
      //**  this.hisoMessageHandler = new HisoMessageHandler(dataSource);
        this.proxyToPerformerHandler = new ProxyToPerformerHandler(productIndicatorResponderMap);
    }

    public Supplier<LinkedList<ChannelHandlerAdapter>> inputToStringDecoders = this::getInputAdapters;



    private LinkedList<ChannelHandlerAdapter> getInputAdapters() {
        LinkedList<ChannelHandlerAdapter> adapters = new LinkedList<>();

        adapters.add(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer(ETX)));
        adapters.add(stringDecoder);
        adapters.add(new HisoMessageDecoder());

        adapters.add(proxyToPerformerHandler);

//***        adapters.add(new NmmHandler());

        adapters.add(new ErrorLogger());

        return adapters;

    }
}