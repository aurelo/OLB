package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.field.TransactionCode;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.Map;
import java.util.Optional;

public interface HISOMessage {

    Base24Header getHeader();
    ProductIndicator getProductType();
    MessageType getMessageType();

    String getPrimaryBitmap();

    Map<BitmapField, String> getFields();

    Optional<TransactionCode> getTransactionCode();

    Optional<TransactionCode.ISOTransactionCode> getISOTransactionalCode();

    String dataEncoded();

    String prettyPrint();

}
