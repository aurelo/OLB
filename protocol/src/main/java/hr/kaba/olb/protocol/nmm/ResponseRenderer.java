package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;

/**
 * Abstraction for creating HISO response messages on basis of nmm request and response code
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface ResponseRenderer {
    HISOMessage respond(HISOMessage request, ResponseCode responseCode, String transmissionDateTime);
}
