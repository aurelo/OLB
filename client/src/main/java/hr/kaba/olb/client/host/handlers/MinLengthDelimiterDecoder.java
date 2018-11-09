package hr.kaba.olb.client.host.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * Constructs logical messages from incoming network bytes
 * Uses delimiter to signal message end, but ignores it when it appears in first XX characters
 * (as defined by minimum length)
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class MinLengthDelimiterDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(MinLengthDelimiterDecoder.class);

    private final int minMessageLength;
    private final byte delimiter;
    private final boolean keepDelimiter;

    /**
     *
     * @param minMessageLength minimum length message should have
     * @param delimiter delimiter signifying end of message - taken into consideration after minimum message length
     * @param keepDelimiter is delimiter kept as part of incoming message, or is stripped
     */
    public MinLengthDelimiterDecoder(int minMessageLength, byte delimiter, boolean keepDelimiter) {

        Objects.nonNull(minMessageLength);
        Objects.nonNull(delimiter);
        Objects.nonNull(keepDelimiter);

        if (minMessageLength < 0) {
            throw new IllegalArgumentException("Minimum message length cannot be lower than zero!");
        }

        this.minMessageLength = minMessageLength;
        this.delimiter = delimiter;
        this.keepDelimiter = keepDelimiter;
    }


    /**
     *
     * @param channelHandlerContext netty's handler context
     * @param in incoming byte parts
     * @param out full logical message joined from incoming parts
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < minMessageLength) {
            return;
        }

        int indexOf = in.indexOf(minMessageLength, in.readableBytes(), delimiter) + (keepDelimiter ? 1 : 0);

        if (indexOf != -1) {
            logger.debug("received hex message: [{}]", ByteBufUtil.hexDump(in, 0, indexOf));
            out.add(in.readBytes(indexOf));
        }
    }
}
