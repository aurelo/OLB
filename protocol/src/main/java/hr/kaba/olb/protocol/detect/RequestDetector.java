package hr.kaba.olb.protocol.detect;

import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.field.TransactionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service capable of determining request type from message
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
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

        TransactionCode.ISOTransactionCode transactionCode;

        if (!request.getISOTransactionalCode().isPresent()) {
            return requestType;
        } else {
            transactionCode = request.getISOTransactionalCode().get();
        }

        // ATM REQUESTS
        if (messageType.isRequest()) {
            if (transactionCode == TransactionCode.ISOTransactionCode.BALANCE_INQUIRY){
                requestType = RequestType.ATM_BALANCE_INQUIRY;
            } else if (transactionCode == TransactionCode.ISOTransactionCode.DEPOSIT) {
                requestType = RequestType.ATM_DEPOSIT;
            } else if (transactionCode == TransactionCode.ISOTransactionCode.WITHDRAWAL) {
                requestType = RequestType.ATM_WITHDRAWAL_REQUEST;
            }

        }

        if (messageType.isAdvice()) {
            requestType = RequestType.ATM_WITHDRAWAL_ADVICE;
        }

        if (messageType.isReversal()) {
            requestType = RequestType.ATM_REVERSAL;
        }

        if (messageType.isReject()) {
            requestType = RequestType.REJECT;
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
        } else if (messageType.isReject()) {
            requestType = RequestType.REJECT;
        }

        return requestType;
    }

}