package hr.kaba.olb.responders.ora.service;

import hr.kaba.olb.codec.constants.ResponseCode;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.responders.ora.OraResponder;
import hr.kaba.olb.responders.ora.service.proxy.RequestRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service that determines request type on basis of request message and forwards it to appropriate handler
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class DbResponder {

    private static final Logger logger = LoggerFactory.getLogger(DbResponder.class);

    public static HisoAnswer respond(Connection connection, HISOMessage request, Long mbuTransAnswerId) {
        logger.debug("routing request");

        HisoAnswer answer = new HisoAnswer(ResponseCode.ADVICE_SYSTEM_MALFUNCTION.getCode(), null, null);

        try {

            answer = RequestRouter.forwardRequest(connection, request, mbuTransAnswerId);

        } catch (SQLException e) {
            logger.error(e.getMessage());
            logger.error(OraResponder.DB_MARKER, e.getMessage());
//            AppMailer.INSTANCE.sendError("OLB error from DB response", e.getMessage());
        }

        return answer;
    }


}
