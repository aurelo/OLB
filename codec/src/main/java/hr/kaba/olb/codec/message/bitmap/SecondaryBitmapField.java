package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum SecondaryBitmapField implements BitmapField{
    P65(1, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P66(2, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P67(3, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P68(4, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P69(5, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P70(6, 3, ProductIndicator.NMM_PRODUCT, "TODO"),
    P71(7, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P72(8, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P73(9, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P74(10, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P75(11, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P76(12, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P77(13, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P78(14, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P79(15, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P80(16, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P81(17, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P82(18, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P83(19, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P84(20, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P85(21, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P86(22, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P87(23, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P88(24, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P89(25, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    S90(26, 42, ProductIndicator.TRANSACTION_PRODUCTS, "Podaci originalnoj transakciji u reversal ili advice poruci."),
    P91(27, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P92(28, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P93(29, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P94(30, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    S95(31, 42, ProductIndicator.TRANSACTION_PRODUCTS, "Stvarni iznos transakcije (postojanje ovog polja ukazuje na «parcijalni reversal/advice»)."),
    P96(32, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P97(33, 42, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P98(34, 42, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P99(35, 42, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    S100(36, FieldType.N, FieldSize.VARIABILE, 11, ProductIndicator.TRANSACTION_PRODUCTS, "Šifra institucije prihvatitelja poruke"),
    P101(37, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    S102(38, FieldType.ANS, FieldSize.VARIABILE, 28, ProductIndicator.TRANSACTION_PRODUCTS, "Identifikacija računa pridruženog kartici"),
    S103(39, FieldType.ANS, FieldSize.VARIABILE, 28, ProductIndicator.TRANSACTION_PRODUCTS, "Identifikacija sekundarnog računan kartice"),
    P104(40, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P105(41, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P106(42, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P107(43, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P108(44, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P109(45, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P110(46, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P111(47, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P112(48, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P113(49, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P114(50, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P115(51, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P116(52, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P117(53, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P118(54, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P119(55, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P120(56, 9, ProductIndicator.NMM_PRODUCT, "Base24 Key Management"),
    P121(57, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P122(58, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    S123(59, FieldType.ANS, FieldSize.VARIABILE, 553, ProductIndicator.NMM_PRODUCT, "Cryptographic Service MessageBuilder"),
    S123A(59, 15, ProductIndicator.ATM_PRODUCT, "Polje depozitne funkcije (trenutno nije podržano)"),
    S124A(60, 4, ProductIndicator.ATM_PRODUCT, "Tip depozita"),
    S125A(61, 4, ProductIndicator.ATM_PRODUCT, "Polje depozitne funkcije. Trenutno nije podrzano"),
    P126A(62, FieldType.ANS, FieldSize.VARIABILE, 998, ProductIndicator.ATM_PRODUCT, "ISO polje s ATM token podacima"),
    S126P(62, 41, ProductIndicator.POS_PRODUCT, "Podaci o predautorizaciji"),
    S127P(63, FieldType.ANS, FieldSize.VARIABILE, 200, ProductIndicator.POS_PRODUCT, "POS podaci o specifičnim transakcijama (plaćanje na rate) i dojava stanja računa."),
    S128(64, 14, ProductIndicator.NMM_PRODUCT, "Secondary MAC")
    ;

    private static final int POSITION_OFFSET = 64;

    private final int position;
    private final FieldType fieldType;
    private final FieldSize fieldSize;
    private final int maxLength;
    private final ProductIndicator[] productIndicators;
    private final String description;

    SecondaryBitmapField(int position, FieldType fieldType, FieldSize fieldSize, int maxLength, ProductIndicator[] productIndicators, String description) {
        this.position = position;
        this.fieldType = fieldType;
        this.fieldSize = fieldSize;
        this.maxLength = maxLength;
        this.productIndicators = productIndicators;
        this.description = description;
    }

    SecondaryBitmapField(int position, int maxLength, ProductIndicator[] productIndicators, String description) {
        this(position, FieldType.ANS, FieldSize.FIXED, maxLength, productIndicators, description);
    }

    public int getPosition() {
        return position;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public ProductIndicator[] getProductIndicators() {
        return productIndicators;
    }

    public String getDescription() {
        return description;
    }

    public boolean isValidValue(String value) {
        return ((value != null) && (value.length() == this.getMaxLength()));
    }

    @Override
    public int getOrderingPosition() {
        return getPosition() + POSITION_OFFSET;
    }


    public static List<BitmapField> fieldsForProduct(ProductIndicator productIndicator) {
        List<BitmapField> fields = new ArrayList<>();

        for (SecondaryBitmapField f : SecondaryBitmapField.values()) {
            if (Arrays.asList(f.productIndicators).contains(productIndicator)) {
                fields.add(f);
            }
        }

        return fields;
    }


    public static BitmapField fromPosition(int position) {
        if (position < 1 || position > SecondaryBitmapField.values().length) {
            throw new IllegalArgumentException(String.format("Invalid position %d for primary bitmap", position));
        }

        Optional<SecondaryBitmapField> result = Arrays.stream(SecondaryBitmapField.values()).filter(e -> position == e.getPosition()).findFirst();
        return result.orElseThrow(() -> new EnumConstantNotPresentException(SecondaryBitmapField.class, "Could not find field"));
    }


}
