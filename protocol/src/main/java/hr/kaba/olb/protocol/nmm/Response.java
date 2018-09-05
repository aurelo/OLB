package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Response implements ResponseRenderer {

    private final static Logger logger = LoggerFactory.getLogger(Response.class);

    private final InitiatorType responder;

    public Response(InitiatorType responder) {
        this.responder = responder;
    }

    @Override
    public HISOMessage respond(HISOMessage request, ResponseCode responseCode, String transmissionDateTime) {

        logger.debug("responding on request: {} with response code: {}", request, responseCode);

        Map<BitmapField, String> responseFields = new HashMap<>();

        responseFields.put(PrimaryBitmapField.P7, transmissionDateTime);
        responseFields.put(PrimaryBitmapField.P39, responseCode.getCode());

        logger.debug("calling OlbCodec.respondTo");

        return OLBCodec.respondTo(request, responder, responseFields);
    }
}
