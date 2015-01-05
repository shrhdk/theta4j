package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT16;

import java.util.HashMap;
import java.util.Map;

public enum OperationCode {
    UNDEFINED(new UINT16(0x1000)),
    GET_DEVICE_INFO(new UINT16(0x1001)),
    OPEN_SESSION(new UINT16(0x1002)),
    CLOSE_SESSION(new UINT16(0x1003)),
    GET_STORAGE_IDS(new UINT16(0x1004)),
    GET_STORAGE_INFO(new UINT16(0x1005)),
    GET_NUM_OBJECTS(new UINT16(0x1006)),
    GET_OBJECT_HANDLES(new UINT16(0x1007)),
    GET_OBJECT_INFO(new UINT16(0x1008)),
    GET_OBJECT(new UINT16(0x1009)),
    GET_THUMB(new UINT16(0x100A)),
    DELETE_OBJECT(new UINT16(0x100B)),
    SEND_OBJECT_INFO(new UINT16(0x100C)),
    SEND_OBJECT(new UINT16(0x100D)),
    INITIATE_CAPTURE(new UINT16(0x100E)),
    FORMAT_STORE(new UINT16(0x100F)),
    RESET_DEVICE(new UINT16(0x1010)),
    SELF_TEST(new UINT16(0x1011)),
    SET_OBJECT_PROTECTION(new UINT16(0x1012)),
    POWER_DOWN(new UINT16(0x1013)),
    GET_DEVICE_PROP_DESC(new UINT16(0x1014)),
    GET_DEVICE_PROP_VALUE(new UINT16(0x1015)),
    SET_DEVICE_PROP_VALUE(new UINT16(0x1016)),
    RESET_DEVICE_PROP_VALUE(new UINT16(0x1017)),
    TERMINATE_OPEN_CAPTURE(new UINT16(0x1018)),
    MOVE_OBJECT(new UINT16(0x1019)),
    COPY_OBJECT(new UINT16(0x101A)),
    GET_PARTIAL_OBJECT(new UINT16(0x101B)),
    INITIATE_OPEN_CAPTURER(new UINT16(0x101C));

    private static final Map<UINT16, OperationCode> operationCodeList = new HashMap<>();

    static {
        for (OperationCode operationCode : OperationCode.values()) {
            operationCodeList.put(operationCode.getCode(), operationCode);
        }
    }

    public OperationCode valueOf(UINT16 code) {
        if (!operationCodeList.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        return operationCodeList.get(code);
    }

    private final UINT16 code;

    public UINT16 getCode() {
        return code;
    }

    private OperationCode(UINT16 code) {
        this.code = code;
    }

    public static boolean isReservedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b0001_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 code) {
        byte msn = (byte) (code.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1001_0000;
    }
}
