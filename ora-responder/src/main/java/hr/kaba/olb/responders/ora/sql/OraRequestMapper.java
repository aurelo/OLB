package hr.kaba.olb.responders.ora.sql;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class OraRequestMapper {

    private final static Logger logger = LoggerFactory.getLogger(OraRequestMapper.class);


    public static CallableStatement insert_hiso_decod(Connection connection, HISOMessage request) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(PlSqlCalls.PRC_MBU_INSERT_HISO_DECOD);

        callableStatement.setString("p_protocol", request.getProductType().getCode());
        callableStatement.setString("p_ret_status", "000");
        callableStatement.setString("p_orig_code", request.getHeader().getOriginatorCode().getCode());
        callableStatement.setString("p_rsp_code", request.getHeader().getResponderCode().getCode());
        callableStatement.setString("p_msg_typ", request.getMessageType().getCode());
        callableStatement.setString("p_bitmap1", request.getPrimaryBitmap());

        callableStatement.setString("p_dat_p1", request.getFields().get(PrimaryBitmapField.P1));
        callableStatement.setString("p_dat_p3", request.getFields().get(PrimaryBitmapField.P3));
        callableStatement.setString("p_dat_p4", request.getFields().get(PrimaryBitmapField.P4));
        callableStatement.setString("p_dat_p7", request.getFields().get(PrimaryBitmapField.P7));
        callableStatement.setString("p_dat_p11", request.getFields().get(PrimaryBitmapField.P11));
        callableStatement.setString("p_dat_p12", request.getFields().get(PrimaryBitmapField.P12));
        callableStatement.setString("p_dat_p13", request.getFields().get(PrimaryBitmapField.P13));
        callableStatement.setString("p_dat_p17", request.getFields().get(PrimaryBitmapField.P17));
        callableStatement.setString("p_dat_p23", request.getFields().get(PrimaryBitmapField.P23));
        callableStatement.setString("p_dat_p25", request.getFields().get(PrimaryBitmapField.P25));
        callableStatement.setString("p_dat_p32", request.getFields().get(PrimaryBitmapField.P32));
        callableStatement.setString("p_dat_p35", request.getFields().get(PrimaryBitmapField.P35));
        callableStatement.setString("p_dat_p37", request.getFields().get(PrimaryBitmapField.P37));
        callableStatement.setString("p_dat_p38", request.getFields().get(PrimaryBitmapField.P38));
        callableStatement.setString("p_dat_p39", request.getFields().get(PrimaryBitmapField.P39));
        callableStatement.setString("p_dat_p41", request.getFields().get(PrimaryBitmapField.P41));
        callableStatement.setString("p_dat_p43", request.getFields().get(PrimaryBitmapField.P43));
        callableStatement.setString("p_dat_p44a", request.getFields().get(PrimaryBitmapField.P44A));
        callableStatement.setString("p_dat_p44p", request.getFields().get(PrimaryBitmapField.P44P));
        callableStatement.setString("p_dat_p49", request.getFields().get(PrimaryBitmapField.P49));
        callableStatement.setString("p_dat_p60a", request.getFields().get(PrimaryBitmapField.P60A));
        callableStatement.setString("p_dat_p60p", request.getFields().get(PrimaryBitmapField.P60P));
        callableStatement.setString("p_dat_p61a", request.getFields().get(PrimaryBitmapField.P61A));
        callableStatement.setString("p_dat_p61p", request.getFields().get(PrimaryBitmapField.P61P));
        callableStatement.setString("p_dat_s90", request.getFields().get(SecondaryBitmapField.S90));
        callableStatement.setString("p_dat_s95", request.getFields().get(SecondaryBitmapField.S95));
        callableStatement.setString("p_dat_s100", request.getFields().get(SecondaryBitmapField.S100));
        callableStatement.setString("p_dat_s126", request.getFields().get(SecondaryBitmapField.S126P));
        callableStatement.setString("p_dat_s127", request.getFields().get(SecondaryBitmapField.S127P));

        callableStatement.registerOutParameter("p_iid", Types.NUMERIC);
        callableStatement.registerOutParameter("p_iduplicate", Types.NUMERIC);

        return callableStatement;
    }

    public static CallableStatement insert_mbu_trans(Connection connection, Long hisoId, String msgType) throws SQLException {

        logger.debug("mapping insert_mbu_trans hisoId: {}, msgType: {}", hisoId, msgType);

        CallableStatement callableStatement = connection.prepareCall(PlSqlCalls.PRC_MBU_INSERT_TRS);

        callableStatement.setLong("p_iHisoId", hisoId);
        callableStatement.setString("p_TRS_TYPE", msgType);

        callableStatement.registerOutParameter("p_iTRS_ID", Types.NUMERIC);
        callableStatement.registerOutParameter("p_iDuplicate", Types.INTEGER);
        callableStatement.registerOutParameter("p_iTrsStatus", Types.VARCHAR);

        return callableStatement;
    }

    public static CallableStatement insert_hiso_resp(Connection connection, HISOMessage response, long mbuTransId, long hisoRequestId) throws SQLException {
        logger.debug("mapping insert_hiso_resp - P44A: {}", response.getFields().get(PrimaryBitmapField.P44A));

        CallableStatement callableStatement = connection.prepareCall(PlSqlCalls.PRC_MBU_INSERT_HISO_RSP);

        callableStatement.setString("p_protocol", response.getProductType().getCode());
        callableStatement.setString("p_ret_status", "000");
        callableStatement.setString("p_orig_code", response.getHeader().getOriginatorCode().getCode());
        callableStatement.setString("p_rsp_code", response.getHeader().getResponderCode().getCode());
        callableStatement.setString("p_msg_typ", response.getMessageType().getCode());
        callableStatement.setString("p_bitmap1", response.getPrimaryBitmap());

        callableStatement.setString("p_dat_p1", response.getFields().get(PrimaryBitmapField.P1));
        callableStatement.setString("p_dat_p3", response.getFields().get(PrimaryBitmapField.P3));
        callableStatement.setString("p_dat_p4", response.getFields().get(PrimaryBitmapField.P4));
        callableStatement.setString("p_dat_p7", response.getFields().get(PrimaryBitmapField.P7));
        callableStatement.setString("p_dat_p11", response.getFields().get(PrimaryBitmapField.P11));
        callableStatement.setString("p_dat_p12", response.getFields().get(PrimaryBitmapField.P12));
        callableStatement.setString("p_dat_p13", response.getFields().get(PrimaryBitmapField.P13));
        callableStatement.setString("p_dat_p17", response.getFields().get(PrimaryBitmapField.P17));
        callableStatement.setString("p_dat_p23", response.getFields().get(PrimaryBitmapField.P23));
        callableStatement.setString("p_dat_p25", response.getFields().get(PrimaryBitmapField.P25));
        callableStatement.setString("p_dat_p32", response.getFields().get(PrimaryBitmapField.P32));
        callableStatement.setString("p_dat_p35", response.getFields().get(PrimaryBitmapField.P35));
        callableStatement.setString("p_dat_p37", response.getFields().get(PrimaryBitmapField.P37));
        callableStatement.setString("p_dat_p38", response.getFields().get(PrimaryBitmapField.P38));
        callableStatement.setString("p_dat_p39", response.getFields().get(PrimaryBitmapField.P39));
        callableStatement.setString("p_dat_p41", response.getFields().get(PrimaryBitmapField.P41));
        callableStatement.setString("p_dat_p43", response.getFields().get(PrimaryBitmapField.P43));
        callableStatement.setString("p_dat_p44a", response.getFields().get(PrimaryBitmapField.P44A));
        callableStatement.setString("p_dat_p44p", response.getFields().get(PrimaryBitmapField.P44P));
        callableStatement.setString("p_dat_p49", response.getFields().get(PrimaryBitmapField.P49));
        callableStatement.setString("p_dat_p60a", response.getFields().get(PrimaryBitmapField.P60A));
        callableStatement.setString("p_dat_p60p", response.getFields().get(PrimaryBitmapField.P60P));
        callableStatement.setString("p_dat_p61a", response.getFields().get(PrimaryBitmapField.P61A));
        callableStatement.setString("p_dat_p61p", response.getFields().get(PrimaryBitmapField.P61P));
        callableStatement.setString("p_dat_s90", response.getFields().get(SecondaryBitmapField.S90));
        callableStatement.setString("p_dat_s95", response.getFields().get(SecondaryBitmapField.S95));
        callableStatement.setString("p_dat_s100", response.getFields().get(SecondaryBitmapField.S100));
        callableStatement.setString("p_dat_s126", response.getFields().get(SecondaryBitmapField.S126P));
        callableStatement.setString("p_dat_s127", response.getFields().get(SecondaryBitmapField.S127P));

        callableStatement.setLong("p_itrsid", mbuTransId);
        callableStatement.setLong("p_ihisoreqid", hisoRequestId);

        return callableStatement;

    }
    ;


    public static CallableStatement positive_responder(Connection connection, long mbuTransId) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(PlSqlCalls.PRC_MBU_LOG_ALL);

        callableStatement.setLong("p_id_trs", mbuTransId);

        callableStatement.registerOutParameter("p_rsp_code", Types.VARCHAR);
        callableStatement.registerOutParameter("p_ledger_balance", Types.INTEGER);
        callableStatement.registerOutParameter("p_available_balance", Types.INTEGER);

        return callableStatement;

    }

    public static PreparedStatement previous_response(Connection connection, long mbuTransId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(PlSqlCalls.SELECT_TRX_RESPONSE_FIELDS);

        statement.setLong(1, mbuTransId);

        return statement;
    }


}
