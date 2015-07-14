/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.code;

import org.theta4j.ptp.type.UINT16;

public enum ResponseCode implements Code<UINT16> {
    UNDEFINED(0x2000),
    OK(0x2001),
    GENERAL_ERROR(0x2002),
    SESSION_NOT_OPEN(0x2003),
    INVALID_TRANSACTION_ID(0x2004),
    OPERATION_NOT_SUPPORTED(0x2005),
    PARAMETER_NOT_SUPPORTED(0x2006),
    INCOMPLETE_TRANSFER(0x2007),
    INVALID_STORAGE_ID(0x2008),
    INVALID_OBJECT_HANDLE(0x2009),
    DEVICE_PROP_NOT_SUPPORTED(0x200A),
    INVALID_OBJECT_FORMAT_CODE(0x200B),
    STORE_FULL(0x200C),
    OBJECT_WRITE_PROTECTED(0x200D),
    STORE_READ_ONLY(0x200E),
    ACCESS_DENIED(0x200F),
    NO_THUMBNAIL_PRESENT(0x2010),
    SELF_TEST_FAILED(0x2011),
    PARTIAL_DELETION(0x2012),
    STORE_NOT_AVAILABLE(0x2013),
    SPECIFICATION_BY_FORMAT_UNSUPPORTED(0x2014),
    NO_VALID_OBJECT_INFO(0x2015),
    INVALID_CODE_FORMAT(0x2016),
    UNKNOWN_VENDOR_CODE(0x2017),
    CAPTURE_ALREADY_TERMINATED(0x2018),
    DEVICE_BUSY(0x2019),
    INVALID_PARENT_OBJECT(0x201A),
    INVALID_DEVICE_PROP_FORMAT(0x201B),
    INVALID_DEVICE_PROP_VALUE(0x201C),
    INVALID_PARAMETER(0x201D),
    SESSION_ALREADY_OPEN(0x201E),
    TRANSACTION_CANCELLED(0x201F),
    SPECIFICATION_OF_DESTINATION_UNSUPPORTED(0x2020);

    // Property

    private final UINT16 value;

    // Constructor

    ResponseCode(int value) {
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
        return msn == (byte) 0b0010_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 value) {
        byte msn = (byte) (value.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1010_0000;
    }
}
