package hr.kaba.olb.protocol;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.trx.HisoResponse;

/**
 * Interface for clients implementing transaction protocols (ATM/POS)
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface TrxResponder {

    HisoResponse respond(HISOMessage request);

    /**
     * Reject messages (message type = 9xxx) are not to be responded to
     * NO_RESPONSE response signifies this kind of responses
     */
    HISOMessage NO_RESPONSE = null;
}
