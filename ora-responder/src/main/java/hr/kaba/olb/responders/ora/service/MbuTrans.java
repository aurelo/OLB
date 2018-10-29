package hr.kaba.olb.responders.ora.service;

import hr.kaba.olb.codec.message.HISOMessage;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MbuTrans {

    private static final Logger logger = LoggerFactory.getLogger(MbuTrans.class);

    public static class MbuTransAnswer {

        private final Long id;
        private final Integer isDuplicate;
        private final String status;

        public MbuTransAnswer(Long id, Integer isDuplicate, String status) {
            this.id = id;
            this.isDuplicate = isDuplicate;
            this.status = status;
        }

        public Long id() {
            return id;
        }

        public boolean isDuplicate() {
            return isDuplicate == 1;
        }

        public String status() {
            return status;
        }
    }

    /**
     *
     * @param connection
     * @param request
     * @param hisoDecodRequestId
     * @return
     * @throws SQLException
     */
    public static MbuTransAnswer logRequest(Connection connection, HISOMessage request, Long hisoDecodRequestId) throws SQLException {

        logger.debug("calling insert mbu trans");

        CallableStatement insertMbuTransCS = mapMbuTransInsert(connection, hisoDecodRequestId, request.getMessageType().getCode());
        insertMbuTransCS.execute();

        logger.debug("after executing insert mbu trans");

        Long mbuTransId = insertMbuTransCS.getLong("p_iTRS_ID");
        Integer isMbuTransDuplicate = insertMbuTransCS.getInt("p_iDuplicate");
        String mbuTransStatus = insertMbuTransCS.getString("p_iTrsStatus");

        logger.debug("inserted mbu hiso decod return id: {}, isDuplicate: {}, status: {}", mbuTransId, isMbuTransDuplicate, mbuTransStatus);

        insertMbuTransCS.close();


        return new MbuTransAnswer(mbuTransId, isMbuTransDuplicate, mbuTransStatus);
    }

    /**
     *
     * @param connection
     * @param mbuTransId
     * @return
     * @throws SQLException
     */
    public static HisoAnswer previousResponse(Connection connection, Long mbuTransId) throws SQLException {
        logger.debug("transaction is duplicate - returning previous forwardRequest for trans id: {}", mbuTransId);

        PreparedStatement selectPreviousResponse = mapFetchPreviousResponse(connection, mbuTransId);

        ResultSet previousResponseResultSet = selectPreviousResponse.executeQuery();

        previousResponseResultSet.next();

        String responseString = previousResponseResultSet.getString("rsp_code");
        Integer ledgerBalance = previousResponseResultSet.getInt("ledger_balance");
        Integer availableBalance = previousResponseResultSet.getInt("available_balance");

        logger.debug("Previous response=> code: {}", responseString);

        return new HisoAnswer(responseString, ledgerBalance, availableBalance);

    }


    public static String assignApprovalCode(Connection connection, Long mbuTransId) throws SQLException {

        logger.debug("assigning approval code for transaction {}", mbuTransId);

        CallableStatement assignedApprovalCode = mapAssignApprovalCode(connection, mbuTransId);

        assignedApprovalCode.execute();

        return assignedApprovalCode.getString(1);
    }


    public static String getApprovalCode(Connection connection, Long mbuTransId) throws SQLException {
        logger.debug("getting approval code for transaction {}", mbuTransId);

        CallableStatement assignedApprovalCode = mapGetApprovalCode(connection, mbuTransId);

        assignedApprovalCode.execute();

        return assignedApprovalCode.getString(1);
    }


    /**
     *
     */
    private final static String PRC_MBU_INSERT_TRS = "{call mbuintf.prc_mbu_insert_trs(" +
            "                             p_iHisoId      => :p_iHisoId,\n" +
            "                             p_TRS_TYPE     => :p_TRS_TYPE,\n" +
            "                             p_iTRS_ID      => :p_iTRS_ID,\n" +
            "                             p_iDuplicate   => :p_iDuplicate,\n" +
            "                             p_iTrsStatus   => :p_iTrsStatus)\n}";

    private static CallableStatement mapMbuTransInsert(Connection connection, Long hisoId, String msgType) throws SQLException {

        logger.debug("mapping insert_mbu_trans hisoId: {}, msgType: {}", hisoId, msgType);

        CallableStatement callableStatement = connection.prepareCall(PRC_MBU_INSERT_TRS);

        callableStatement.setLong("p_iHisoId", hisoId);
        callableStatement.setString("p_TRS_TYPE", msgType);

        callableStatement.registerOutParameter("p_iTRS_ID", Types.NUMERIC);
        callableStatement.registerOutParameter("p_iDuplicate", Types.INTEGER);
        callableStatement.registerOutParameter("p_iTrsStatus", Types.VARCHAR);

        return callableStatement;
    }

    private static final String SELECT_TRX_RESPONSE_FIELDS = "select   mt.rsp_code\n" +
            ",        mt.ledger_balance \n" +
            ",        mt.available_balance \n" +
            "from     mbu_trans mt \n" +
            "where    mt.pk_id = ?\n" +
            "\n";


    private static PreparedStatement mapFetchPreviousResponse(Connection connection, long mbuTransId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_TRX_RESPONSE_FIELDS);

        statement.setLong(1, mbuTransId);

        return statement;
    }

    private static final String ASSIGN_APPROVAL_CODE = "{? = call mbuintf.assign_approval_code(p_id_trs => ?)}";

    private static CallableStatement mapAssignApprovalCode(Connection connection, long mbuTransId) throws SQLException {
        CallableStatement statement = connection.prepareCall(ASSIGN_APPROVAL_CODE);

        statement.registerOutParameter(1, Types.VARCHAR);
        statement.setLong(2, mbuTransId);

        return statement;
    }

    private static final String GET_APPROVAL_CODE = "{? = call mbuintf.get_approval_code(p_id_trs => ?)}";

    private static CallableStatement mapGetApprovalCode(Connection connection, long mbuTransId) throws SQLException {
        CallableStatement statement = connection.prepareCall(GET_APPROVAL_CODE);

        statement.registerOutParameter(1, Types.VARCHAR);
        statement.setLong(2, mbuTransId);

        return statement;
    }




}
