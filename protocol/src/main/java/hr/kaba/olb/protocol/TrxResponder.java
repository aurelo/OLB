package hr.kaba.olb.protocol;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.trx.HisoResponse;

@FunctionalInterface
public interface TrxResponder {

    final HISOMessage NO_RESPONSE = null;

    HisoResponse respond(HISOMessage request);
}
