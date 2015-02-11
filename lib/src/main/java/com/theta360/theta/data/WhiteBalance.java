package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum WhiteBalance {
    /**
     * Automatic
     */
    AUTO(0x0002),
    /**
     * Daylight
     */
    DAYLIGHT(0x0004),
    /**
     * Shade
     */
    IN_SHADE(0x8001),
    /**
     * Cloudy
     */
    CLOUDY(0x8002),
    /**
     * Incandescent lamp 1
     */
    INCANDESCENT_LAMP_1(0x0006),
    /**
     * Incandescent lamp 2
     */
    INCANDESCENT_LAMP_2(0x8020),
    /**
     * Fluorescent lamp 1 (Daylight color)
     */
    DAYLIGHT_FLUORESCENT_LAMP(0x8003),
    /**
     * Fluorescent lamp 2 (Neutral white color)
     */
    NATURAL_WHITE_FLUORESCENT_LAMP(0x8004),
    /**
     * Fluorescent lamp 3 (White)
     */
    COOL_WHITE_FLUORESCENT_LAMP(0x8005),
    /**
     * Fluorescent lamp 4 (Light bulb color)
     */
    LIGHT_BULB(0x8006);

    private final UINT16 value;

    private WhiteBalance(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    public UINT16 value() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, WhiteBalance> WHITE_BALANCE_MAP = new HashMap<>();

    static {
        for (WhiteBalance whiteBalance : WhiteBalance.values()) {
            WHITE_BALANCE_MAP.put(whiteBalance.value, whiteBalance);
        }
    }

    public static WhiteBalance valueOf(UINT16 value) {
        Validators.validateNonNull("value", value);

        if (!WHITE_BALANCE_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown WhiteBalance Value: " + value);
        }

        return WHITE_BALANCE_MAP.get(value);
    }
}
