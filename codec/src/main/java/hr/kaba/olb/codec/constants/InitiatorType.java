package hr.kaba.olb.codec.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum InitiatorType {
     UNKNOWN("0")
    ,BASE24_TERMINAL("1")
    ,DEVICE_HANDLER_PROCESS("2")
    ,AUTHORIZATION_PROCESS("3")
    ,HISO_PROCESS("4")
    ,HOST("5")
    ,EXTERNAL_NETWORK_PROCESS("6")
    ,EXTERNAL_NETWORK("7")
    ,FHM_PROCESS("8")
    ;

    private final String code;

    private static final Map<String, InitiatorType> codes;

    static {
        codes = Arrays.stream(InitiatorType.values()).collect(Collectors.toMap(InitiatorType::getCode, e -> e));
    }

    InitiatorType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static InitiatorType find(String code) {
        return codes.getOrDefault(code, InitiatorType.UNKNOWN);
    }
}
