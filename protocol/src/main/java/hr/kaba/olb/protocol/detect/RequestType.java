package hr.kaba.olb.protocol.detect;


/**
 * All types of possible request types
 * Determined on basis of message type and message code combination
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public enum RequestType {
    UNKNOWN

    ,REJECT

    , ATM_WITHDRAWAL_REQUEST
    , ATM_WITHDRAWAL_ADVICE
    , ATM_BALANCE_INQUIRY
    , ATM_REVERSAL
    , ATM_DEPOSIT

    , POS_PURCHASE_REQUEST
    , POS_WITHDRAWAL_REQUEST
    , POS_WITHDRAWAL_ADJUSTMENT_REQUEST
    , POS_INSTALLMENT_REQUEST
    , POS_INSTALLMENT_CANCELLATION_REQUEST
    , POS_RETURN_REQUEST
    , POS_ONLINE_REQUEST

    , POS_PURCHASE_ADVICE
    , POS_WITHDRAWAL_ADVICE
    , POS_WITHDRAWAL_ADJUSTMENT_ADVICE
    , POS_INSTALLMENT_ADVICE
    , POS_PURCHASE_ADJUSTMENT_ADVICE
    , POS_INSTALLMENT_CANCELLATION_ADVICE
    , POS_ONLINE_ADVICE
    , POS_RETURN_ADVICE

    , POS_PURCHASE_REVERSAL
    , POS_WITHDRAWAL_REVERSAL
    , POS_INSTALLMENT_REVERSAL
    , POS_INSTALLMENT_CANCELLATION_REVERSAL
    , POS_RETURN_REVERSAL
    , POS_ONLINE_REVERSAL

    , POS_AUTHORIZATION_REQUEST
    , POS_AUTHORIZATION_ADVICE
    ;
}