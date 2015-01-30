package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

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
    SINGLE_SHOT(0x0001),
    /**
     * Interval shooting mode.
     * <p/>
     * TimelapseNumber and TimelapseInterval cannot be changed when this mode is set.
     * Change these setting values before switching to interval shooting mode.
     *
     * @see com.theta360.theta.Theta#setTimelapseNumber(int)
     * @see com.theta360.theta.Theta#setTimelapseInterval(long)
     */
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

    private static final Map<UINT16, StillCaptureMode> STILL_CAPTURE_MODE_MAP = new HashMap<>();

    static {
        for (StillCaptureMode stillCaptureMode : StillCaptureMode.values()) {
            STILL_CAPTURE_MODE_MAP.put(stillCaptureMode.value, stillCaptureMode);
        }
    }

    public static StillCaptureMode valueOf(UINT16 value) {
        if (!STILL_CAPTURE_MODE_MAP.containsKey(value)) {
            throw new RuntimeException();
        }

        return STILL_CAPTURE_MODE_MAP.get(value);
    }
}
