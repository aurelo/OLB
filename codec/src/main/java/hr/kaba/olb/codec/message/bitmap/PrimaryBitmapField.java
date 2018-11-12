package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.*;
import java.util.function.Predicate;


/**
 * First 64 fields in a HISO message
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public enum PrimaryBitmapField implements BitmapField{
    P1(1, FieldType.AN, FieldSize.FIXED, 16, ProductIndicator.ALL_PRODUCTS, "Sekundarna bitmapa"),
    P2(2, FieldType.AN, FieldSize.VARIABILE, 19, ProductIndicator.TRANSACTION_PRODUCTS, "PAN"),
    P3(3, FieldType.AN, FieldSize.FIXED, 6, ProductIndicator.TRANSACTION_PRODUCTS, "Tip transakcije"),
    P4(4, FieldType.N, FieldSize.FIXED, 12, ProductIndicator.TRANSACTION_PRODUCTS, "Iznos transakcije u valuti izdavatelja"),
    P5(5, FieldType.N, FieldSize.FIXED, 12, ProductIndicator.TRANSACTION_PRODUCTS, "Orginalni iznos transacije"),
    P6(6, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P7(7, 10, ProductIndicator.ALL_PRODUCTS, "GMT transakcije [MMDDhhmmss]"),
    P8(8, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P9(9, FieldType.N, FieldSize.FIXED, 18, ProductIndicator.TRANSACTION_PRODUCTS, "Prenamjenjeno polje – Tečajna rata za originalnu valutu."),
    P10(10, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P11(11, FieldType.N, FieldSize.FIXED, 6, ProductIndicator.ALL_PRODUCTS, "Broj poruke inicijatora zahtjeva"),
    P12(12, FieldType.N, FieldSize.FIXED, 6, ProductIndicator.TRANSACTION_PRODUCTS, "Local Transaction Time - [hhmmss]"),
    P13(13, FieldType.N, FieldSize.FIXED, 4, ProductIndicator.TRANSACTION_PRODUCTS, "Local Transaction Date - [MMDD]"),
    P14(14, FieldType.N, FieldSize.FIXED, 4, ProductIndicator.TRANSACTION_PRODUCTS, "Datum isteka kartice [YYMM]"),
    P15(15, FieldType.N, FieldSize.FIXED, 4, ProductIndicator.TRANSACTION_PRODUCTS, "Settlement Date - [MMDD]"),
    P16(16, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P17(17, FieldType.N, FieldSize.FIXED, 4, ProductIndicator.TRANSACTION_PRODUCTS, "Capture Date - [MMDD]"),
    P18(18, FieldType.N, FieldSize.FIXED, 4, ProductIndicator.TRANSACTION_PRODUCTS, "Kategorija prodajnog mjesta"),
    P19(19, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P20(20, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P21(21, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P22(22, FieldType.N, FieldSize.FIXED, 3, ProductIndicator.TRANSACTION_PRODUCTS, "point of Service Entry Mode"),
    P23(23, FieldType.N, FieldSize.FIXED, 3, ProductIndicator.TRANSACTION_PRODUCTS, "Redni broj kartice"),
    P24(24, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P25(25, FieldType.N, FieldSize.FIXED, 2, ProductIndicator.TRANSACTION_PRODUCTS, "Point of Service Condition Code"),
    P26(26, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P27(27, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P28(28, 8, ProductIndicator.TRANSACTION_PRODUCTS, "transakcijskog iznosa koji predstavlja rabat ili fee. Ne koristi se."),
    P29(29, 8, ProductIndicator.TRANSACTION_PRODUCTS, "Settlement Fee Amount"),
    P30(30, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P31(31, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P32(32, FieldType.N, FieldSize.VARIABILE, 11, ProductIndicator.TRANSACTION_PRODUCTS, "Identifikacija prihvatitelja transakcije."),
    P33(33, FieldType.N, FieldSize.VARIABILE, 11, ProductIndicator.TRANSACTION_PRODUCTS, "Ne koristi se."),
    P34(34, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P35(35, FieldType.ANS, FieldSize.VARIABILE, 37, ProductIndicator.TRANSACTION_PRODUCTS, "Magnetska ISO2 traka"),
    P36(36, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P37(37, FieldType.AN, FieldSize.FIXED, 12, ProductIndicator.TRANSACTION_PRODUCTS, "RRN"),
    P38(38, FieldType.AN, FieldSize.FIXED, 6, ProductIndicator.TRANSACTION_PRODUCTS, "Broj autorizacije, Approval Code."),
    P39(39, FieldType.AN, FieldSize.FIXED, 2, ProductIndicator.ALL_PRODUCTS, "Response Code"),
    P40(40, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P41(41, FieldType.ANS, FieldSize.FIXED, 16, ProductIndicator.TRANSACTION_PRODUCTS, "Identifikacija terminala (TID)"),
    P42(42, FieldType.ANS, FieldSize.FIXED, 16, ProductIndicator.TRANSACTION_PRODUCTS, "Identifikacija trgovine (MID)"),
    P43(43, FieldType.ANS, FieldSize.FIXED, 40, ProductIndicator.TRANSACTION_PRODUCTS, "Naziv prodajnog mjesta"),
    P44A(44, FieldType.ANS, FieldSize.FIXED, 27, ProductIndicator.ATM_PRODUCT, "Polje je namjenjeno za razmjenu tipa računa, stanja računa/kredita i/ili raspoloživog iznosa."),
    P44P(44, FieldType.ANS, FieldSize.FIXED, 4, ProductIndicator.POS_PRODUCT, "Ne koristi se."),
    P45(45, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P46(46, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P47(47, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P48A(48, FieldType.ANS, FieldSize.FIXED, 47, ProductIndicator.ATM_PRODUCT, "Ne koristi se."),
    P48P(48, FieldType.ANS, FieldSize.FIXED, 30, ProductIndicator.POS_PRODUCT, "Ne koristi se."),
    P49(49, 3, ProductIndicator.TRANSACTION_PRODUCTS, "Šifra valute izdavatelja. Troznamenkasta oznaka ISO-4217."),
    P50(50, 3, ProductIndicator.TRANSACTION_PRODUCTS, "Prenamjenjeno polje – šifra originalne valute ISO 4217"),
    P51(51, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P52(52, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P53(53, 16, ProductIndicator.NMM_PRODUCT, "TODO"),
    P54(54, FieldType.ANS, FieldSize.FIXED, 15, ProductIndicator.UNKNOWN_PRODUCT, "Ne koristi se."),
    P55(55, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P56(56, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P57(57, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P58(58, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P59(59, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P60A(60, FieldType.ANS, FieldSize.FIXED, 15, ProductIndicator.ATM_PRODUCT, "Podaci o vlasniku ATM terminala"),
    P60P(60, FieldType.ANS, FieldSize.FIXED, 19, ProductIndicator.POS_PRODUCT, "Podaci o vlasniku POS terminala"),
    P61A(61, FieldType.ANS, FieldSize.FIXED, 16, ProductIndicator.ATM_PRODUCT, "Podaci o izdavatelju kartice"),
    P61P(61, FieldType.ANS, FieldSize.FIXED, 22, ProductIndicator.POS_PRODUCT, "Podaci o izdavatelju kartice"),
    P62(62, 0, ProductIndicator.UNKNOWN_PRODUCT, "TODO"),
    P63(63, FieldType.ANS, FieldSize.VARIABILE, 1000, ProductIndicator.POS_PRODUCT, "ISO polje s POS token podacima."),
    P64(64, 16, ProductIndicator.NMM_PRODUCT, "TODO")
    ;

    private final int position;
    private BitmapField.FieldType fieldType;
    private FieldSize fieldSize;
    private final int maxLength;
    private ProductIndicator[] productIndicators;
    private final String description;



    PrimaryBitmapField(int position, FieldType fieldType, FieldSize fieldSize, int maxLength, ProductIndicator[] productIndicators, String description) {
        this.position = position;
        this.fieldType = fieldType;
        this.fieldSize = fieldSize;
        this.maxLength = maxLength;
        this.productIndicators = productIndicators;
        this.description = description;
    }

    PrimaryBitmapField(int position, int maxLength, ProductIndicator[] productIndicators, String description) {
        this(position, FieldType.AN, FieldSize.FIXED, maxLength, productIndicators, description);
    }


    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public FieldSize getFieldSize() {
        return fieldSize;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public ProductIndicator[] getProductIndicators() {
        return productIndicators;
    }



    @Override
    public boolean isValidValue(String value) {
        return ((value != null) && (value.length() == this.getMaxLength()));
    }

    @Override
    public int getOrderingPosition() {
        return getPosition();
    }

    public static List<BitmapField> fieldsForProduct(ProductIndicator productIndicator) {
        List<BitmapField> fields = new ArrayList<>();

        for (PrimaryBitmapField f : PrimaryBitmapField.values()) {
            if (Arrays.asList(f.productIndicators).contains(productIndicator)) {
                fields.add(f);
            }
        }

        return fields;
    }


    public static BitmapField fromPosition(int position) {
        if (position < 1 || position > PrimaryBitmapField.values().length) {
            throw new IllegalArgumentException(String.format("Invalid position %d for primary bitmap", position));
        }

        Optional<PrimaryBitmapField> result = Arrays.stream(PrimaryBitmapField.values()).filter(e -> position == e.getPosition()).findFirst();
        return result.orElseThrow(() -> new EnumConstantNotPresentException(PrimaryBitmapField.class, "Could not find field"));
    }

    public final static Predicate<Map.Entry<BitmapField, String>> TYPE = (entry) -> entry.getKey() instanceof PrimaryBitmapField;

}
