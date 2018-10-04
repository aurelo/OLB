package hr.kaba.olb.responders.ora.service.proxy;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.protocol.detect.RequestDetector;
import hr.kaba.olb.protocol.detect.RequestType;
import hr.kaba.olb.responders.ora.service.HisoAnswer;
import hr.kaba.olb.responders.ora.service.proxy.resp.Procedures;

import java.sql.Connection;
import java.sql.SQLException;

public class RequestRouter {

    public static HisoAnswer forwardRequest(Connection connection, HISOMessage request, Long trxIdToRespondTo) throws SQLException {

        RequestType requestType = RequestDetector.determineType(request);

        ConnectionResponder requestHandler = Procedures.handlerFor(requestType);

        return requestHandler.onConnection(connection)
                             .respondTo(trxIdToRespondTo);


    }


}
