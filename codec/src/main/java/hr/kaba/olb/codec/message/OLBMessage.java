package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.message.field.ATMTransactionCode;
import hr.kaba.olb.codec.message.field.TransactionCode;
import hr.kaba.olb.codec.message.field.POSTransactionCode;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.bitmap.Bitmap;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * A value object representing an OLB message: with header, message type and values
 * of message fields based on Online do Banke - OLB specification
 * <p>
 *
 * @author Zlatko Gudasić
 */
public class OLBMessage implements HISOMessage {

    private final Base24Header header;
    private final MessageType messageType;
    private final Map<BitmapField, String> fieldsValues;

    public OLBMessage(Base24Header header, MessageType messageType, Map<BitmapField, String> data) {
        this.header = header;
        this.messageType = messageType;
        this.fieldsValues = data;
    }

    @Override
    public Base24Header getHeader() {
        return header;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public Map<BitmapField, String> getFieldsValues() {
        return fieldsValues;
    }

    @Override
    public ProductIndicator getProductType() {
        return header.getProductIndicator();
    }

    @Override
    public String getPrimaryBitmap() {
        String binaryBitmap = Bitmap.binaryBitmapFromFields(
                getFields()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getKey() instanceof PrimaryBitmapField)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );

        return Bitmap.binaryToHex(binaryBitmap);
    }

    @Override
    public Map<BitmapField, String> getFields() {
        return fieldsValues;
    }

    @Override
    public Optional<TransactionCode> getTransactionCode() {
        Optional<TransactionCode> maybeTransactionCode = Optional.empty();

        if (getProductType() == ProductIndicator.ATM) {
            maybeTransactionCode = Optional.of(ATMTransactionCode.from(getFields().get(PrimaryBitmapField.P3)));
        }
        else if (getProductType() == ProductIndicator.POS) {
            maybeTransactionCode = Optional.of(POSTransactionCode.from(getFields().get(PrimaryBitmapField.P3)));
        }


        return maybeTransactionCode;

    }

    @Override
    public Optional<TransactionCode.ISOTransactionCode> getISOTransactionalCode() {
        if (!getTransactionCode().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(getTransactionCode().get().getISOTransactionCode());
    }


    @Override
    public String dataEncoded() {
        return Bitmap.encode(getFields());
    }
}
