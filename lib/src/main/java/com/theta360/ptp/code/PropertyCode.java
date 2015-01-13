package com.theta360.ptp.code;

import com.theta360.ptp.type.UINT16;

public enum PropertyCode implements Code<UINT16> {
    UNDEFINED(0x5000),
    BATTERY_LEVEL(0x5001),
    FUNCTIONAL_MODE(0x5002),
    IMAGE_SIZE(0x5003),
    COMPRESSION_SETTING(0x5004),
    WHITE_BALANCE(0x5005),
    RGB_GAIN(0x5006),
    F_NUMBER(0x5007),
    FOCAL_LENGTH(0x5008),
    FOCUS_DISTANCE(0x5009),
    FOCUS_MODE(0x500A),
    EXPOSURE_METERING_MODE(0x500B),
    FLASH_MODE(0x500C),
    EXPOSURE_TIME(0x500D),
    EXPOSURE_PROGRAM_MODE(0x500E),
    EXPOSURE_INDEX(0x500F),
    EXPOSURE_BIAS_COMPENSATION(0x5010),
    DATE_TIME(0x5011),
    CAPTURE_DELAY(0x5012),
    STILL_CAPTURE_MODE(0x5013),
    CONTRAST(0x5014),
    SHARPNESS(0x5015),
    DIGITAL_ZOOM(0x5016),
    EFFECT_MODE(0x5017),
    BURST_NUMBER(0x5018),
    BURST_INTERVAL(0x5019),
    TIMELAPSE_NUMBER(0x501A),
    TIMELAPSE_INTERVAL(0x501B),
    FOCUS_METERING_MODE(0x501C),
    UPLOAD_URL(0x501D),
    ARTIST(0x501E),
    COPYRIGHT_INFO(0x501F);

    private final UINT16 value;

    private PropertyCode(int value) {
        this.value = new UINT16(value);
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }

    // Code Type Checker

    public static boolean isReservedCode(UINT16 value) {
        byte msn = (byte) (value.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b0101_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 value) {
        byte msn = (byte) (value.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1101_0000;
    }
}
