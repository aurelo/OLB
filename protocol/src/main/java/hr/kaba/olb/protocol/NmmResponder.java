package hr.kaba.olb.protocol;

import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;

@FunctionalInterface
public interface NmmResponder {

    ResponseCode respond(HISOMessage request);

    final static NmmResponder OK_NMM_RESPONDER = request -> ResponseCode.NMM_APPROVED;

}
