package hr.kaba.olb.protocol;

import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;

/**
 * Interface for clients implementing NMM (Network Management Messages) protocol
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface NmmResponder {

    ResponseCode respond(HISOMessage request);

    /**
     * nmm responder that approves every request
     */
    NmmResponder OK_NMM_RESPONDER = request -> ResponseCode.NMM_APPROVED;

}
