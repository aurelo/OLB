package hr.kaba.olb.responders.ora;

import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.trx.Response;
import hr.kaba.olb.protocol.trx.HisoResponse;
import hr.kaba.olb.responders.ora.service.*;
import hr.kaba.olb.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.sql.*;
import java.time.LocalDateTime;

public class OraResponder implements TrxResponder {
    private final static Logger logger = LoggerFactory.getLogger(OraResponder.class);

    public final static Marker DB_MARKER = MarkerFactory.getMarker("DB");

    private final static InitiatorType HOST_RESPONDER = InitiatorType.HOST;

    private final DbSource dbSource;

    public OraResponder(DbSource dataSource) {
        this.dbSource = dataSource;
    }

    /**
     *
     * @param request
     * @return
     */

    @Override
    public HisoResponse respond(HISOMessage request) {

        logger.debug("Ora responder for message type: {}", request.getMessageType());

        try (Connection connection = dbSource.getConnection()) {

            if (request.getMessageType().isReject()) {
                RejectLogger.logReject(connection, request);
                return HisoResponse.NO_RESPONSE;
            }


            HisoDecod.LogRequestAnswer hisoRequestReturn = HisoDecod.logRequest(connection, request);

            MbuTrans.MbuTransAnswer mbuTransAnswer = MbuTrans.logRequest(connection, request, hisoRequestReturn.id());

            HisoAnswer hisoAnswer;
            String approvalCode = null;

            if (mbuTransAnswer.isDuplicate()) {
                hisoAnswer = MbuTrans.previousResponse(connection, mbuTransAnswer.id());
                approvalCode = MbuTrans.getApprovalCode(connection, mbuTransAnswer.id());
            } else {
                hisoAnswer = DbResponder.respond(connection, request, mbuTransAnswer.id());
                if (request.getMessageType() == MessageType.AUTHORIZATION_REQ || request.getMessageType() == MessageType.TRX_REQ) {
                    approvalCode = MbuTrans.assignApprovalCode(connection, mbuTransAnswer.id());
                }
            }

            Pair<HISOMessage, HisoResponse> responsePair = constructResponse(request, hisoAnswer, approvalCode);

            HISOMessage response = responsePair.getFirst();
            HisoResponse trxResponse = responsePair.getSecond();

            HisoDecod.logResponse(connection, response, mbuTransAnswer.id(), hisoRequestReturn.id());

            return trxResponse;


        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
            return new HisoResponse(ResponseCode.TRX_SYSTEM_MALFUNCTION, null, null, null);
        }


    }

    /**
     *
     * @param request request being answered to
     * @param answer response code with balances
     * @param approvalCode Authorisation Identification Response - used as host transaction non unique marker
     * @return Pair of response and answer to request
     */
    private Pair<HISOMessage, HisoResponse> constructResponse(HISOMessage request, HisoAnswer answer, String approvalCode) {
        String responseString = answer.responseCode();
        Integer ledgerBalance = answer.ledgerBalance();
        Integer availableBalance = answer.availableBalance();


        ResponseCode responseCode = ResponseCode.from(responseString, MessageType.responseFor(request.getMessageType()));
        HisoResponse trxResponse = new HisoResponse(responseCode, ledgerBalance, availableBalance, approvalCode);

        logger.debug("trx response: {}", trxResponse);

        HISOMessage response = new Response(HOST_RESPONDER).respond(request, trxResponse, Formatters.formatTransmissionDate(LocalDateTime.now()));

        return new Pair<>(response, trxResponse);
    }


}
