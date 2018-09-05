package hr.kaba.olb.responders.ora.sql;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.sql.CallableStatement;

public class PlSqlCalls {

    public static final String PRC_MBU_INSERT_HISO_DECOD =
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


    public final static String PRC_MBU_INSERT_TRS = "{call mbuintf.prc_mbu_insert_trs(" +
            "                             p_iHisoId      => :p_iHisoId,\n" +
            "                             p_TRS_TYPE     => :p_TRS_TYPE,\n" +
            "                             p_iTRS_ID      => :p_iTRS_ID,\n" +
            "                             p_iDuplicate   => :p_iDuplicate,\n" +
            "                             p_iTrsStatus   => :p_iTrsStatus)\n}";


    public final static String PRC_MBU_INSERT_HISO_RSP = "{call mbuintf.prc_mbu_insert_hiso_rsp (\n" +
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


    public static final String PRC_MBU_LOG_ALL = "{call mbuintf.prc_mbu_log_all(" +
            "       p_id_trs            => :p_id_trs\n" +
            "      ,p_rsp_code          => :p_rsp_code\n" +
            "      ,p_ledger_balance    => :p_ledger_balance\n" +
            "      ,p_available_balance => :p_available_balance\n" +
            ")}";


    public static final String SELECT_TRX_RESPONSE_FIELDS = "select   mt.rsp_code\n" +
            ",        mt.ledger_balance \n" +
            ",        mt.available_balance \n" +
            "from     mbu_trans mt \n" +
            "where    mt.pk_id = ?\n" +
            "\n";

    public final String INSERT_OLB_LOG_TAB = "{call insert_olb_log_tab(?)}";

}
