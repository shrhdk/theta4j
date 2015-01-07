package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;

public enum EventCode implements Code {
    UNDEFINED(new UINT16(0x4000)),
    CANCEL_TRANSACTION(new UINT16(0x4001)),
    OBJECT_ADDED(new UINT16(0x4002)),
    OBJECT_REMOVED(new UINT16(0x4003)),
    STORE_ADDED(new UINT16(0x4004)),
    STORE_REMOVED(new UINT16(0x4005)),
    DEVICE_PROP_CHANGED(new UINT16(0x4006)),
    OBJECT_INFO_CHANGED(new UINT16(0x4007)),
    DEVICE_INFO_CHANGED(new UINT16(0x4008)),
    REQUEST_OBJECT_TRANSFER(new UINT16(0x4009)),
    STORE_FULL(new UINT16(0x400A)),
    DEVICE_RESET(new UINT16(0x400B)),
    STORAGE_INFO_CHANGED(new UINT16(0x400C)),
    CAPTURE_COMPLETE(new UINT16(0x400D)),
    UNREPORTED_STATUS(new UINT16(0x400E));

    private final UINT16 code;

    private EventCode(UINT16 code) {
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
        return msn == (byte) 0b0100_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1100_0000;
    }
}
