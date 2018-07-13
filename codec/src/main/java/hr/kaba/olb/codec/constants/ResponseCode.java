package hr.kaba.olb.codec.constants;

import java.util.*;
import java.util.stream.Collectors;

public enum ResponseCode {
     NMM_APPROVED("00", MessageType.NMM_RESPONSES)
    ,NMM_REJECTED("05", MessageType.NMM_RESPONSES)
    ,NMM_DPC_DOWN("91", MessageType.NMM_RESPONSES)

    ,TRX_APPROVED("00", MessageType.RESPONSES)
    ,TRX_TRANSACTION_NOT_PERMITTED_TO_CARDHOLDER("57", MessageType.RESPONSES)
    ,TRX_RESTRICTED_CARD("62", MessageType.RESPONSES)
    ,TRX_EXPIRED_CARD("54", MessageType.RESPONSES)
    ,TRX_INVALID_CARD_NUMBER("14", MessageType.RESPONSES)
    ,TRX_NO_CARD_RECORD("56", MessageType.RESPONSES)
    ,TRX_INVALID_TRANSACTION("12", MessageType.RESPONSES)
    ,TRX_NO_CREDIT_ACCOUNT("39", MessageType.RESPONSES)
    ,TRX_TRANSACTION_NOT_PERMITTED_TO_TERMINAL("58", MessageType.RESPONSES)
    ,TRX_NOT_SUFFICIENT_FUNDS("51", MessageType.RESPONSES)
    ,TRX_EXCEEDS_WITHDRAWAL_FREQUENCY_LIMIT("65", MessageType.RESPONSES)
    ,TRX_EXCEEDS_WITHDRAWAL_AMOUNT_LIMIT("61", MessageType.RESPONSES)
    ,TRX_DO_NOT_HONOR("05", MessageType.RESPONSES)
    ,TRX_DUPLICATE_TRANSMISSION("94", MessageType.RESPONSES)
    ,TRX_SYSTEM_MALFUNCTION("96", MessageType.RESPONSES)
    ,TRX_ISSUER_OR_SWITCH_IS_INOPERATIVE("91", MessageType.RESPONSES)
    ,TRX_FORMAT_ERROR("30", MessageType.RESPONSES)
    ,TRX_INVALID_AMOUNT("13", MessageType.RESPONSES)
    ,TRX_STOLEN_CARD_CAPTURE_CARD("43", MessageType.RESPONSES)
    ,TRX_EXPIRED_CARD_CAPTURE_CARD("33", MessageType.RESPONSES)
    ,TRX_PICK_UP_CAPTURE_CARD("04", MessageType.RESPONSES)

    ,REVERSAL_APPROVED("00", MessageType.REVERSALS)
    ,REVERSAL_CUSTOMER_CANCELLATION("17", MessageType.REVERSALS)
    ,REVERSAL_INVALID_RESPONSE("20", MessageType.REVERSALS)
    ,REVERSAL_NO_ACTION_TAKEN("21", MessageType.REVERSALS)
    ,REVERSAL_SUSPECTED_MALFUNCTION("22", MessageType.REVERSALS)
    ,REVERSAL_COMPLETED_PARTIALLY("32", MessageType.REVERSALS)
    ,REVERSAL_REQUESTED_FUNCTION_NOT_SUPPORTED("40", MessageType.REVERSALS)
    ,REVERSAL_RESPONSE_RECEIVED_TOO_LATE("68", MessageType.REVERSALS)
    ,REVERSAL_RESERVED_FOR_PRIVATE_USE("82", MessageType.REVERSALS)
    ,REVERSAL_SYSTEM_MALFUNCTION("96", MessageType.REVERSALS)
    ,REVERSAL_DESTINATION_NOT_AVAILABLE("R9", MessageType.REVERSALS)
    ,REVERSAL_SUSPECT_REVERSAL("S0", MessageType.REVERSALS)
    ,REVERSAL_MAC_FAILURE("U1", MessageType.REVERSALS)
    ,REVERSAL_KMAC_SYNCHRONIZATION_ERROR("U2", MessageType.REVERSALS)
    ,REVERSAL_MESSAGE_REPLAY_ERROR("U3", MessageType.REVERSALS)
    ,REVERSAL_INVALID_MAC("U4", MessageType.REVERSALS)
    ,REVERSAL_KME_SYNCHRONIZATION_ERROR("U5", MessageType.REVERSALS)

    ,ADVICE_APPROVED("00", MessageType.ADVICES)
    ,ADVICE_SUSPECTED_MALFUNCTION("22", MessageType.ADVICES)
    ,ADVICE_ORIGINAL_AMOUNT_INCORRECT("64", MessageType.ADVICES)
    ,ADVICE_SUSPICIOUS_REVERSAL("83", MessageType.ADVICES)
    ,ADVICE_MISDISPENSE_REVERSAL("84", MessageType.ADVICES)
    ,ADVICE_PLUS_ADD_CASH_WITHDRAWAL("85", MessageType.ADVICES)
    ,ADVICE_DUPLICATE_TRANSMISSION("94", MessageType.ADVICES)
    ,ADVICE_RECONCILE_ERROR("95", MessageType.ADVICES)
    ,ADVICE_SYSTEM_MALFUNCTION("96", MessageType.ADVICES)
    ,ADVICE_SUSPICIOUS_REVERSAL_OVERRIDE("S1", MessageType.ADVICES)
    ,ADVICE_MISDISPENSE_REV_OVERRIDE("S2", MessageType.ADVICES)
    ,ADVICE_PLUS_ADD_CASH_WDR_OR_ADVANCE("S3", MessageType.ADVICES)
    ;

     private final String code;
     private final MessageType[] validForMessages;



    ResponseCode(String code, MessageType[] validForMessages) {
        this.code = code;
        this.validForMessages = validForMessages;
    }

    public String getCode() {
        return code;
    }

    public MessageType[] getValidForMessages() {
        return validForMessages;
    }

    public static List<ResponseCode> validResponseCodes(MessageType messageType) {
        return Arrays.stream(ResponseCode.values()).filter(e -> Arrays.asList(e.getValidForMessages()).contains(messageType)).collect(Collectors.toList());
    }

    public static boolean isValidResponseForMessage(ResponseCode code, MessageType messageType) {
        return validResponseCodes(messageType).contains(code);
    }

    public boolean isValidFor(MessageType messageType) {
        return Arrays.asList(getValidForMessages()).contains(messageType);
    }
}
