package org.theta4j.data;

import org.theta4j.ptp.type.UINT16;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum StillCaptureMode {
    /**
     * The shooting mode is "Video shooting mode".
     *
     * Cannot change to other Still Capture Modes.
     * (Model: RICOH THETA m15)
     */
    VIDEO(0x0000),
    /**
     * Single shot mode.
     *
     * Default value when the shooting mode is "Still image shooting mode".
     */
    SINGLE_SHOT(0x0001),
    /**
     * Interval shooting mode.
     *
     * TimelapseNumber and TimelapseInterval cannot be changed when this mode is set.
     * Change these setting values before switching to interval shooting mode.
     *
     * @see org.theta4j.Theta#setTimelapseNumber(int)
     * @see org.theta4j.Theta#setTimelapseInterval(long)
     */
    INTERVAL_SHOT(0x0003);

    private final UINT16 value;

    private StillCaptureMode(int value) {
        this.value = new UINT16(value);
    }

    // Getter

    public UINT16 value() {
        return value;
    }

    // valueOf

    private static final Map<UINT16, StillCaptureMode> STILL_CAPTURE_MODE_MAP = new HashMap<>();

    static {
        for (StillCaptureMode stillCaptureMode : StillCaptureMode.values()) {
            STILL_CAPTURE_MODE_MAP.put(stillCaptureMode.value, stillCaptureMode);
        }
    }

    public static StillCaptureMode valueOf(UINT16 value) {
        Validators.notNull("value", value);

        if (!STILL_CAPTURE_MODE_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown StillCaptureMode Value: " + value);
        }

        return STILL_CAPTURE_MODE_MAP.get(value);
    }
}
