package hr.kaba.olb.logger.gelf;

import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.field.TransactionCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Mapper of HISO field to GELF message name value entries
 *
 * @author Zlatko Gudasić
 * @version 1.0
 * @since 12.11.2018
 */
class HisoToGelf {

    public enum Mapping {
        PROTOCOL(Arrays.asList(ProductIndicator.ALL_PRODUCTS), "protocol", message -> message.getProductType().name()),
        TYPE(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "type", message -> message.getMessageType().getCode()),
        CODE(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "code", message -> message.getISOTransactionalCode().orElse(TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES).getCode()),
        RRN(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "rrn", message -> message.getFields().get(PrimaryBitmapField.P37)),
        AMOUNT(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "amount", AMOUNT_FROM_MESSAGE),
        CARD(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "card", message -> message.getFields().get(PrimaryBitmapField.P35).split("=")[0]),
        TRX_DATE(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "transmission_date", message -> message.getFields().get(PrimaryBitmapField.P7)),
        RESPONSE(Arrays.asList(ProductIndicator.TRANSACTION_PRODUCTS), "response", message -> message.getFields().getOrDefault(PrimaryBitmapField.P39, "XX")),
        ;

        private final List<ProductIndicator> mapForProducts;
        private final String name;
        private final Function<HISOMessage, String> getter;

        /**
         * @param mapForProducts for what product types is field valid
         * @param name           name of field in HISO message
         * @param getter         strategy of getting field value from a message
         */
        Mapping(List<ProductIndicator> mapForProducts, String name, Function<HISOMessage, String> getter) {
            this.mapForProducts = mapForProducts;
            this.name = name;
            this.getter = getter;
        }

        public List<ProductIndicator> forProducts() {
            return mapForProducts;
        }

        static List<Mapping> valuesFor(ProductIndicator productIndicator) {
            return Arrays.stream(Mapping.values())
                         .filter(v -> v.forProducts().contains(productIndicator))
                         .collect(Collectors.toList());
        }
    }


    static Map<String, String> adapt(HISOMessage message) {
        Map<String, String> gelfFields = new HashMap<>();

        for (Mapping field : Mapping.valuesFor(message.getProductType())) {
            gelfFields.put(field.name, field.getter.apply(message));
        }

        return gelfFields;
    }


    private final static Function<HISOMessage, String> AMOUNT_FROM_MESSAGE = (HISOMessage message) -> {
        String trxAmountString = message.getFields().get(PrimaryBitmapField.P4);
        return String.valueOf(Integer.valueOf(trxAmountString) / 100.0);
    };

}
