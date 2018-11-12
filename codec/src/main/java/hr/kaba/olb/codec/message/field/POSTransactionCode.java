package hr.kaba.olb.codec.message.field;


import hr.kaba.olb.codec.constants.ProductIndicator;


/**
 * Transaction codes representing type of transaction request of request
 * P3 field in the message
 * Contains 6 characters
 * 1-2 => ISO transaction code
 * 3-4 => from account type
 * 5-6 => to account type
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class POSTransactionCode extends TransactionCode {

    private final TransactionCode.ISOTransactionCode isoTransactionCode;
    private final TransactionCode.AccountType fromAccountType;
    private final TransactionCode.AccountType toAccountType;


    public POSTransactionCode(ISOTransactionCode isoTransactionCode, AccountType fromAccountType, AccountType toAccountType) {
        this.isoTransactionCode = isoTransactionCode;
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
    }

    @Override
    public ProductIndicator forProduct() {
        return ProductIndicator.POS;
    }

    public static POSTransactionCode from(String message) throws IllegalArgumentException{

        if (message.length() != 6) {
            throw new IllegalArgumentException(String.format("Illegal argument for parsing POS transaction code: %s", message));
        }


        return new POSTransactionCode(ISOTransactionCode.find(message.substring(0, 2)), AccountType.find(message.substring(2, 4)), AccountType.find(message.substring(4, 6)));
    }


    public ISOTransactionCode getIsoTransactionCode() {
        return isoTransactionCode;
    }

    public AccountType getFromAccountType() {
        return fromAccountType;
    }

    public AccountType getToAccountType() {
        return toAccountType;
    }

    @Override
    public ISOTransactionCode getISOTransactionCode() {
        return this.isoTransactionCode;
    }
}
