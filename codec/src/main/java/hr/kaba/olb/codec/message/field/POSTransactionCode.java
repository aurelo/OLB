package hr.kaba.olb.codec.message.field;


import hr.kaba.olb.codec.constants.ProductIndicator;

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
