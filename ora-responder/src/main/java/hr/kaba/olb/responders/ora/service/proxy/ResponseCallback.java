package hr.kaba.olb.responders.ora.service.proxy;

import hr.kaba.olb.responders.ora.service.HisoAnswer;

import java.sql.SQLException;

/**
 * Describes services that can respond to database transaction id
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
@FunctionalInterface
public interface ResponseCallback {
    HisoAnswer respondTo(Long trxIdToRespondTo) throws SQLException;
}
