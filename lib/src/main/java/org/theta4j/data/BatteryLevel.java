/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.theta4j.ptp.type.UINT8;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

/**
 * Battery Level
 */
public enum BatteryLevel {
    /**
     * The battery level is full.
     */
    FULL(100),
    /**
     * The battery level is half.
     */
    HALF(67),
    /**
     * The battery level is near end.
     */
    NEAR_END(33),
    /**
     * The battery level is empty.
     */
    END(0);

    // Map for valueOf method

    private static final Map<UINT8, BatteryLevel> BATTERY_LEVEL_MAP = new HashMap<>();

    static {
        for (BatteryLevel batteryLevel : BatteryLevel.values()) {
            BATTERY_LEVEL_MAP.put(batteryLevel.value, batteryLevel);
        }
    }

    // Property

    private final UINT8 value;

    // Constructor

    BatteryLevel(int value) {
        this.value = new UINT8(value);
    }

    // Getter

    public UINT8 value() {
        return value;
    }

    // valueOf

    public static BatteryLevel valueOf(UINT8 value) {
        Validators.notNull("value", value);

        if (!BATTERY_LEVEL_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown BatteryLevel value: " + value);
        }

        return BATTERY_LEVEL_MAP.get(value);
    }
}
