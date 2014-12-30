package com.theta360.theta;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode {
    WLAN_POWER_CONTROL(new UINT16(0x99A1));

    private static final Map<UINT16, OperationCode> operationCodeList = new HashMap<>();

    static {
        for (OperationCode operationCode : OperationCode.values()) {
            operationCodeList.put(operationCode.getCode(), operationCode);
        }
    }

    public OperationCode valueOf(UINT16 code) {
        if (!operationCodeList.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return operationCodeList.get(code);
    }

    private final UINT16 code;

    public UINT16 getCode() {
        return code;
    }

    private OperationCode(UINT16 code) {
        this.code = code;
    }
}
