package hr.kaba.olb.codec.constants;


import java.util.*;
import java.util.stream.Collectors;

public enum MessageType {

    NMM_REQ("0800", ProductIndicator.NMM_PRODUCT),
    NMM_RESP("0810", ProductIndicator.NMM_PRODUCT),
    NMM_REJECT("9xxx", ProductIndicator.NMM_PRODUCT),
    TRX_REQ("0200", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_RESP("0210", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_ADVICE("0220", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_ADVICE_REPEAT("0221", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_ADVICE_RESP("0230", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_REVERSAL("0420", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_REVERSAL_REPEAT("0421", ProductIndicator.TRANSACTION_PRODUCTS),
    TRX_REVERSAL_RESP("0430", ProductIndicator.TRANSACTION_PRODUCTS),
    AUTHORIZATION_REQ("0100", ProductIndicator.POS_PRODUCT),
    AUTHORIZATION_RESP("0110", ProductIndicator.POS_PRODUCT),
    AUTHORIZATION_ADVICE("0120", ProductIndicator.POS_PRODUCT),
    AUTHORIZATION_ADVICE_REPEAT("0121", ProductIndicator.POS_PRODUCT),
    AUTHORIZATION_ADVICE_RESP("0130", ProductIndicator.POS_PRODUCT),
    FHM_REQ("0300", ProductIndicator.FHM_PRODUCT),
    FHM_RESP("0310", ProductIndicator.FHM_PRODUCT)
    ;

    private final String code;
    private final ProductIndicator[] productInicators;



    private final static Map<String, MessageType> codes;

    // what message type is response for message requests
    private final static Map<MessageType, MessageType> responses;


    private final static List<MessageType> REQUEST_MESSAGE_TYPES = Arrays.asList(TRX_REQ, TRX_RESP);
    private final static List<MessageType> REVERSALS_MESSAGE_TYPES = Arrays.asList(TRX_REVERSAL, TRX_REVERSAL_REPEAT, TRX_REVERSAL_RESP);
    private final static List<MessageType> ADVICE_MESSAGE_TYPES = Arrays.asList(TRX_ADVICE, TRX_ADVICE_RESP, TRX_ADVICE_REPEAT);
    private final static List<MessageType> AUTHORIZATION_REQUEST_MESSAGE_TYPES = Arrays.asList(AUTHORIZATION_REQ, AUTHORIZATION_RESP);
    private final static List<MessageType> AUTHORIZATION_ADVICE_MESSAGE_TYPES = Arrays.asList(AUTHORIZATION_ADVICE, AUTHORIZATION_ADVICE_RESP, AUTHORIZATION_ADVICE_REPEAT);


    static
    {
        codes = Arrays.stream(MessageType.values()).collect(Collectors.toMap(MessageType::getCode, e -> e));


        responses = new HashMap<>();
        responses.put(MessageType.NMM_REQ, MessageType.NMM_RESP);

        responses.put(MessageType.TRX_REQ, MessageType.TRX_RESP);
        responses.put(MessageType.TRX_ADVICE, MessageType.TRX_ADVICE_RESP);
        responses.put(MessageType.TRX_ADVICE_REPEAT, MessageType.TRX_ADVICE_RESP);
        responses.put(MessageType.TRX_REVERSAL, MessageType.TRX_REVERSAL_RESP);
        responses.put(MessageType.TRX_REVERSAL_REPEAT, MessageType.TRX_REVERSAL_RESP);
        responses.put(MessageType.AUTHORIZATION_REQ, MessageType.AUTHORIZATION_ADVICE_RESP);
        responses.put(MessageType.AUTHORIZATION_ADVICE, MessageType.AUTHORIZATION_ADVICE_RESP);
        responses.put(MessageType.AUTHORIZATION_ADVICE_REPEAT, MessageType.AUTHORIZATION_ADVICE_RESP);

        responses.put(MessageType.FHM_REQ, MessageType.FHM_RESP);

    }

    MessageType(String code, ProductIndicator[] productInicators) {
        this.code = code;
        this.productInicators = productInicators;
    }

    public static MessageType from(String code) {
        return codes.get(code);
    }

    public String getCode() {
        return code;
    }

    public ProductIndicator[] getProductInicators() {
        return productInicators;
    }

    public static final MessageType[] RESPONSES = {TRX_RESP, AUTHORIZATION_RESP, TRX_ADVICE_RESP, AUTHORIZATION_ADVICE_RESP};
    public static final MessageType[] REVERSALS = {TRX_REVERSAL_RESP};
    public static final MessageType[] ADVICES = {AUTHORIZATION_ADVICE_RESP};
    public static final MessageType[] NMM_RESPONSES = {NMM_RESP};


    public static MessageType responseFor(MessageType originalMessageType) {
        MessageType responseType = responses.get(originalMessageType);
        return (responseType == null) ? originalMessageType : responseType;
    }

    public boolean isRequest() {
        return REQUEST_MESSAGE_TYPES.contains(this);
    }

    public boolean isReversal() {
        return REVERSALS_MESSAGE_TYPES.contains(this);
    }

    public boolean isAdvice() {
        return ADVICE_MESSAGE_TYPES.contains(this);
    }

    public boolean isAuthorizationRequest() {
        return AUTHORIZATION_REQUEST_MESSAGE_TYPES.contains(this);
    }

    public boolean isAuthorizationAdvice() {
        return AUTHORIZATION_ADVICE_MESSAGE_TYPES.contains(this);
    }


    @Override
    public String toString() {
        return String.format("%s - %s", name(), getCode());
    }
}