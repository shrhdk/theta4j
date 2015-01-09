package com.theta360.ptp.code;

import com.theta360.ptp.type.UINT16;

public enum PropertyCode implements Code {
    UNDEFINED(new UINT16(0x5000)),
    BATTERY_LEVEL(new UINT16(0x5001)),
    FUNCTIONAL_MODE(new UINT16(0x5002)),
    IMAGE_SIZE(new UINT16(0x5003)),
    COMPRESSION_SETTING(new UINT16(0x5004)),
    WHITE_BALANCE(new UINT16(0x5005)),
    RGB_GAIN(new UINT16(0x5006)),
    F_NUMBER(new UINT16(0x5007)),
    FOCAL_LENGTH(new UINT16(0x5008)),
    FOCUS_DISTANCE(new UINT16(0x5009)),
    FOCUS_MODE(new UINT16(0x500A)),
    EXPOSURE_METERING_MODE(new UINT16(0x500B)),
    FLASH_MODE(new UINT16(0x500C)),
    EXPOSURE_TIME(new UINT16(0x500D)),
    EXPOSURE_PROGRAM_MODE(new UINT16(0x500E)),
    EXPOSURE_INDEX(new UINT16(0x500F)),
    EXPOSURE_BIAS_COMPENSATION(new UINT16(0x5010)),
    DATE_TIME(new UINT16(0x5011)),
    CAPTURE_DELAY(new UINT16(0x5012)),
    STILL_CAPTURE_MODE(new UINT16(0x5013)),
    CONTRAST(new UINT16(0x5014)),
    SHARPNESS(new UINT16(0x5015)),
    DIGITAL_ZOOM(new UINT16(0x5016)),
    EFFECT_MODE(new UINT16(0x5017)),
    BURST_NUMBER(new UINT16(0x5018)),
    BURST_INTERVAL(new UINT16(0x5019)),
    TIMELAPSE_NUMBER(new UINT16(0x501A)),
    TIMELAPSE_INTERVAL(new UINT16(0x501B)),
    FOCUS_METERING_MODE(new UINT16(0x501C)),
    UPLOAD_URL(new UINT16(0x501D)),
    ARTIST(new UINT16(0x501E)),
    COPYRIGHT_INFO(new UINT16(0x501F));

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


    public static boolean isReservedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b0101_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1101_0000;
    }
}
