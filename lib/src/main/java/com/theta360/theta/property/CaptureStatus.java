package com.theta360.theta.property;

import java.util.HashMap;
import java.util.Map;

public enum CaptureStatus {
    IDLE((byte) 0),
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

    private static final Map<Byte, CaptureStatus> captureStatusMap = new HashMap<>();

    static {
        for (CaptureStatus captureStatus : CaptureStatus.values()) {
            captureStatusMap.put(captureStatus.value, captureStatus);
        }
    }

    public static CaptureStatus valueOf(byte value) {
        if (!captureStatusMap.containsKey(value)) {
            throw new RuntimeException();
        }

        return captureStatusMap.get(value);
    }
}
