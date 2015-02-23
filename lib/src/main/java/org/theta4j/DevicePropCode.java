package org.theta4j;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.type.UINT16;

enum DevicePropCode implements Code<UINT16> {
    BATTERY_LEVEL(0x5001),
    WHITE_BALANCE(0x5005),
    EXPOSURE_INDEX(0x500F),
    EXPOSURE_BIAS_COMPENSATION(0x5010),
    DATE_TIME(0x5011),
    STILL_CAPTURE_MODE(0x5013),
    TIMELAPSE_NUMBER(0x501A),
    TIMELAPSE_INTERVAL(0x501B),
    AUDIO_VOLUME(0x502C),
    ERROR_INFO(0xD006),
    SHUTTER_SPEED(0xD00F),
    GPS_INFO(0xD801),
    AUTO_POWER_OFF_DELAY(0xD802),
    SLEEP_DELAY(0xD803),
    CHANNEL_NUMBER(0xD807),
    CAPTURE_STATUS(0xD808),
    RECORDING_TIME(0xD809),
    REMAINING_RECORDING_TIME(0xD80A);

    private final UINT16 value;

    private DevicePropCode(int value) {
        this.value = new UINT16(value);
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }
}
