package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.constants.ProductIndicator;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;
import hr.kaba.olb.codec.constants.MessageType;

import java.util.*;
import java.util.stream.Collectors;

import static hr.kaba.olb.codec.constants.MessageType.*;
import static hr.kaba.olb.codec.constants.ProductIndicator.*;

public class FormatRules {
    private static final List<FormatRule> rules = new ArrayList<>();

    private static final Map<MessageType, MessageType> behaveAs = new HashMap<>();

    static {
        behaveAs.put(MessageType.AUTHORIZATION_ADVICE_REPEAT, MessageType.AUTHORIZATION_ADVICE);
        behaveAs.put(MessageType.TRX_ADVICE_REPEAT, MessageType.TRX_ADVICE);
        behaveAs.put(MessageType.TRX_REVERSAL_REPEAT, MessageType.TRX_REVERSAL);
    }

    static {
        // NMM requests
        rules.add(new FormatRule(NMM, NMM_REQ, PrimaryBitmapField.P1));
        rules.add(new FormatRule(NMM, NMM_REQ, PrimaryBitmapField.P7));
        rules.add(new FormatRule(NMM, NMM_REQ, PrimaryBitmapField.P11));
        rules.add(new FormatRule(NMM, NMM_REQ, SecondaryBitmapField.P70));

        // NMM responses
        rules.add(new FormatRule(NMM, NMM_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(NMM, NMM_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(NMM, NMM_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(NMM, NMM_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(NMM, NMM_RESP, SecondaryBitmapField.P70));

        //ATM requests 200
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P1, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P12));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P13));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P17));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P43));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P60A));
        rules.add(new FormatRule(ATM, TRX_REQ, PrimaryBitmapField.P61A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REQ, SecondaryBitmapField.S100, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REQ, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REQ, SecondaryBitmapField.S124A, FormatRule.FieldStatus.OPTIONAL));

        //POS requests 200
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P22, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P25));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P39, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P43));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, TRX_REQ, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S90, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S126P, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REQ, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //POS requests 100
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P22, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P25));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P43));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, SecondaryBitmapField.S126P, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_REQ, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //ATM responses 210
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P12));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P13));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P17));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P44A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P60A));
        rules.add(new FormatRule(ATM, TRX_RESP, PrimaryBitmapField.P61A));
        rules.add(new FormatRule(ATM, TRX_RESP, SecondaryBitmapField.S100));
        rules.add(new FormatRule(ATM, TRX_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_RESP, SecondaryBitmapField.S123A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_RESP, SecondaryBitmapField.S125A, FormatRule.FieldStatus.OPTIONAL));

        //POS requests 210
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, TRX_RESP, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_RESP, SecondaryBitmapField.S90, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_RESP, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_RESP, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, TRX_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_RESP, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //POS requests 110
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_RESP, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //ATM advices 220
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P1));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P12));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P13));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P17));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P39));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P43, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P60A));
        rules.add(new FormatRule(ATM, TRX_ADVICE, PrimaryBitmapField.P61A));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S90, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S100));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S123A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S124A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE, SecondaryBitmapField.S125A, FormatRule.FieldStatus.OPTIONAL));

        //POS advices 220
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P22, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P25));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P43));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, TRX_ADVICE, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S90, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S126P, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //POS pre authorizations advices 120
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P22, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P25));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P43));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, SecondaryBitmapField.S126P, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //ATM advice responses 230
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P1, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_ADVICE_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));

        //POS requests 230
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_ADVICE_RESP, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //POS requests 130
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P18));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, AUTHORIZATION_ADVICE_RESP, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));


        //ATM reversals 420
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P1));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P12));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P13));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P17));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P39));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P43));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P60A));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, PrimaryBitmapField.P61A));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S90));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S100));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S123A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S124A, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL, SecondaryBitmapField.S125A, FormatRule.FieldStatus.OPTIONAL));

        //POS reversals 420
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P12));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P13));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P17));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P18, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P22, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P23, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P25));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P38, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P43));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P60P));
        rules.add(new FormatRule(POS, TRX_REVERSAL, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_REVERSAL, SecondaryBitmapField.S90));
        rules.add(new FormatRule(POS, TRX_REVERSAL, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, SecondaryBitmapField.S100));
        rules.add(new FormatRule(POS, TRX_REVERSAL, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL, SecondaryBitmapField.S126P, FormatRule.FieldStatus.OPTIONAL));


        //ATM reversal responses 430
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, SecondaryBitmapField.S90));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, SecondaryBitmapField.S95, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(ATM, TRX_REVERSAL_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));

        //POS reversal 430
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P1));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P3));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P4));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P7));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P11));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P18, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P32));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P35));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P37));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P39));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P41));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P49));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, PrimaryBitmapField.P61P));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, SecondaryBitmapField.S90));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, SecondaryBitmapField.S102, FormatRule.FieldStatus.OPTIONAL));
        rules.add(new FormatRule(POS, TRX_REVERSAL_RESP, SecondaryBitmapField.S127P, FormatRule.FieldStatus.OPTIONAL));

    }

    public static MessageType behavesAs(MessageType messageType) {
        return behaveAs.keySet().contains(messageType) ? behaveAs.get(messageType) : messageType;
    }

    static boolean containsField(ProductIndicator productIndicator, MessageType messageType, BitmapField field) {
        final FormatRule rule = new FormatRule(productIndicator, behavesAs(messageType), field);

        return rules.stream()
                    .anyMatch(e -> e.equals(rule) && (e.getFieldStatus().equals(FormatRule.FieldStatus.MANDATORY) || e.getFieldStatus().equals(FormatRule.FieldStatus.OPTIONAL)))
                ;

    }

    public static Map<BitmapField, String> filterFields(ProductIndicator productIndicator, MessageType messageType, Map<BitmapField, String> fields) {
        return fields.entrySet().stream()
                                .filter(e -> containsField(productIndicator, messageType, e.getKey()))
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}

