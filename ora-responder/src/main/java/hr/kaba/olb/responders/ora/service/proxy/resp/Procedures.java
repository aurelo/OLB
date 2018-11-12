package hr.kaba.olb.responders.ora.service.proxy.resp;

import hr.kaba.olb.protocol.detect.RequestType;
import hr.kaba.olb.responders.ora.service.RejectLogger;
import hr.kaba.olb.responders.ora.service.proxy.ConnectionResponder;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapper class that maps request types to Connection responders (services that can respond to request on connection)
 *
 * @author  Zlatko Gudasić
 * @version 1.0
 * @since   12.11.2018
 */
public class Procedures {


    private static final ConnectionResponder PRC_MBU_UNKNOWN = new SimpleResponder("{call mbuintf.prc_mbu_unknown(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final ConnectionResponder PRC_MBU_ATM_REQ_ISPLATA = new ElaborateResponder(
            "{call mbuintf.prc_mbu_atm_req_isplata(p_id_trs            => :p_id_trs,\n" +
                    "                                      p_add_rsp_ui        => :p_add_rsp_ui,\n" +
                    "                                      p_available_balance => :p_available_balance,\n" +
                    "                                      p_ledger_balance    => :p_ledger_balance,\n" +
                    "                                      p_rsp_code          => :p_rsp_code)}");

    private static final ConnectionResponder PRC_MBU_ATM_REQ_UPLATA = new ElaborateResponder(
            "{call mbuintf.prc_mbu_atm_req_uplata(p_id_trs            => :p_id_trs,\n" +
                    "                                      p_add_rsp_ui        => :p_add_rsp_ui,\n" +
                    "                                      p_available_balance => :p_available_balance,\n" +
                    "                                      p_ledger_balance    => :p_ledger_balance,\n" +
                    "                                      p_rsp_code          => :p_rsp_code)}");


    private static final ConnectionResponder PRC_MBU_ATM_REQ_STANJE = new ElaborateResponder(
            "{call mbuintf.prc_mbu_atm_req_stanje(p_id_trs            => :p_id_trs,\n" +
                    "                                      p_add_rsp_ui        => :p_add_rsp_ui,\n" +
                    "                                      p_available_balance => :p_available_balance,\n" +
                    "                                      p_ledger_balance    => :p_ledger_balance,\n" +
                    "                                      p_rsp_code          => :p_rsp_code)}"
    );

