package com.theta360.theta.data;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum ErrorInfo {
    NONE(0x0000_0000L, Level.NONE),
    /**
     * Insufficient memory space.
     * <p/>
     * Issued when the shutter button is pressed when the remaining number of photos is 0.
     */
    OUT_OF_MEMORY(0x0000_0001L, Level.WARN),
    /**
     * The file number is exceeded the limitation.
     * <p/>
     * Issued when the file number limitation is exceeded. Recovered by resetting the file number.
     */
    FILE_NUMBER_OVER(0x0000_0004L, Level.WARN),
    /**
     * Internal clock is not set.
     * <p/>
     * Issued when the camera's internal clock is not set.
     */
    MISSING_TIME_SETTINGS(0x0000_0008L, Level.WARN),
    /**
     * Electromagnetic compass error.
     * <p/>
     * Issued when an error occurred in the electromagnetic compass.
     */
    COMPASS_ERROR(0x0000_0010L, Level.WARN),
    /**
     * Insufficient remaining battery for firmware update.
     * <p/>
     * Issued when there is insufficient remaining battery for firmware update.
     */
    FIRMWARE_UPDATE_WITH_LOW_BATTERY(0x0000_0020L, Level.WARN),
    /**
     * No firmware to update.
     * <p/>
     * Issued when the firmware to update cannot be found.
     */
    MISSING_FIRMWARE_FOR_UPDATE(0x0000_0040L, Level.WARN),
    /**
     * Invalid firmware is detected.
     * <p/>
     * Issued when an invalid firmware file is detected for firmware update.
     */
    INVALID_FIRMWARE(0x0000_0080L, Level.WARN),
    /**
     * Temperature error.
     * <p/>
     * Issued when the temperature detected inside the camera exceeds the standard temperature, and the camera power is simultaneously turned off.
     */
    TEMPERATURE_ERROR(0x8000_0000L, Level.ERROR),
    /**
     * Recharging error.
     * <p/>
     * Issued when a USB recharging error is detected, and the camera power is simultaneously turned off.
     */
    BATTERY_CHARGE_ERROR(0x4000_0000L, Level.ERROR),
    /**
     * Miscellaneous Error.
     */
    MISC_ERROR(0x2000_0000L, Level.ERROR),
    /**
     * SD memory card access error.
     * <p/>
     * Issued when an error in which the SD memory card cannot be accessed is detected.
     */
    SD_CARD_ACCESS_ERROR(0x1000_0000L, Level.ERROR),
    /**
     * Internal memory access error.
     * <p/>
     * Issued when an error in which the camera internal memory cannot be accessed is detected.
     */
    INTERNAL_MEMORY_ACCESS_ERROR(0x0800_0000L, Level.ERROR),
    /**
     * SD memory card format error.
     * <p/>
     * Issued when an error in which the SD memory card is not initialized is detected.
     */
    SD_CARD_FORMAT_ERROR(0x0400_0000L, Level.ERROR),
    /**
     * Internal memory format error.
     * <p/>
     * Issued when an error in which the internal memory is not be initialized is detected.
     */
    INTERNAL_MEMORY_FORMAT_ERROR(0x0200_0000L, Level.ERROR),
    /**
     * SD memory card fault.
     * <p/>
     * Issued when a fault is detected in the SD memory card.
     */
    SD_CARD_ERROR(0x0100_0000L, Level.ERROR),
    /**
     * Firmware update is failed.
     * <p/>
     * Issued when the firmware update is failed.
     */
    FIRMWARE_UPDATE_FAILED(0x0080_0000L, Level.ERROR),
    /**
     * Hardware error is detected.
     * <p/>
     * Issued when a malfunction is detected in the hardware used for shooting.
     */
    HARDWARE_ERROR(0x0040_0000L, Level.ERROR);

    private final UINT32 value;
    private final Level level;

    private ErrorInfo(long value, Level level) {
        this.value = new UINT32(value);
        this.level = level;
    }

    // Getter

    public UINT32 value() {
        return value;
    }

    public Level level() {
        return level;
    }

    // valueOf

    private static final Map<UINT32, ErrorInfo> ERROR_INFO_MAP = new HashMap<>();

    static {
        for (ErrorInfo errorInfo : ErrorInfo.values()) {
            ERROR_INFO_MAP.put(errorInfo.value, errorInfo);
        }
    }

    public static ErrorInfo valueOf(UINT32 value) {
        Validators.validateNonNull("value", value);

        if (!ERROR_INFO_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown ErrorInfo Value:" + value);
        }

        return ERROR_INFO_MAP.get(value);
    }

    // Related Enum

    public enum Level {
        NONE, WARN, ERROR;
    }
}
