package hr.kaba.olb.client.host.handlers;

import hr.kaba.olb.client.host.MessageWriter;
import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.logger.gelf.HisoLog4j2GelfLogger;
import hr.kaba.olb.protocol.NmmResponder;
import hr.kaba.olb.protocol.Sys;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.nmm.Request;
import hr.kaba.olb.protocol.nmm.Response;
import hr.kaba.olb.protocol.nmm.ResponseRenderer;
import hr.kaba.olb.protocol.trx.HisoResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Arrays;

/**
 * Handler that calls domain service responsible for responding to request
 * and implements protocol required network management status messages
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class HisoProtocolHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HisoProtocolHandler.class);

    private final static Marker HISO_REQ_RESP_MARKER = MarkerFactory.getMarker("HISO_REQ_RESP");


    private final NmmResponder nmmResponder;
    private final TrxResponder trxResponder;

    private final ResponseRenderer nmmResponseRenderer;
    private final hr.kaba.olb.protocol.trx.ResponseRenderer trxResponseRenderer;


    /**
     *
     * @param respondAs who should be initiator of responses
     * @param nmmResponder service responsible for network management messages requests/responses
     * @param trxResponder service responsible for transaction responses
     */
    public HisoProtocolHandler(InitiatorType respondAs, NmmResponder nmmResponder, TrxResponder trxResponder) {
        this.nmmResponder = nmmResponder;
        this.trxResponder = trxResponder;

        nmmResponseRenderer = new Response(respondAs);
        trxResponseRenderer = new hr.kaba.olb.protocol.trx.Response(respondAs);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HISOMessage request = (HISOMessage) msg;

        logger.info("REQUEST: [{}]\n{}", ((HISOMessage) msg).dataEncoded(), ((HISOMessage) msg).prettyPrint());

        HisoLog4j2GelfLogger gelfRequestLogger = new HisoLog4j2GelfLogger(request);
        gelfRequestLogger.log(() -> logger.info(HISO_REQ_RESP_MARKER, "Hiso request [{}]", request.dataEncoded()));



        HISOMessage response = null;

        if (isNmm(request)) {

            ResponseCode responseCode = nmmResponder.respond(request);

            response = nmmResponseRenderer.respond(request, responseCode, sysdate());

        } else if (isTrx(request)) {

            HisoResponse trxResponse = trxResponder.respond(request);

            response = trxResponseRenderer.respond(request, trxResponse, sysdate());
        }


        if (response != null) {


            logger.info("RESPONSE: [{}]\n{}", response.dataEncoded(), response.prettyPrint());

            String encodedAndWrapedMessage = OLBCodec.encodeAndWrap(response);
            logger.info("ENCODED: [{}]", encodedAndWrapedMessage);

            HisoLog4j2GelfLogger gelfResponseLogger = new HisoLog4j2GelfLogger(response);
            gelfResponseLogger.log(() -> logger.info(HISO_REQ_RESP_MARKER, "Hiso response [{}]", request.dataEncoded()));

            MessageWriter.write(ctx, encodedAndWrapedMessage);

        }
        else if (response == HisoResponse.NO_RESPONSE){
            logger.info("Not sending response because request was Reject message");
        }
        else{

            super.channelRead(ctx, msg);
        }

    }

    /**
     * Sends logon request when connection to host was successful as required by protocol
     *
     * @param ctx channel handler context
     * @throws Exception possible during writing
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("Channel active, writing nmm logon request");

        HISOMessage logonRequest = Request.logon(sysdate(), Sys.auditTraceNumber());

        logger.info("LOGON REQUEST: [{}]\n{}", logonRequest.dataEncoded(), logonRequest.prettyPrint());

        String encodedAndWrapedMessage = OLBCodec.encodeAndWrap(logonRequest);

        logger.info("ENCODED: [{}]", encodedAndWrapedMessage);

        MessageWriter.write(ctx, encodedAndWrapedMessage);

        super.channelActive(ctx);
    }

    private String sysdate() {
        return Sys.transmissionDate.get();
    }

    private boolean isTrx(HISOMessage request) {
        return Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS).contains(request.getProductType());
    }

    private boolean isNmm(HISOMessage request) {
        return request.getProductType().equals(ProductIndicator.NMM);
    }

}
