package hr.kaba.olb.responders.ora.service.proxy;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.detect.RequestDetector;
import hr.kaba.olb.protocol.detect.RequestType;
import hr.kaba.olb.responders.ora.service.HisoAnswer;
import hr.kaba.olb.responders.ora.service.proxy.resp.Procedures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class RequestRouter {

    private static final Logger logger = LoggerFactory.getLogger(RequestRouter.class);

    public static HisoAnswer forwardRequest(Connection connection, HISOMessage request, Long trxIdToRespondTo) throws SQLException {

        RequestType requestType = RequestDetector.determineType(request);

        logger.debug("Request type: {}", requestType);

        ConnectionResponder requestHandler = Procedures.handlerFor(requestType);

        return requestHandler.onConnection(connection)
                             .respondTo(trxIdToRespondTo);


    }


}
