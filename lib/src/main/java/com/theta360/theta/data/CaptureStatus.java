package com.theta360.theta.data;

import java.util.HashMap;
import java.util.Map;

public enum CaptureStatus {
    /**
     * The camera is ready.
     */
    IDLE((byte) 0),
    /**
     * The camera is executing shooting.
     */
    CAPTURING((byte) 1);

    private final byte value;

    private CaptureStatus(byte value) {
        this.value = value;
    }

    // Getter

    public byte getValue() {
        return value;
    }

    // valueOf

    private static final Map<Byte, CaptureStatus> CAPTURE_STATUS_MAP = new HashMap<>();

    static {
        for (CaptureStatus captureStatus : CaptureStatus.values()) {
            CAPTURE_STATUS_MAP.put(captureStatus.value, captureStatus);
        }
    }

    public static CaptureStatus valueOf(byte value) {
        if (!CAPTURE_STATUS_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown CaptureStatus Value: " + value);
        }

        return CAPTURE_STATUS_MAP.get(value);
    }
}
