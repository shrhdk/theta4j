package com.theta360.theta.code;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode implements Code {
    GET_RESIZED_IMAGE_OBJECT(new UINT16(0x1022)),
    WLAN_POWER_CONTROL(new UINT16(0x99A1));

    private static final Map<UINT16, OperationCode> operationCodeList = new HashMap<>();

    static {
        for (OperationCode operationCode : OperationCode.values()) {
            operationCodeList.put(operationCode.getCode(), operationCode);
        }
    }

    private final UINT16 code;

    @Override
    public UINT16 getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name();
    }

    private OperationCode(UINT16 code) {
        this.code = code;
    }

    public OperationCode valueOf(UINT16 code) {
        if (!operationCodeList.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return operationCodeList.get(code);
    }
}
