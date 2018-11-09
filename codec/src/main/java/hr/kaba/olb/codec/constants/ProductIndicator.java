package hr.kaba.olb.codec.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * first 2 letters in base 24 header representing message module
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public enum ProductIndicator {
     NMM("00", "Upravljačka poruka")
    ,ATM("01", "ATM transakcijska poruka")
    ,POS("02", "POS transakcijska poruka")
    ,FHM("08", "Poruka održavanja podataka")
    ,ERR(null, "Unsupported type")
    ;


    private final String code;
    private final String description;

    private static final Map<String, ProductIndicator> codes;
    static {
        codes = Arrays.stream(ProductIndicator.values()).collect(Collectors.toMap(ProductIndicator::getCode, e -> e));
    }


    ProductIndicator(String code, String description) {
        this.code = code;
        this.description = description;
    }


    @Override
    public String toString() {
        return String.format("%s (%s) - %s", this.name(), this.code, this.description);
    }

    public String getCode() {
        return this.code;
    }

    public static ProductIndicator find(String value) {
        return codes.getOrDefault(value, ProductIndicator.ERR);
    }


    public final static ProductIndicator[] NMM_PRODUCT = new ProductIndicator[]{ProductIndicator.NMM};
    public final static ProductIndicator[] POS_PRODUCT = new ProductIndicator[]{ProductIndicator.POS};
    public final static ProductIndicator[] ATM_PRODUCT = new ProductIndicator[]{ProductIndicator.ATM};
    public final static ProductIndicator[] TRANSACTION_PRODUCTS = new ProductIndicator[]{ProductIndicator.ATM, ProductIndicator.POS};
    public final static ProductIndicator[] UNKNOWN_PRODUCT = new ProductIndicator[]{ProductIndicator.ERR};
    public static final ProductIndicator[] FHM_PRODUCT = new ProductIndicator[]{ProductIndicator.FHM};

    public final static ProductIndicator[] ALL_PRODUCTS = new ProductIndicator[]{ProductIndicator.NMM, ProductIndicator.POS, ProductIndicator.ATM};

}
