package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;
import com.theta360.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum ISOSpeed {
    ISO_100(100), ISO_125(125), ISO_160(160), ISO_200(200),
    ISO_250(250), ISO_320(320), ISO_400(400), ISO_500(500),
    ISO_640(640), ISO_800(800), ISO_1000(1000), ISO_1250(1250),
    AUTO(0xFFFF);

    private final UINT16 value;

    // Constructor

    private ISOSpeed(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    public UINT16 getValue() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, ISOSpeed> isoSpeedMap = new HashMap<>();

    static {
        for (ISOSpeed isoSpeed : ISOSpeed.values()) {
            isoSpeedMap.put(isoSpeed.value, isoSpeed);
        }
    }

    public static ISOSpeed valueOf(UINT16 value) {
        Validators.validateNonNull("value", value);

        if (!isoSpeedMap.containsKey(value)) {
            throw new RuntimeException("Unknown ISOSpeed value: " + value);
        }

        return isoSpeedMap.get(value);
    }
}
