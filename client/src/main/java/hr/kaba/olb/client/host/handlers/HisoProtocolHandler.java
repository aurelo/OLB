package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.host.MessageWriter;
import hr.kaba.olb.client.host.Protocol;
import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.NmmResponder;
import hr.kaba.olb.protocol.Sys;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.nmm.Request;
import hr.kaba.olb.protocol.nmm.Response;
import hr.kaba.olb.protocol.nmm.ResponseRenderer;
import hr.kaba.olb.protocol.trx.TrxResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class HisoProtocolHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HisoProtocolHandler.class);

    private final InitiatorType respondAs;

    private final NmmResponder nmmResponder;
    private final TrxResponder trxResponder;

    private final ResponseRenderer nmmResponseRenderer;
    private final hr.kaba.olb.protocol.trx.ResponseRenderer trxResponseRenderer;


    public HisoProtocolHandler(InitiatorType respondAs, NmmResponder nmmResponder, TrxResponder trxResponder) {
        this.respondAs = respondAs;
        this.nmmResponder = nmmResponder;
        this.trxResponder = trxResponder;

        nmmResponseRenderer = new Response(this.respondAs);
        trxResponseRenderer = new hr.kaba.olb.protocol.trx.Response(respondAs);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HISOMessage request = (HISOMessage) msg;

        HISOMessage response = null;

        if (isNmm(request)) {

            ResponseCode responseCode = nmmResponder.respond(request);

            response = nmmResponseRenderer.respond(request, responseCode, sysdate());

        } else if (isTrx(request)) {

            TrxResponse trxResponse = trxResponder.respond(request);

            response = trxResponseRenderer.respond(request, trxResponse, sysdate());
        }


        if (response != null) {

            MessageWriter.write(ctx, OLBCodec.encode(response));
        }
        else{

            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("Channel active, writing nmm logon request");

        HISOMessage logonRequest = Request.logon(sysdate(), Sys.auditTraceNumber());

        MessageWriter.write(ctx, OLBCodec.encode(logonRequest));

        super.channelActive(ctx);
    }

    private String sysdate() {
        return Protocol.transmissionDate.get();
    }

    private boolean isTrx(HISOMessage request) {
        return Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS).contains(request.getProductType());
    }

    private boolean isNmm(HISOMessage request) {
        return request.getProductType().equals(ProductIndicator.NMM);
    }

}
