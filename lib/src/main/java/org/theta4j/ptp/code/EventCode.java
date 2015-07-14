/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.code;

import org.theta4j.ptp.type.UINT16;

public enum EventCode implements Code<UINT16> {
    UNDEFINED(0x4000),
    CANCEL_TRANSACTION(0x4001),
    OBJECT_ADDED(0x4002),
    OBJECT_REMOVED(0x4003),
    STORE_ADDED(0x4004),
    STORE_REMOVED(0x4005),
    DEVICE_PROP_CHANGED(0x4006),
    OBJECT_INFO_CHANGED(0x4007),
    DEVICE_INFO_CHANGED(0x4008),
    REQUEST_OBJECT_TRANSFER(0x4009),
    STORE_FULL(0x400A),
    DEVICE_RESET(0x400B),
    STORAGE_INFO_CHANGED(0x400C),
    CAPTURE_COMPLETE(0x400D),
    UNREPORTED_STATUS(0x400E);

    // Property

    private final UINT16 value;

    // Constructor

    EventCode(int value) {
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
        return msn == (byte) 0b0100_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 value) {
        byte msn = (byte) (value.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1100_0000;
    }
}
