/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.theta4j.ptp.type.UINT8;
import org.theta4j.util.Validators;

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

    // Map for valueOf method

    private static final Map<UINT8, CaptureStatus> CAPTURE_STATUS_MAP = new HashMap<>();

    static {
        for (CaptureStatus captureStatus : CaptureStatus.values()) {
            CAPTURE_STATUS_MAP.put(captureStatus.value, captureStatus);
        }
    }

    // Property

    private final UINT8 value;

    // Constructor

    CaptureStatus(int value) {
        this.value = new UINT8(value);
    }

    // Getter

    public UINT8 value() {
        return value;
    }

    // valueOf

    public static CaptureStatus valueOf(UINT8 value) {
        Validators.notNull("value", value);

        if (!CAPTURE_STATUS_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown CaptureStatus Value: " + value);
        }

        return CAPTURE_STATUS_MAP.get(value);
    }
}
