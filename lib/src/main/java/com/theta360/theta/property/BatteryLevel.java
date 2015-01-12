package com.theta360.theta.property;

import java.util.HashMap;
import java.util.Map;

public enum BatteryLevel {
    FULL((byte) 100),
    HALF((byte) 67),
    NEAR_END((byte) 33),
    END((byte) 0);

    private final byte value;

    private BatteryLevel(byte value) {
        this.value = value;
    }

    // Getter

    public byte getValue() {
        return value;
    }

    // valueOf

    private static final Map<Byte, BatteryLevel> batteryLevelMap = new HashMap<>();

    static {
        for (BatteryLevel batteryLevel : BatteryLevel.values()) {
            batteryLevelMap.put(batteryLevel.value, batteryLevel);
        }
    }

    public static BatteryLevel valueOf(byte value) {
        if (!batteryLevelMap.containsKey(value)) {
            throw new RuntimeException();
        }

        return batteryLevelMap.get(value);
    }
}
