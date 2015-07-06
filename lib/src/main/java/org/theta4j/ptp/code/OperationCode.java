package org.theta4j.ptp.code;

import org.theta4j.ptp.type.UINT16;

public enum OperationCode implements Code<UINT16> {
    UNDEFINED(0x1000),
    GET_DEVICE_INFO(0x1001),
    OPEN_SESSION(0x1002),
    CLOSE_SESSION(0x1003),
    GET_STORAGE_IDS(0x1004),
    GET_STORAGE_INFO(0x1005),
    GET_NUM_OBJECTS(0x1006),
    GET_OBJECT_HANDLES(0x1007),
    GET_OBJECT_INFO(0x1008),
    GET_OBJECT(0x1009),
    GET_THUMB(0x100A),
    DELETE_OBJECT(0x100B),
    SEND_OBJECT_INFO(0x100C),
    SEND_OBJECT(0x100D),
    INITIATE_CAPTURE(0x100E),
    FORMAT_STORE(0x100F),
    RESET_DEVICE(0x1010),
    SELF_TEST(0x1011),
    SET_OBJECT_PROTECTION(0x1012),
    POWER_DOWN(0x1013),
    GET_DEVICE_PROP_DESC(0x1014),
    GET_DEVICE_PROP_VALUE(0x1015),
    SET_DEVICE_PROP_VALUE(0x1016),
    RESET_DEVICE_PROP_VALUE(0x1017),
    TERMINATE_OPEN_CAPTURE(0x1018),
    MOVE_OBJECT(0x1019),
    COPY_OBJECT(0x101A),
    GET_PARTIAL_OBJECT(0x101B),
    INITIATE_OPEN_CAPTURER(0x101C);

    // Property

    private final UINT16 value;

    // Constructor

    OperationCode(int value) {
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
        return msn == (byte) 0b0001_0000;
    }

    public static boolean isVendorExtendedCode(UINT16 value) {
        byte msn = (byte) (value.bytes()[0] & (byte) 0xF0);
        return msn == (byte) 0b1001_0000;
    }
}
