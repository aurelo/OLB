package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;

@FunctionalInterface
public interface ResponseRenderer {
    HISOMessage respond(HISOMessage request, ResponseCode responseCode, String transmissionDateTime);
}
