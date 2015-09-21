/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.theta4j.ptp.type.UINT16;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

/**
 * The enum represents the  ISO speed.
 */
public enum ISOSpeed {
    /**
     * 100
     */
    ISO_100(100),
    /**
     * 125
     */
    ISO_125(125),
    /**
     * 160
     */
    ISO_160(160),
    /**
     * 200
     */
    ISO_200(200),
    /**
     * 250
     */
    ISO_250(250),
    /**
     * 320
     */
    ISO_320(320),
    /**
     * 400
     */
    ISO_400(400),
    /**
     * 500
     */
    ISO_500(500),
    /**
     * 640
     */
    ISO_640(640),
    /**
     * 800
     */
    ISO_800(800),
    /**
     * 1000
     */
    ISO_1000(1000),
    /**
     * 1250
     */
    ISO_1250(1250),
    /**
     * Auto
     */
    AUTO(0xFFFF);

    // Map for valueOf method

    private static final Map<UINT16, ISOSpeed> ISO_SPEED_MAP = new HashMap<>();

    static {
        for (ISOSpeed isoSpeed : ISOSpeed.values()) {
            ISO_SPEED_MAP.put(isoSpeed.value, isoSpeed);
        }
    }

    // Property

    private final UINT16 value;

    // Constructor

    ISOSpeed(int value) {
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
     * Returns the ISO speed enum from the integer value defined by THETA API v1.
     */
    public static ISOSpeed valueOf(UINT16 value) {
        Validators.notNull("value", value);

        if (!ISO_SPEED_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown ISOSpeed value: " + value);
        }

        return ISO_SPEED_MAP.get(value);
    }
}
