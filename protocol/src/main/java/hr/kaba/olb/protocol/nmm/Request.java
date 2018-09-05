package hr.kaba.olb.protocol.nmm;

import hr.kaba.olb.codec.constants.*;
import hr.kaba.olb.codec.message.Base24Header;
import hr.kaba.olb.codec.message.HISOMessage;
import hr.kaba.olb.codec.message.OLBMessage;
import hr.kaba.olb.codec.message.bitmap.Bitmap;
import hr.kaba.olb.codec.message.bitmap.BitmapField;
import hr.kaba.olb.codec.message.bitmap.PrimaryBitmapField;
import hr.kaba.olb.codec.message.bitmap.SecondaryBitmapField;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final NetworkManagementInformationCode requestType;

    private Map<BitmapField, String> fixedFields;

    private final Base24Header header;

    public Request(NetworkManagementInformationCode requestType, InitiatorType originator) {
        this.requestType = requestType;

        this.header = new Base24Header(ProductIndicator.NMM, Header.RELEASE_NUMBER, Header.STATUS, originator, InitiatorType.UNKNOWN);

        this.fixedFields = new HashMap<>();
        fixedFields.put(SecondaryBitmapField.P70, requestType.getCode());
        fixedFields.put(PrimaryBitmapField.P1, Bitmap.hexBitmapFromFields(fixedFields));

    }

    private Map<BitmapField, String> requestData(String transmissionDateTime, String sysTraceAudeitNumber) {
        Map<BitmapField, String> variableFields = new HashMap<>();

        variableFields.put(PrimaryBitmapField.P7, transmissionDateTime);
        variableFields.put(PrimaryBitmapField.P11, sysTraceAudeitNumber);

        variableFields.putAll(fixedFields);

        return variableFields;
    }

    public HISOMessage create(String transmissionDateTime, String sysTraceAuditNumber) {
        return new OLBMessage(header, MessageType.NMM_REQ, requestData(transmissionDateTime, sysTraceAuditNumber));
    }


    public static HISOMessage echo(String transmissionDateTime, String sysTraceAuditNumber) {
        return new Request(NetworkManagementInformationCode.ECHO, InitiatorType.HOST).create(transmissionDateTime, sysTraceAuditNumber);
    }


    public static HISOMessage logon(String transmissionDateTime, String sysTraceAuditNumber) {
        return new Request(NetworkManagementInformationCode.LOGON, InitiatorType.HOST).create(transmissionDateTime, sysTraceAuditNumber);
    }

    public static HISOMessage logoff(String transmissionDateTime, String sysTraceAuditNumber) {
        return new Request(NetworkManagementInformationCode.LOGOFF, InitiatorType.HOST).create(transmissionDateTime, sysTraceAuditNumber);
    }

}
