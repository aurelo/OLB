package hr.kaba.olb.client;

import hr.kaba.olb.client.host.handlers.HisoMessageDecoder;
import hr.kaba.olb.client.host.handlers.HisoProtocolHandler;
import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.Protocol;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.HisoHeader;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.protocol.NmmResponder;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.trx.HisoResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

class OlbClientTrxTest {

    private EmbeddedChannel embeddedChannel;
    private TrxResponder okResponderMock;

    @BeforeEach
    void setUp() {

        okResponderMock = mock(TrxResponder.class);

        HisoResponse okResponse = new HisoResponse(ResponseCode.TRX_APPROVED, Integer.valueOf(1000), Integer.valueOf(2000));
        when(okResponderMock.respond(any(HISOMessage.class))).thenReturn(okResponse);

        embeddedChannel = new EmbeddedChannel(
                new ChannelInitializer<EmbeddedChannel>() {

                    @Override
                    protected void initChannel(EmbeddedChannel channel) throws Exception {

                        channel.pipeline()
                               .addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer(Protocol.ETX)))
                               .addLast(new StringDecoder(Protocol.HISO_CHARSET))
                               .addLast(new HisoMessageDecoder())
                               .addLast(new HisoProtocolHandler(InitiatorType.HOST, NmmResponder.OK_NMM_RESPONDER, okResponderMock));

                    }
                }
        );
    }



    @Test
    void atmRequest() {
        String requestString = "ISO0160000100200B238820128A0801800000000100000000120000000000100000629222135019477002135063006300011119101151082376020206000485645=191222100000110950007441        A1151082        KABA RIJEKA           RIJEKA       HR HR1910122400PRO1+0000132400PRO10100P11240000800000";
        String request = HisoHeader.headerFrom(requestString)
                                   .concat(requestString)
                                   .concat(Protocol.MESSAGE_TERMINATOR);

        ByteBuf buf = Unpooled.buffer();
        int i = buf.writeCharSequence(request, Protocol.HISO_CHARSET);
        ByteBuf input = buf.duplicate();


        System.out.println("write inbound: " + input.toString());
        embeddedChannel.writeInbound(input);

        ByteBuf read = embeddedChannel.readInbound();

        System.out.println("READ: " + read);

        verify(okResponderMock).respond(any(HISOMessage.class));
    }

    private String constructResponse(String request) {
        Map<BitmapField, String> responseFields = new HashMap<>();
        responseFields.put(PrimaryBitmapField.P39, "00");
        responseFields.put(PrimaryBitmapField.P44A, "254000000001000000000002000");

        HISOMessage responsMessage = OLBCodec.respondTo(OLBCodec.decode(request), InitiatorType.HOST, responseFields);
        return OLBCodec.encodeAndWrap(responsMessage);
    }

}