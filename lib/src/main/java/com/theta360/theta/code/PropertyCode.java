package com.theta360.theta.code;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.type.UINT16;

public enum PropertyCode implements Code {
    BATTERY_LEVEL(new UINT16(0x5001)),
    WHITE_BALANCE(new UINT16(0x5005)),
    EXPOSURE_INDEX(new UINT16(0x500F)),
    EXPOSURE_BIAS_COMPENSATION(new UINT16(0x5010)),
    DATE_TIME(new UINT16(0x5011)),
    STILL_CAPTURE_MODE(new UINT16(0x5013)),
    TIMELAPSE_NUMBER(new UINT16(0x501A)),
    TIMELAPSE_INTERVAL(new UINT16(0x501B)),
    AUDIO_VOLUME(new UINT16(0x502C)),
    ERROR_INFO(new UINT16(0xD006)),
    SHUTTER_SPEED(new UINT16(0xD00F)),
    GPS_INFO(new UINT16(0xD801)),
    AUTO_POWER_OFF_DELAY(new UINT16(0xD802)),
    SLEEP_DELAY(new UINT16(0xD803)),
    CHANNEL_NUMBER(new UINT16(0xD807)),
    CAPTURE_STATUS(new UINT16(0xD808)),
    RECORDING_TIME(new UINT16(0xD809)),
    REMAINING_RECORDING_TIME(new UINT16(0xD80A));

    private final UINT16 code;

    private PropertyCode(UINT16 code) {
        if (code == null) {
            throw new AssertionError();
        }

        this.code = code;
    }

    @Override
    public UINT16 getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name();
    }
}
