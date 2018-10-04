package hr.kaba.olb.responders.ora.service;

import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class HisoDecod {

    private final static Logger logger = LoggerFactory.getLogger(HisoDecod.class);

    public static class LogRequestAnswer {
        private final Long id;
        private final Integer isDuplicate;

        public LogRequestAnswer(Long id, Integer isDuplicate) {
            this.id = id;
            this.isDuplicate = isDuplicate;
        }

        public Long id() {
            return id;
        }

        public Integer isDuplicate() {
            return isDuplicate;
        }
    }

    /**
     *
     * @param connection
     * @param request
     * @return
     * @throws SQLException
     */
    public static LogRequestAnswer logRequest(Connection connection, HISOMessage request) throws SQLException {

        CallableStatement insertHisoDecodCS = mapHisoRequest(connection, request);

        insertHisoDecodCS.execute();

        Long hisoDecodId = insertHisoDecodCS.getLong("p_iid");
        Integer isHisoDecodDuplicate = insertHisoDecodCS.getInt("p_iduplicate");

        logger.debug("insertHisoDecodCS return id: {}, isDuplicate: {}", hisoDecodId, isHisoDecodDuplicate);

        insertHisoDecodCS.close();

        return new LogRequestAnswer(hisoDecodId, isHisoDecodDuplicate);

    }

    /**
     *
     * @param connection
     * @param response
     * @param mbuTransId
     * @param mbuHisoRequestId
     * @throws SQLException
     */
    public static void logResponse(Connection connection, HISOMessage response, Long mbuTransId, Long mbuHisoRequestId) throws SQLException {

        CallableStatement insertHisoRespCS = mapHisoResponse(connection, response, mbuTransId, mbuHisoRequestId);
        insertHisoRespCS.execute();
        insertHisoRespCS.close();

    }

    private static final String PRC_MBU_INSERT_HISO_DECOD =
            "{call    mbuintf.prc_mbu_insert_hiso_decod(" +
                    "                                          p_protocol   => :p_protocol,\n" +
                    "                                          p_ret_status => :p_ret_status,\n" +
                    "                                          p_orig_code  => :p_orig_code,\n" +
                    "                                          p_rsp_code   => :p_rsp_code,\n" +
                    "                                          p_msg_typ    => :p_msg_typ,\n" +
                    "                                          p_bitmap1    => :p_bitmap1,\n" +
                    "                                          p_dat_p1     => :p_dat_p1,\n" +
                    "                                          p_dat_p3     => :p_dat_p3,\n" +
                    "                                          p_dat_p4     => :p_dat_p4,\n" +
                    "                                          p_dat_p7     => :p_dat_p7,\n" +
                    "                                          p_dat_p11    => :p_dat_p11,\n" +
                    "                                          p_dat_p12    => :p_dat_p12,\n" +
                    "                                          p_dat_p13    => :p_dat_p13,\n" +
                    "                                          p_dat_p17    => :p_dat_p17,\n" +
                    "                                          p_dat_p23    => :p_dat_p23,\n" +
                    "                                          p_dat_p25    => :p_dat_p25,\n" +
                    "                                          p_dat_p32    => :p_dat_p32,\n" +
                    "                                          p_dat_p35    => :p_dat_p35,\n" +
                    "                                          p_dat_p37    => :p_dat_p37,\n" +
                    "                                          p_dat_p38    => :p_dat_p38,\n" +
                    "                                          p_dat_p39    => :p_dat_p39,\n" +
                    "                                          p_dat_p41    => :p_dat_p41,\n" +
                    "                                          p_dat_p43    => :p_dat_p43,\n" +
                    "                                          p_dat_p44a   => :p_dat_p44a,\n" +
                    "                                          p_dat_p44p   => :p_dat_p44p,\n" +
                    "                                          p_dat_p49    => :p_dat_p49,\n" +
                    "                                          p_dat_p60a   => :p_dat_p60a,\n" +
                    "                                          p_dat_p60p   => :p_dat_p60p,\n" +
                    "                                          p_dat_p61a   => :p_dat_p61a,\n" +
                    "                                          p_dat_p61p   => :p_dat_p61p,\n" +
                    "                                          p_dat_s90    => :p_dat_s90,\n" +
                    "                                          p_dat_s95    => :p_dat_s95,\n" +
                    "                                          p_dat_s100   => :p_dat_s100,\n" +
                    "                                          p_dat_s126   => :p_dat_s126,\n" +
                    "                                          p_dat_s127   => :p_dat_s127,\n" +
                    "                                          p_iid        => :p_iid,\n" +
                    "                                          p_iduplicate => :p_iduplicate)}";


    /**
     *
     * @param connection
     * @param request
     * @return
     * @throws SQLException
     */
    private static CallableStatement mapHisoRequest(Connection connection, HISOMessage request) throws SQLException {

        CallableStatement callableStatement = connection.prepareCall(PRC_MBU_INSERT_HISO_DECOD);

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

    private final static String PRC_MBU_INSERT_HISO_RSP = "{call mbuintf.prc_mbu_insert_hiso_rsp (\n" +
            "       p_protocol     => :p_protocol\n" +
            "      ,p_ret_status   => :p_ret_status\n" +
            "      ,p_orig_code    => :p_orig_code\n" +
            "      ,p_rsp_code     => :p_rsp_code\n" +
            "      ,p_msg_typ      => :p_msg_typ\n" +
            "      ,p_bitmap1      => :p_bitmap1\n" +
            "      ,p_dat_p1       => :p_dat_p1\n" +
            "      ,p_dat_p3       => :p_dat_p3\n" +
            "      ,p_dat_p4       => :p_dat_p4\n" +
            "      ,p_dat_p7       => :p_dat_p7\n" +
            "      ,p_dat_p11      => :p_dat_p11\n" +
            "      ,p_dat_p12      => :p_dat_p12\n" +
            "      ,p_dat_p13      => :p_dat_p13\n" +
            "      ,p_dat_p17      => :p_dat_p17\n" +
            "      ,p_dat_p23      => :p_dat_p23\n" +
            "      ,p_dat_p25      => :p_dat_p25\n" +
            "      ,p_dat_p32      => :p_dat_p32\n" +
            "      ,p_dat_p35      => :p_dat_p35\n" +
            "      ,p_dat_p37      => :p_dat_p37\n" +
            "      ,p_dat_p38      => :p_dat_p38\n" +
            "      ,p_dat_p39      => :p_dat_p39\n" +
            "      ,p_dat_p41      => :p_dat_p41\n" +
            "      ,p_dat_p43      => :p_dat_p43\n" +
            "      ,p_dat_p44a     => :p_dat_p44a\n" +
            "      ,p_dat_p44p     => :p_dat_p44p\n" +
            "      ,p_dat_p49      => :p_dat_p49\n" +
            "      ,p_dat_p60a     => :p_dat_p60a\n" +
            "      ,p_dat_p60p     => :p_dat_p60p\n" +
            "      ,p_dat_p61a     => :p_dat_p61a\n" +
            "      ,p_dat_p61p     => :p_dat_p61p\n" +
            "      ,p_dat_s90      => :p_dat_s90\n" +
            "      ,p_dat_s95      => :p_dat_s95\n" +
            "      ,p_dat_s100     => :p_dat_s100\n" +
            "      ,p_dat_s126     => :p_dat_s126\n" +
            "      ,p_dat_s127     => :p_dat_s127\n" +
            "      ,p_itrsid       => :p_itrsid\n" +
            "      ,p_ihisoreqid   => :p_ihisoreqid\n" +
            "  )}";


    /**
     *
     * @param connection
     * @param response
     * @param mbuTransId
     * @param hisoRequestId
     * @return
     * @throws SQLException
     */
    private static CallableStatement mapHisoResponse(Connection connection, HISOMessage response, long mbuTransId, long hisoRequestId) throws SQLException {
        logger.debug("mapping insert_hiso_resp - P44A: {}", response.getFields().get(PrimaryBitmapField.P44A));

        CallableStatement callableStatement = connection.prepareCall(PRC_MBU_INSERT_HISO_RSP);

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

}
