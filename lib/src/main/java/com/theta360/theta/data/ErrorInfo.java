package com.theta360.theta.data;

import com.theta360.ptp.type.UINT32;

import java.util.HashMap;
import java.util.Map;

public enum ErrorInfo {
    NONE(0x0000_0000L, Level.NONE),
    OUT_OF_MEMORY(0x0000_0001L, Level.WARN),
    FILE_NUMBER_OVER(0x0000_0004L, Level.WARN),
    MISSING_TIME_SETTINGS(0x0000_0008L, Level.WARN),
    COMPASS_ERROR(0x0000_0010L, Level.WARN),
    FIRMWARE_UPDATE_WITH_LOW_BATTERY(0x0000_0020L, Level.WARN),
    MISSING_FIRMWARE_FOR_UPDATE(0x0000_0040L, Level.WARN),
    INVALID_FIRMWARE(0x0000_0080L, Level.WARN),
    TEMPERATURE_ERROR(0x8000_0000L, Level.ERROR),
    BATTERY_CHARGE_ERROR(0x4000_0000L, Level.ERROR),
    MISC_ERROR(0x2000_0000L, Level.ERROR),
    SD_CARD_ACCESS_ERROR(0x1000_0000L, Level.ERROR),
    INTERNAL_MEMORY_ACCESS_ERROR(0x0800_0000L, Level.ERROR),
    SD_CARD_FORMAT_ERROR(0x0400_0000L, Level.ERROR),
    INTERNAL_MEMORY_FORMAT_ERROR(0x0200_0000L, Level.ERROR),
    SD_CARD_ERROR(0x0100_0000L, Level.ERROR),
    FIRMWARE_UPDATE_FAILED(0x0080_0000L, Level.ERROR),
    HARDWARE_ERROR(0x0040_0000L, Level.ERROR);

    private final UINT32 value;
    private final Level level;

    private ErrorInfo(long value, Level level) {
        this.value = new UINT32(value);
        this.level = level;
    }

    // Getter

    public UINT32 getValue() {
        return value;
    }

    public Level getLevel() {
        return level;
    }

    // valueOf

    private static final Map<UINT32, ErrorInfo> errorInfoMap = new HashMap<>();

    static {
        for (ErrorInfo errorInfo : ErrorInfo.values()) {
            errorInfoMap.put(errorInfo.value, errorInfo);
        }
    }

    public static ErrorInfo valueOf(UINT32 value) {
        if (!errorInfoMap.containsKey(value)) {
            throw new RuntimeException("Unknown ErrorInfo Value:" + value);
        }

        return errorInfoMap.get(value);
    }

    // Related Enum

    public enum Level {
        NONE, WARN, ERROR;
    }
}
