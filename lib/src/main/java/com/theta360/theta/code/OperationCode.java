package com.theta360.theta.code;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode implements Code<UINT16> {
    GET_RESIZED_IMAGE_OBJECT(0x1022),
    WLAN_POWER_CONTROL(0x99A1);

    private final UINT16 value;

    private OperationCode(int value) {
        this.value = new UINT16(value);
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, OperationCode> operationCodeMap = new HashMap<>();

    static {
        for (OperationCode operationCode : OperationCode.values()) {
            operationCodeMap.put(operationCode.value(), operationCode);
        }
    }

    public OperationCode valueOf(UINT16 code) {
        if (!operationCodeMap.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return operationCodeMap.get(code);
    }
}
