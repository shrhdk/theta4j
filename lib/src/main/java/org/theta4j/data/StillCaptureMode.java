/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.theta4j.ptp.type.UINT16;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum represents the still capture mode.
 */
public enum StillCaptureMode {
    /**
     * The shooting mode is "Video shooting mode".
     * <p/>
     * Cannot change to other Still Capture Modes.
     * (Model: RICOH THETA m15)
     */
    VIDEO(0x0000),
    /**
     * Single shot mode.
     * <p/>
     * Default value when the shooting mode is "Still image shooting mode".
     */
    SINGLE(0x0001),
    /**
     * Interval shooting mode.
     * <p/>
     * TimelapseNumber and TimelapseInterval cannot be changed when this mode is set.
     * Change these setting values before switching to interval shooting mode.
     *
     * @see org.theta4j.Theta#setTimelapseNumber(int)
     * @see org.theta4j.Theta#setTimelapseInterval(int)
     */
    TIME_LAPSE(0x0003);

    // Map for valueOf method

    private static final Map<UINT16, StillCaptureMode> STILL_CAPTURE_MODE_MAP = new HashMap<>();

    static {
        for (StillCaptureMode stillCaptureMode : StillCaptureMode.values()) {
            STILL_CAPTURE_MODE_MAP.put(stillCaptureMode.value, stillCaptureMode);
        }
    }

    // Property

    private final UINT16 value;

    // Constructor

    StillCaptureMode(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    /**
     * Returns the integer value according THETA API v1.
     */
    public UINT16 value() {
        return value;
    }

    // valueOf

    /**
     * Returns the still capture mode enum from the integer value defined by THETA API v1.
     */
    public static StillCaptureMode valueOf(UINT16 value) {
        Validators.notNull("value", value);

        if (!STILL_CAPTURE_MODE_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown StillCaptureMode Value: " + value);
        }

        return STILL_CAPTURE_MODE_MAP.get(value);
    }
}
