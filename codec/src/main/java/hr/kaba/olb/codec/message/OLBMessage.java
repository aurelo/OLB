package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.message.field.ATMTransactionCode;
import hr.kaba.olb.codec.message.field.TransactionCode;
import hr.kaba.olb.codec.message.field.POSTransactionCode;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.bitmap.Bitmap;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(OLBMessage.class);

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
        return Bitmap.bitmapFromFields(getFields(), PrimaryBitmapField.TYPE);
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

    public String prettyPrint() {

        logger.debug("pretty print for message: {} product type: {}", this, getProductType());

        String header = String.format("%s - %s - %s", getProductType(), getMessageType().getCode(), getISOTransactionalCode().orElse(TransactionCode.ISOTransactionCode.NMM));

        String fields = getFields().entrySet()
                                   .stream()
                                   .sorted((e1, e2) -> e1.getKey().compare(e1.getKey(), e2.getKey()))
                                   .map(entry -> {
                                       System.out.println("Entry: " + entry);
                                       return String.format("%s=%s", entry.getKey(), entry.getValue());
                                        }
                                   )
                                   .collect(Collectors.joining("\n"));

        return String.format("%s\n%s", header, fields);
    }
}
