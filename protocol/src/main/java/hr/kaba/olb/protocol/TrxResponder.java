package hr.kaba.olb.protocol;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.trx.TrxResponse;

@FunctionalInterface
public interface TrxResponder {
    TrxResponse respond(HISOMessage request);
}
