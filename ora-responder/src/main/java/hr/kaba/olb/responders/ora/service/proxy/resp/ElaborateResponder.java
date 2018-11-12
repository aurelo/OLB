package hr.kaba.olb.responders.ora.service.proxy.resp;

import hr.kaba.olb.responders.ora.service.HisoAnswer;
import hr.kaba.olb.responders.ora.service.proxy.ConnectionResponder;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

/**
 * Service that responds to request with response code and account balances
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class ElaborateResponder extends ConnectionResponder {

    private final String sql;

    ElaborateResponder(String sql) {
        this.sql = sql;
    }

    @Override
    public HisoAnswer respondTo(Long trxIdToRespondTo) throws SQLException {
        Objects.requireNonNull(connection);

        CallableStatement callableStatement = connection.prepareCall(sql);

        callableStatement.setLong("p_id_trs", trxIdToRespondTo);

        callableStatement.registerOutParameter("p_add_rsp_ui", Types.INTEGER);
        callableStatement.registerOutParameter("p_available_balance", Types.INTEGER);
        callableStatement.registerOutParameter("p_ledger_balance", Types.INTEGER);
        callableStatement.registerOutParameter("p_rsp_code", Types.VARCHAR);

        callableStatement.execute();

        Integer p_add_rsp_ui = callableStatement.getInt("p_add_rsp_ui");
        Integer p_available_balance = callableStatement.getInt("p_available_balance");
        Integer p_ledger_balance = callableStatement.getInt("p_ledger_balance");
        String p_rsp_code = callableStatement.getString("p_rsp_code");

        callableStatement.close();

        return new HisoAnswer(p_rsp_code, p_ledger_balance, p_available_balance);

    }
}
