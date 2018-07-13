package hr.kaba.olb.codec.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum NetworkManagementInformationCode {
     LOGON("001")
    ,LOGOFF("002")
    ,ECHO("301");

     private final String code;

     private static Map<String, NetworkManagementInformationCode> codes;

     static {
         codes = Arrays.stream(NetworkManagementInformationCode.values()).collect(Collectors.toMap(NetworkManagementInformationCode::getCode, e -> e));
     }

    NetworkManagementInformationCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NetworkManagementInformationCode find(String code) {
        return codes.get(code);
    }

}
