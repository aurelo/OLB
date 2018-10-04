package hr.kaba.olb.responders.ora.service.proxy.resp;

import hr.kaba.olb.responders.ora.service.HisoAnswer;
import hr.kaba.olb.responders.ora.service.proxy.ConnectionResponder;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class SimpleResponder extends ConnectionResponder {

    private final String sql;

    public SimpleResponder(String sql) {
        this.sql = sql;
    }

    @Override
    public HisoAnswer respondTo(Long trxIdToRespondTo) throws SQLException {
        Objects.requireNonNull(connection);

        CallableStatement callableStatement = connection.prepareCall(sql);

        callableStatement.setLong("p_id_trs", trxIdToRespondTo);
        callableStatement.registerOutParameter("p_rsp_code", Types.VARCHAR);

        callableStatement.execute();

        String p_rsp_code = callableStatement.getString("p_rsp_code");

        callableStatement.close();

        return new HisoAnswer(p_rsp_code, null, null);
    }
}
