package com.theta360.theta.data;

import com.theta360.ptp.type.UINT8;
import com.theta360.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum CaptureStatus {
    /**
     * The camera is ready.
     */
    IDLE(0),
    /**
     * The camera is executing shooting.
     */
    CAPTURING(1);

    private final UINT8 value;

    private CaptureStatus(int value) {
        this.value = new UINT8(value);
    }

    // Getter

    public UINT8 value() {
        return value;
    }

    // valueOf

    private static final Map<UINT8, CaptureStatus> CAPTURE_STATUS_MAP = new HashMap<>();

    static {
        for (CaptureStatus captureStatus : CaptureStatus.values()) {
            CAPTURE_STATUS_MAP.put(captureStatus.value, captureStatus);
        }
    }

    public static CaptureStatus valueOf(UINT8 value) {
        Validators.validateNonNull("value", value);

        if (!CAPTURE_STATUS_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown CaptureStatus Value: " + value);
        }

        return CAPTURE_STATUS_MAP.get(value);
    }
}
