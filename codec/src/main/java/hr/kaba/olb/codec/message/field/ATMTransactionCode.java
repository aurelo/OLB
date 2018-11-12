package hr.kaba.olb.codec.message.field;


import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.field.TransactionCode;

/**
 * Transaction codes representing type of transaction request of request
 * P3 field in the message
 * Contains 6 characters
 * 1-2 => ISO transaction code
 * 3-4 => from account type
 * 5   => to account type
 * 6   => transaction settlement indicator
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   09.11.2018
 */
public class ATMTransactionCode extends TransactionCode {

    private final TransactionCode.ISOTransactionCode isoTransactionCode;
    private final TransactionCode.AccountType fromAccountType;
    private final String toAccountType = "0"; // not used momentarily
    private final TransactionCode.TransactionSettlementIndicator transactionSettlementIndicator;

    public ATMTransactionCode(ISOTransactionCode isoTransactionCode, AccountType fromAccountType, TransactionSettlementIndicator transactionSettlementIndicator) {
        this.isoTransactionCode = isoTransactionCode;
        this.fromAccountType = fromAccountType;
        this.transactionSettlementIndicator = transactionSettlementIndicator;
    }

    public static ATMTransactionCode from(String message) throws IllegalArgumentException{

        if (message.length() != 6) {
            throw new IllegalArgumentException(String.format("Illegal argument for parsing ATM transaction code: %s", message));
        }


        return new ATMTransactionCode(ISOTransactionCode.find(message.substring(0, 2)), AccountType.find(message.substring(2, 4)), TransactionSettlementIndicator.find(message.substring(5)));
    }

    @Override
    public ProductIndicator forProduct() {
        return ProductIndicator.ATM;
    }

    public ISOTransactionCode getIsoTransactionCode() {
        return isoTransactionCode;
    }

    public AccountType getFromAccountType() {
        return fromAccountType;
    }

    public String getToAccountType() {
        return toAccountType;
    }

    public TransactionSettlementIndicator getTransactionSettlementIndicator() {
        return transactionSettlementIndicator;
    }

    @Override
    public ISOTransactionCode getISOTransactionCode() {
        return this.isoTransactionCode;
    }
}
