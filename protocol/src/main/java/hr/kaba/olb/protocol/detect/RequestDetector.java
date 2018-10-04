package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.field.TransactionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDetector {
    private final static Logger logger = LoggerFactory.getLogger(RequestDetector.class);

    public static RequestType determineType(HISOMessage request) {

        RequestType requestType = RequestType.UNKNOWN;

        if (request.getHeader().isATM()) {
            requestType = dispatchAtm(request);
        } else if (request.getHeader().isPOS())
            requestType = dispatchPos(request);

        return requestType;
    }

    private static RequestType dispatchAtm(HISOMessage request) {
        RequestType requestType = RequestType.UNKNOWN;

        MessageType messageType = request.getMessageType();
        logger.debug("dispatching for ATM - message type: {}", messageType);

        if (request.getISOTransactionalCode().get() == TransactionCode.ISOTransactionCode.BALANCE_INQUIRY) {
            requestType = RequestType.ATM_BALANCE_INQUIRY;
        } else if (messageType.isRequest()) {
            requestType = RequestType.ATM_WITHDRAWAL_REQUEST;
        } else if (messageType.isAdvice()) {
            requestType = RequestType.ATM_WITHDRAWAL_ADVICE;
        } else if (messageType.isReversal()) {
            requestType = RequestType.ATM_REVERSAL;
        }

        return requestType;
    }

    private static RequestType dispatchPos(HISOMessage request) {
        RequestType requestType = RequestType.UNKNOWN;

        MessageType messageType = request.getMessageType();
        logger.debug("dispatching for POS - message type: {} - {}", messageType, messageType.getCode());

        TransactionCode.ISOTransactionCode isoTransactionCode;

        if (!request.getISOTransactionalCode().isPresent()) {
            return requestType;
        } else {
            isoTransactionCode = request.getISOTransactionalCode().get();
            logger.debug("transaction code: {} - {}", isoTransactionCode, isoTransactionCode.getCode());
        }


        // REQUESTS
        if (messageType.isRequest()) {

            if (isoTransactionCode == TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES) {
                requestType = RequestType.POS_PURCHASE_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL) {
                requestType = RequestType.POS_WITHDRAWAL_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL_ADJUSTMENT_POS) {
                requestType = RequestType.POS_WITHDRAWAL_ADJUSTMENT_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT) {
                requestType = RequestType.POS_INSTALLMENT_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT_REVERSAL) {
                requestType = RequestType.POS_INSTALLMENT_CANCELLATION_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.RETURNS) {
                requestType = RequestType.POS_RETURN_REQUEST;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MAIL_OR_PHONE_ORDER) {
                requestType = RequestType.POS_ONLINE_REQUEST;
            }

        }
        // ADVICES
        else if (messageType.isAdvice()) {
            if (isoTransactionCode == TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES) {
                requestType = RequestType.POS_PURCHASE_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL) {
                requestType = RequestType.POS_WITHDRAWAL_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL_ADJUSTMENT_POS) {
                requestType = RequestType.POS_WITHDRAWAL_ADJUSTMENT_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT) {
                requestType = RequestType.POS_INSTALLMENT_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.CREDIT_ADJUSTMENT) {
                requestType = RequestType.POS_PURCHASE_ADJUSTMENT_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT_REVERSAL) {
                requestType = RequestType.POS_INSTALLMENT_CANCELLATION_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.RETURNS) {
                requestType = RequestType.POS_RETURN_ADVICE;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MAIL_OR_PHONE_ORDER) {
                requestType = RequestType.POS_ONLINE_ADVICE;
            }
            // REVERSALS
        } else if (messageType.isReversal()) {
            if (isoTransactionCode == TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES) {
                requestType = RequestType.POS_PURCHASE_REVERSAL;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL) {
                requestType = RequestType.POS_WITHDRAWAL_REVERSAL;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT) {
                requestType = RequestType.POS_INSTALLMENT_REVERSAL;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MBNET_INSTALMENT_REVERSAL) {
                requestType = RequestType.POS_INSTALLMENT_CANCELLATION_REVERSAL;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.RETURNS) {
                requestType = RequestType.POS_RETURN_REVERSAL;
            } else if (isoTransactionCode == TransactionCode.ISOTransactionCode.MAIL_OR_PHONE_ORDER) {
                requestType = RequestType.POS_ONLINE_REVERSAL;
            }


        } else if (messageType.isAuthorizationRequest()) {
            if (isoTransactionCode == TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES) {
                requestType = RequestType.POS_AUTHORIZATION_REQUEST;
            }
        } else if (messageType.isAuthorizationAdvice()) {
            if (isoTransactionCode == TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES) {
                requestType = RequestType.POS_AUTHORIZATION_ADVICE;
            }
        }

        return requestType;
    }

}