    private static final SimpleResponder PRC_MBU_ATM_REV_REQ = new SimpleResponder("{call mbuintf.prc_mbu_atm_rev_req(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final ConnectionResponder PRC_MBU_POS_REQ_PRODAJA = new SimpleResponder("{call mbuintf.prc_mbu_pos_req_prodaja(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_REQ_ISPLATA = new SimpleResponder("{call mbuintf.prc_mbu_pos_req_isplata(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_REQ_POVRAT = new SimpleResponder("{call mbuintf.prc_mbu_pos_req_povrat(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_REQ_PRODAJA_RATE = new SimpleResponder("{call mbuintf.prc_mbu_pos_req_prodaja_rate(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_POS_PRODAJA_KOR = new SimpleResponder("{call mbuintf.prc_pos_prodaja_kor(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_REVERSAL = new SimpleResponder( "{call mbuintf.prc_mbu_pos_reversal(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_PREAUTH = new SimpleResponder( "{call mbuintf.prc_mbu_pos_preauth(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_REQ_ISPLATA_KOR = new SimpleResponder( "{call mbuintf.prc_mbu_pos_req_isplata_kor(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");

    private static final SimpleResponder PRC_MBU_POS_PROD_RATE_STOR = new SimpleResponder( "{call mbuintf.prc_mbu_pos_prod_rate_stor(p_id_trs => :p_id_trs, p_rsp_code => :p_rsp_code)}");


    private static final Map<RequestType, ConnectionResponder> plsqlHandlers = new HashMap<>();

    static {
        plsqlHandlers.put(RequestType.UNKNOWN, PRC_MBU_UNKNOWN);

        plsqlHandlers.put(RequestType.ATM_WITHDRAWAL_REQUEST, PRC_MBU_ATM_REQ_ISPLATA);
        plsqlHandlers.put(RequestType.ATM_WITHDRAWAL_ADVICE, PRC_MBU_ATM_REQ_ISPLATA);
        plsqlHandlers.put(RequestType.ATM_BALANCE_INQUIRY, PRC_MBU_ATM_REQ_STANJE);
        plsqlHandlers.put(RequestType.ATM_REVERSAL, PRC_MBU_ATM_REV_REQ);
        plsqlHandlers.put(RequestType.ATM_DEPOSIT, PRC_MBU_ATM_REQ_UPLATA);


        plsqlHandlers.put(RequestType.POS_PURCHASE_REQUEST, PRC_MBU_POS_REQ_PRODAJA);
        plsqlHandlers.put(RequestType.POS_PURCHASE_ADVICE, PRC_MBU_POS_REQ_PRODAJA);

        plsqlHandlers.put(RequestType.POS_ONLINE_REQUEST, PRC_MBU_POS_REQ_PRODAJA);
        plsqlHandlers.put(RequestType.POS_ONLINE_ADVICE, PRC_MBU_POS_REQ_PRODAJA);

        plsqlHandlers.put(RequestType.POS_WITHDRAWAL_REQUEST, PRC_MBU_POS_REQ_ISPLATA);
        plsqlHandlers.put(RequestType.POS_WITHDRAWAL_ADVICE, PRC_MBU_POS_REQ_ISPLATA);

        plsqlHandlers.put(RequestType.POS_RETURN_REQUEST, PRC_MBU_POS_REQ_POVRAT);
        plsqlHandlers.put(RequestType.POS_RETURN_ADVICE, PRC_MBU_POS_REQ_POVRAT);

        plsqlHandlers.put(RequestType.POS_INSTALLMENT_REQUEST, PRC_MBU_POS_REQ_PRODAJA_RATE);
        plsqlHandlers.put(RequestType.POS_INSTALLMENT_ADVICE, PRC_MBU_POS_REQ_PRODAJA_RATE);


        plsqlHandlers.put(RequestType.POS_WITHDRAWAL_REVERSAL, PRC_MBU_POS_REVERSAL);
        plsqlHandlers.put(RequestType.POS_PURCHASE_REVERSAL, PRC_MBU_POS_REVERSAL);
        plsqlHandlers.put(RequestType.POS_INSTALLMENT_REVERSAL, PRC_MBU_POS_REVERSAL);
        plsqlHandlers.put(RequestType.POS_ONLINE_REVERSAL, PRC_MBU_POS_REVERSAL);
        plsqlHandlers.put(RequestType.POS_RETURN_REVERSAL, PRC_MBU_POS_REVERSAL);

        plsqlHandlers.put(RequestType.POS_INSTALLMENT_CANCELLATION_REQUEST, PRC_MBU_POS_PROD_RATE_STOR);
        plsqlHandlers.put(RequestType.POS_INSTALLMENT_CANCELLATION_ADVICE, PRC_MBU_POS_PROD_RATE_STOR);
        plsqlHandlers.put(RequestType.POS_INSTALLMENT_CANCELLATION_REVERSAL, PRC_MBU_POS_REVERSAL);

        plsqlHandlers.put(RequestType.POS_AUTHORIZATION_REQUEST, PRC_MBU_POS_PREAUTH);
        plsqlHandlers.put(RequestType.POS_AUTHORIZATION_ADVICE, PRC_MBU_POS_PREAUTH);

        plsqlHandlers.put(RequestType.POS_WITHDRAWAL_ADJUSTMENT_REQUEST, PRC_MBU_POS_REQ_ISPLATA_KOR);
        plsqlHandlers.put(RequestType.POS_WITHDRAWAL_ADJUSTMENT_ADVICE, PRC_MBU_POS_REQ_ISPLATA_KOR);

        plsqlHandlers.put(RequestType.POS_PURCHASE_ADJUSTMENT_ADVICE, PRC_POS_PRODAJA_KOR);
    }


   public static ConnectionResponder handlerFor(RequestType requestType) {
        return plsqlHandlers.getOrDefault(requestType, PRC_MBU_UNKNOWN);
    }

}
