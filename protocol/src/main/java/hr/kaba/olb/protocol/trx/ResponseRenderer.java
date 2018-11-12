package hr.kaba.olb.protocol.trx;

import hr.kaba.olb.codec.message.HISOMessage;

/**
 * Abstraction for creating HISO response messages on basis of request and responder fields
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface ResponseRenderer {
        HISOMessage respond(HISOMessage request, HisoResponse response, String transmissionDateTime);

}
