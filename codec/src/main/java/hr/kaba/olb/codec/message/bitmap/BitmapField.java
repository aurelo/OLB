package hr.kaba.olb.codec.message.bitmap;

import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.Comparator;

public interface BitmapField extends Comparator<BitmapField> {

    public enum FieldType {
         A ("Alphabetic character")
        ,N ("Numeric character")
        ,S ("Special character")
        ,AN("Alphabetic and/or numeric")
        ,ANS("Alphabetic and/or numeric and/or special")
        ,AS("Alphabetic and/or special")
        ,NS("Numeric and/or numeric")
        ;

        public final String description;

        FieldType(String description) {
            this.description = description;
        }
    }

    public enum FieldSize {
        FIXED, VARIABILE
    }

    // postition od the field in the bitmap
    int getPosition();

    FieldType getFieldType();

    FieldSize getFieldSize();

    // length of the field
    int getMaxLength();
    // for what messages is field being used
    ProductIndicator[] getProductIndicators();

    boolean isValidValue(String value);

    int getOrderingPosition();

    @Override
    default int compare(BitmapField o1, BitmapField o2) {
        return o1.getOrderingPosition() - o2.getOrderingPosition();
    }

    default String encoded(String value) {

        String encodedValue;

        if (getFieldSize() == FieldSize.VARIABILE) {

            String format = String.format("%%0%dd%%s", Integer.valueOf(getMaxLength()).toString().trim().length());
            encodedValue = String.format(format, value.length(), value);
        }
        else {
            String format = String.format("%%-%ds", getMaxLength()); // padding spaces on max length
            encodedValue = String.format(format, value);
        }


        return encodedValue;
    }

}
