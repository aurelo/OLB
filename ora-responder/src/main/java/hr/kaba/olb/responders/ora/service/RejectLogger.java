package hr.kaba.olb.responders.ora.service;

import hr.kaba.olb.codec.OLBCodec;
import hr.kaba.olb.codec.message.HISOMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Logs rejected message - message that informs sender that it's response was invalid
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class RejectLogger {

    private static final Logger logger = LoggerFactory.getLogger(RejectLogger.class);

    private final static String PRC_LOG_REJECTED_MESSAGE = "{call mbuintf.log_rejected_message(p_message => :p_message)}";

    public static void logReject(Connection connection, HISOMessage request) throws SQLException {

        PreparedStatement selectPreviousResponse = mapRejectLog(connection, OLBCodec.encode(request));

        selectPreviousResponse.executeQuery();

    }


    private static CallableStatement mapRejectLog(Connection connection, String rejectedMessage) throws SQLException {
        logger.debug("mapping rejected logger for message {}", rejectedMessage);

        CallableStatement callableStatement = connection.prepareCall(PRC_LOG_REJECTED_MESSAGE);

        callableStatement.setString("p_message", rejectedMessage);

        return callableStatement;
    }

}

