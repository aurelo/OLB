package hr.kaba.olb.responders.ora.service.proxy;

import hr.kaba.olb.responders.ora.service.HisoAnswer;

import java.sql.SQLException;

@FunctionalInterface
public interface ResponseCallback {
    HisoAnswer respondTo(Long trxIdToRespondTo) throws SQLException;
}
