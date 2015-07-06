package org.theta4j;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.type.UINT16;

enum OperationCode implements Code<UINT16> {
    GET_RESIZED_IMAGE_OBJECT(0x1022),
    WLAN_POWER_CONTROL(0x99A1);

    // Property

    private final UINT16 value;

    // Constructor

    OperationCode(int value) {
        this.value = new UINT16(value);
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }
}
