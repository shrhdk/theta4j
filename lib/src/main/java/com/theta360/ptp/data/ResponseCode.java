package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    UNDEFINED (new UINT16(0x2000)),
    OK (new UINT16(0x2001)),
    GENERAL_ERROR (new UINT16(0x2002)),
    SESSION_NOT_OPEN (new UINT16(0x2003)),
    INVALID_TRANSACTION_ID (new UINT16(0x2004)),
    OPERATION_NOT_SUPPORTED (new UINT16(0x2005)),
    PARAMETER_NOT_SUPPORTED (new UINT16(0x2006)),
    INCOMPLETE_TRANSFER (new UINT16(0x2007)),
    INVALID_STORAGE_ID (new UINT16(0x2008)),
    INVALID_OBJECT_HANDLE (new UINT16(0x2009)),
    DEVICE_PROP_NOT_SUPPORTED (new UINT16(0x200A)),
    INVALID_OBJECT_FORMAT_CODE (new UINT16(0x200B)),
    STORE_FULL (new UINT16(0x200C)),
    OBJECT_WRITE_PROTECTED (new UINT16(0x200D)),
    STORE_READ_ONLY (new UINT16(0x200E)),
    ACCESS_DENIED (new UINT16(0x200F)),
    NO_THUMBNAIL_PRESENT (new UINT16(0x2010)),
    SELF_TEST_FAILED (new UINT16(0x2011)),
    PARTIAL_DELETION (new UINT16(0x2012)),
    STORE_NOT_AVAILABLE (new UINT16(0x2013)),
    SPECIFICATION_BY_FORMAT_UNSUPPORTED (new UINT16(0x2014)),
    NO_VALID_OBJECT_INFO (new UINT16(0x2015)),
    INVALID_CODE_FORMAT (new UINT16(0x2016)),
    UNKNOWN_VENDOR_CODE (new UINT16(0x2017)),
    CAPTURE_ALREADY_TERMINATED (new UINT16(0x2018)),
    DEVICE_BUSY (new UINT16(0x2019)),
    INVALID_PARENT_OBJECT (new UINT16(0x201A)),
    INVALID_DEVICE_PROP_FORMAT (new UINT16(0x201B)),
    INVALID_DEVICE_PROP_VALUE (new UINT16(0x201C)),
    INVALID_PARAMETER (new UINT16(0x201D)),
    SESSION_ALREADY_OPEN (new UINT16(0x201E)),
    TRANSACTION_CANCELLED (new UINT16(0x201F)),
    SPECIFICATION_OF_DESTINATION_UNSUPPORTED (new UINT16(0x2020));

    private static final Map<UINT16, ResponseCode> responseCodeList = new HashMap<>();

    static {
        for (ResponseCode responseCode : ResponseCode.values()) {
            responseCodeList.put(responseCode.getCode(), responseCode);
        }
    }

    public ResponseCode valueOf(UINT16 code) {
        if(!responseCodeList.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return responseCodeList.get(code);
    }

    private final UINT16 code;

    public UINT16 getCode() {
        return code;
    }

    private ResponseCode(UINT16 code) {
        this.code = code;
    }

    public static boolean isReservedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b0010_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1010_0000;
    }
}
