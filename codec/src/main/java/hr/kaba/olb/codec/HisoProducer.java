package hr.kaba.olb.codec;

import hr.kaba.olb.codec.message.HISOMessage;

@FunctionalInterface
public interface HisoProducer {
    HISOMessage produce();
}
