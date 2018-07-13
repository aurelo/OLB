package hr.kaba.olb.codec.message.field;

import hr.kaba.olb.codec.constants.ProductIndicator;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionCode {

    public enum ISOTransactionCode {
         GOODS_AND_SERVICES("00", ProductIndicator.POS_PRODUCT)
        ,WITHDRAWAL("01", ProductIndicator.TRANSACTION_PRODUCTS)
        ,CREDIT_ADJUSTMENT("22", ProductIndicator.POS_PRODUCT)
        ,GOODS_AND_SERVICES_WITH_CASH_DISBURSEMENT("09", ProductIndicator.POS_PRODUCT)
        ,RESERVED_FOR_PRIVATE_USE_POS("14", ProductIndicator.POS_PRODUCT)
        ,RESERVED_FOR_PRIVATE_USE("19", ProductIndicator.POS_PRODUCT)
        ,RETURNS("20", ProductIndicator.POS_PRODUCT)
        ,DEPOSIT("21", ProductIndicator.ATM_PRODUCT)
        ,DEBIT_ADJUSTMENT("02", ProductIndicator.POS_PRODUCT)
        ,BALANCE_INQUIRY("31", ProductIndicator.TRANSACTION_PRODUCTS)
        ,CARDHOLDER_ACCOUNTS_TRANSFER("40", ProductIndicator.ATM_PRODUCT)
        ,REPLENISHMENT("60", ProductIndicator.POS_PRODUCT)
        ,FULL_REDEMPTION("61", ProductIndicator.POS_PRODUCT)
        ,CARD_ACTIVATION("72", ProductIndicator.POS_PRODUCT)
        ,MAIL_OR_PHONE_ORDER("80", ProductIndicator.POS_PRODUCT)
        ,MBNET_INSTALMENT("1U", ProductIndicator.POS_PRODUCT)
        ,MBNET_INSTALMENT_REVERSAL("2U", ProductIndicator.POS_PRODUCT)
        ,MBNET_ISPLATA_UZ_OBROCNO_TERECENJE("P0", ProductIndicator.ATM_PRODUCT)
        ,EGCP_AKTIVACIJA_KARTICE("P1", ProductIndicator.ATM_PRODUCT);

        private String code;
        private ProductIndicator[] productIndicators;

        private static final Map<String, ISOTransactionCode> codes;

        static {
            codes = Arrays.stream(ISOTransactionCode.values()).collect(Collectors.toMap(ISOTransactionCode::getCode, e -> e));
        }

        public String getCode() {
            return code;
        }

        ISOTransactionCode(String code, ProductIndicator[] productIndicators) {
            this.code = code;
            this.productIndicators = productIndicators;
        }

        public static ISOTransactionCode find(String code) {
            return codes.get(code);
        }
    }


    public enum AccountType {
        NO_ACCOUNT_SPECIFIED("00"), SAVINGS("10"), CHECKING("20"), CREDIT("30"), CREDIT_INSTALLMENTS("32"), OTHER("9M");

        private String code;

        private final static Map<String, AccountType> codes;

        static {
            codes = Arrays.stream(AccountType.values()).collect(Collectors.toMap(AccountType::getCode, e -> e));
        }

        AccountType(String code) {
            this.code = code;
        }


        public String getCode() {
            return code;
        }

        public static AccountType find(String code) {
            return codes.getOrDefault(code, AccountType.OTHER);
        }
    }


    public enum TransactionSettlementIndicator {
         PORAVNANJE_RAZMJENOM_PODATAKA("0")
        ,DIREKTNO_KNJITZENJE_BEZ_NAKNADNOG_PORAVNANJA("1")
        ,BROJ_RATA_3("A")
        ,BROJ_RATA_6("B")
        ,BROJ_RATA_9("C")
        ,BROJ_RATA_12("D");

        private final String code;

        private final static Map<String, TransactionSettlementIndicator> codes;

        static {
            codes = Arrays.stream(TransactionSettlementIndicator.values()).collect(Collectors.toMap(TransactionSettlementIndicator::getCode, e -> e));
        }

        TransactionSettlementIndicator(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static TransactionSettlementIndicator find(String code) {
            return codes.get(code);
        }
    }


    public abstract ProductIndicator forProduct();

    public abstract TransactionCode.ISOTransactionCode getISOTransactionCode();
}
