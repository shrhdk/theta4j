package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum StillCaptureMode {
    VIDEO(0x0000),
    SINGLE_SHOT(0x0001),
    INTERVAL_SHOT(0x0003);

    private final UINT16 value;

    private StillCaptureMode(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    public UINT16 getValue() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, StillCaptureMode> stillCaptureModeMap = new HashMap<>();

    static {
        for (StillCaptureMode stillCaptureMode : StillCaptureMode.values()) {
            stillCaptureModeMap.put(stillCaptureMode.value, stillCaptureMode);
        }
    }

    public static StillCaptureMode valueOf(UINT16 value) {
        if (!stillCaptureModeMap.containsKey(value)) {
            throw new RuntimeException();
        }

        return stillCaptureModeMap.get(value);
    }
}
