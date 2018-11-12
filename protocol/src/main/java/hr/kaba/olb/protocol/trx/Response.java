package hr.kaba.olb.protocol.trx;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.trx.format.Atm44;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Service capable of creating response messages on basis of request and response fields
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class Response implements ResponseRenderer {

    private final static Logger logger = LoggerFactory.getLogger(hr.kaba.olb.protocol.nmm.Response.class);

    private final InitiatorType responder;

    public Response(InitiatorType responder) {
        this.responder = responder;
    }

    @Override
    public HISOMessage respond(HISOMessage request, HisoResponse response, String transmissionDateTime) {

        if (response == HisoResponse.NO_RESPONSE) {
            return TrxResponder.NO_RESPONSE;
        }


        logger.debug("responding on request: {} with response code: {} with ledger balance: {} with available balance: {}", request, response.getResponseCode().getCode(), response.getLedgerBalance().orElse(0), response.getAvailableBalance().orElse(0));

        Map<BitmapField, String> responseFields = new HashMap<>();

        responseFields.put(PrimaryBitmapField.P7, transmissionDateTime);
        responseFields.put(PrimaryBitmapField.P39, response.getResponseCode().getCode());

        if (response.getApprovedCode().isPresent()) {
            responseFields.put(PrimaryBitmapField.P38, response.getApprovedCode().get());
        }

        if (isATM(request)) {
            Atm44 atm44 = Atm44.of(response.getLedgerBalance(), response.getAvailableBalance());

            if (atm44.shouldBeAdded()) {

                logger.debug("adding atm44 filed: {}", atm44.format());

                responseFields.put(PrimaryBitmapField.P44A, atm44.format());
            }
        }



        logger.debug("calling OlbCodec.respondTo");

        return OLBCodec.respondTo(request, responder, responseFields);
    }

    private boolean isATM(HISOMessage request) {
        return request.getProductType() == ProductIndicator.ATM;
    }

}
