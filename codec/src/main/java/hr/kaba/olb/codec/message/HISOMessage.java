package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.field.TransactionCode;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.Map;
import java.util.Optional;

/**
 * Interface representing key elements of On Line to Bank HISO message protocol
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
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
