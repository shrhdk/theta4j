package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum WhiteBalance {
    AUTO(0x0002),
    DAYLIGHT(0x0004),
    IN_SHADE(0x8001),
    CLOUDY(0x8002),
    INCANDESCENT_LAMP_1(0x0006),
    INCANDESCENT_LAMP_2(0x8020),
    DAYLIGHT_FLUORESCENT_LAMP(0x8003),
    NATURAL_WHITE_FLUORESCENT_LAMP(0x8004),
    COOL_WHITE_FLUORESCENT_LAMP(0x8005),
    LIGHT_BULB(0x8006);

    private final UINT16 value;

    private WhiteBalance(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    public UINT16 getValue() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, WhiteBalance> whiteBalanceMap = new HashMap<>();

    static {
        for (WhiteBalance whiteBalance : WhiteBalance.values()) {
            whiteBalanceMap.put(whiteBalance.value, whiteBalance);
        }
    }

    public static WhiteBalance valueOf(UINT16 value) {
        if (!whiteBalanceMap.containsKey(value)) {
            throw new RuntimeException();
        }

        return whiteBalanceMap.get(value);
    }
}
