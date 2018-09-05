package hr.kaba.olb.protocol.trx;

import hr.kaba.olb.codec.message.HISOMessage;

@FunctionalInterface
public interface ResponseRenderer {
        HISOMessage respond(HISOMessage request, TrxResponse response, String transmissionDateTime);

}
