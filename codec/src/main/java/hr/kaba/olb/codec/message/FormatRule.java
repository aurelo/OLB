package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.bitmap.BitmapField;

import java.util.Objects;

class FormatRule {

    public enum FieldStatus {
        MANDATORY, OPTIONAL, EMPTY
    }

    private final ProductIndicator productIndicator;
    private final MessageType messageType;
    private final BitmapField field;
    private final FieldStatus fieldStatus;


    public FormatRule(ProductIndicator productIndicator, MessageType messageType, BitmapField field, FieldStatus fieldStatus) {
        this.productIndicator = productIndicator;
        this.messageType = messageType;
        this.field = field;
        this.fieldStatus = fieldStatus;
    }

    public FormatRule(ProductIndicator productIndicator, MessageType messageType, BitmapField field) {
        this(productIndicator, messageType, field, FieldStatus.MANDATORY);
    }

    public FieldStatus getFieldStatus() {
        return fieldStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // null check
        if (obj == null) return false;
        // type check and cast
        if (getClass() != obj.getClass()) return false;

        FormatRule formatRule = (FormatRule) obj;

        // field comparison
        return Objects.equals(productIndicator, formatRule.productIndicator)
                && Objects.equals(messageType, formatRule.messageType)
                && Objects.equals(field, formatRule.field)
                ;
    }
}
