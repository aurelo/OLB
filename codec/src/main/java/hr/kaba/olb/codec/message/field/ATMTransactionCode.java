package hr.kaba.olb.codec.message.field;


import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.field.TransactionCode;

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
