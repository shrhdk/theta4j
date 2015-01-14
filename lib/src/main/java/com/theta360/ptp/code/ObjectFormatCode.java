package com.theta360.ptp.code;

import com.theta360.ptp.type.UINT16;

public enum ObjectFormatCode implements Code<UINT16> {
    UNKNOWN_NON_IMAGE(0x3000, Type.ANCILLARY_DATA_FILE),
    ASSOCIATION(0x3001, Type.ANCILLARY_DATA_FILE),
    SCRIPT(0x3002, Type.ANCILLARY_DATA_FILE),
    EXECUTABLE(0x3003, Type.ANCILLARY_DATA_FILE),
    TEXT(0x3004, Type.ANCILLARY_DATA_FILE),
    HTML(0x3005, Type.ANCILLARY_DATA_FILE),
    DPOF(0x3006, Type.ANCILLARY_DATA_FILE),
    AIFF(0x3007, Type.ANCILLARY_DATA_FILE),
    WAV(0x3008, Type.ANCILLARY_DATA_FILE),
    MP3(0x3009, Type.ANCILLARY_DATA_FILE),
    AVI(0x300A, Type.ANCILLARY_DATA_FILE),
    MPEG(0x300B, Type.ANCILLARY_DATA_FILE),
    ASF(0x300C, Type.ANCILLARY_DATA_FILE),
    UNKNOWN_IMAGE(0x3800, Type.IMAGE_FILE),
    EXIF_JPEG(0x3801, Type.IMAGE_FILE),
    TIFF_EP(0x3802, Type.IMAGE_FILE),
    FLASHPIX(0x3803, Type.IMAGE_FILE),
    BMP(0x3804, Type.IMAGE_FILE),
    CIFF(0x3805, Type.IMAGE_FILE),
    RESERVED(0x3806, Type.IMAGE_FILE),
    GIF(0x3807, Type.IMAGE_FILE),
    JFIF(0x3808, Type.IMAGE_FILE),
    PCD(0x3809, Type.IMAGE_FILE),
    PICT(0x380A, Type.IMAGE_FILE),
    PNG(0x380B, Type.IMAGE_FILE),
    UNDEFINED(0x380C, Type.IMAGE_FILE),
    TIFF(0x380D, Type.IMAGE_FILE),
    TIFF_IT(0x380E, Type.IMAGE_FILE),
    JP2(0x380F, Type.IMAGE_FILE),
    JPX(0x3810, Type.IMAGE_FILE);

    private final UINT16 value;
    private final Type type;

    private ObjectFormatCode(int value, Type type) {
        this.value = new UINT16(value);
        this.type = type;
    }

    // Getter

    public Type type() {
        return type;
    }

    // Code

    @Override
    public UINT16 value() {
        return value;
    }

    // Inner Enum

    public enum Type {
        IMAGE_FILE, ANCILLARY_DATA_FILE;
    }
}
