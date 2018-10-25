package hr.kaba.olb.logger.gelf;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.field.TransactionCode;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HisoToGelf {

    public enum Mapping {
        PROTOCOL("protocol", message -> message.getProductType().name()),
        TYPE("type", message -> message.getMessageType().getCode()),
        CODE("code", message -> message.getISOTransactionalCode().orElse(TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES).getCode()),
        RRN("rrn", message -> message.getFields().get(PrimaryBitmapField.P37)),
        AMOUNT("amount", message -> message.getFields().get(PrimaryBitmapField.P4)),
        CARD("card", message -> message.getFields().get(PrimaryBitmapField.P35).split("=")[0]),
        TRX_DATE("transmission_date", message -> message.getFields().get(PrimaryBitmapField.P7)),
        RESPONSE("response", message -> message.getFields().getOrDefault(PrimaryBitmapField.P39, "XX"))
        ;

        private final String name;
        private final Function<HISOMessage, String> getter;

        Mapping(String name, Function<HISOMessage, String> getter) {
            this.name = name;
            this.getter = getter;
        }
    }


    static Map<String, String> adapt(HISOMessage message) {
        Map<String, String> gelfFields = new HashMap<>();

        for (Mapping field : Mapping.values()) {
            gelfFields.put(field.name, field.getter.apply(message));
        }

        return gelfFields;
    }

}
