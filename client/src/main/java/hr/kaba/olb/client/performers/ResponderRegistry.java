package hr.kaba.olb.client.performers;

import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.nmm.ResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponderRegistry {

    private final static Logger logger = LoggerFactory.getLogger(ResponderRegistry.class);

    private Map<ProductIndicator, ResponseRenderer> responders;

    public ResponderRegistry(Map<ProductIndicator, ResponseRenderer> responders) {
        this.responders = responders;
    }

    public void addResponder(ProductIndicator respondOn, ResponseRenderer responseRenderer) {
        responders.putIfAbsent(respondOn, responseRenderer);
    }

    public HISOMessage respond(HISOMessage request, LocalDateTime transmissionDateTime) {

        logger.debug("responding to: {} of product type: {} with response time: {}", request, request.getProductType(), transmissionDateTime);

        ResponseRenderer responseRenderer = responders.get(request.getProductType());

        logger.debug("Found registered responseRenderer for product type: {}", responseRenderer);

        String responseDateTime = Formatters.formatTransmissionDate(transmissionDateTime);

        logger.debug("will respond with transmission time: {}", responseDateTime);

        HISOMessage respond = request;

        if (responseRenderer != null) {
            respond = responseRenderer.respond(request, ResponseCode.NMM_APPROVED, responseDateTime);
        } else {
            logger.warn("No responseRenderer registered for: {}", request.getProductType().getCode());
        }

        logger.debug("got response: {}", respond);

        return respond;

    }

}
