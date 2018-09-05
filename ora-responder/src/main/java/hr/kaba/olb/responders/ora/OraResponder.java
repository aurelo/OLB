package hr.kaba.olb.responders.ora;

import hr.kaba.olb.codec.constants.Formatters;
import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.MessageType;
import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.TrxResponder;
import hr.kaba.olb.protocol.trx.Response;
import hr.kaba.olb.protocol.trx.TrxResponse;
import hr.kaba.olb.responders.ora.sql.OraRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

public class OraResponder implements TrxResponder {
    private final static Logger logger = LoggerFactory.getLogger(OraResponder.class);

    private final static InitiatorType HOST_RESPONDER = InitiatorType.HOST;

    private final DbSource dbSource;

    public OraResponder(DbSource dataSource) {
        this.dbSource = dataSource;
    }

    @Override
    public TrxResponse respond(HISOMessage request) {

        try (Connection connection = dbSource.getConnection()) {

//            connection.setAutoCommit(false);

            CallableStatement insertHisoDecodCS = OraRequestMapper.insert_hiso_decod(connection, request);

            insertHisoDecodCS.execute();

            Long hisoDecodId = insertHisoDecodCS.getLong("p_iid");
            Integer isHisoDecodDuplicate = insertHisoDecodCS.getInt("p_iduplicate");

            logger.debug("insertHisoDecodCS return id: {}, isDuplicate: {}", hisoDecodId, isHisoDecodDuplicate);

            insertHisoDecodCS.close();


            logger.debug("calling insert mbu trans");

            CallableStatement insertMbuTransCS = OraRequestMapper.insert_mbu_trans(connection, hisoDecodId, request.getMessageType().getCode());
            insertMbuTransCS.execute();

            logger.debug("after executing insert mbu trans");

            Long mbuTransId = insertMbuTransCS.getLong("p_iTRS_ID");
            Integer isMbuTransDuplicate = insertMbuTransCS.getInt("p_iDuplicate");
            String mbuTransStatus = insertMbuTransCS.getString("p_iTrsStatus");

            logger.debug("inserted mbu hiso decod return id: {}, isDuplicate: {}, status: {}", mbuTransId, isMbuTransDuplicate, mbuTransStatus);

//            connection.commit();

            insertMbuTransCS.close();


            String responseString;
            Integer ledgerBalance;
            Integer availableBalance;

            if (isMbuTransDuplicate == 0) {

                logger.debug("Transaction is not duplicate - calling positive responder");

                CallableStatement positiveResponderCS = OraRequestMapper.positive_responder(connection, mbuTransId);
                positiveResponderCS.execute();

                responseString = positiveResponderCS.getString("p_rsp_code");
                ledgerBalance = positiveResponderCS.getInt("p_ledger_balance");
                availableBalance = positiveResponderCS.getInt("p_available_balance");

                positiveResponderCS.close();

            } else {

                logger.debug("transaction is duplicate - returning previous answer for trans id: {}", mbuTransId);

                PreparedStatement selectPreviousResponse = OraRequestMapper.previous_response(connection, mbuTransId);

                ResultSet previousResponseResultSet = selectPreviousResponse.executeQuery();

                previousResponseResultSet.next();

                responseString = previousResponseResultSet.getString("rsp_code");
                ledgerBalance = previousResponseResultSet.getInt("ledger_balance");
                availableBalance = previousResponseResultSet.getInt("available_balance");

            }

            TrxResponse trxResponse = new TrxResponse(ResponseCode.from(responseString, MessageType.responseFor(request.getMessageType())), ledgerBalance, availableBalance);


            HISOMessage response = new Response(HOST_RESPONDER).respond(request, trxResponse, Formatters.formatTransmissionDate(LocalDateTime.now()));

            CallableStatement insertHisoRespCS = OraRequestMapper.insert_hiso_resp(connection, response, mbuTransId, hisoDecodId);
            insertHisoRespCS.execute();

//            connection.commit();

            insertHisoRespCS.close();

            return trxResponse;


        } catch (SQLException e) {
            e.printStackTrace();
            return new TrxResponse(ResponseCode.TRX_SYSTEM_MALFUNCTION, null, null);
        }


    }


}
