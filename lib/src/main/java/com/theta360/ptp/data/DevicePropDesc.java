package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.util.Validators;

import java.io.IOException;

public class DevicePropDesc {
    private final UINT16 devicePropCode;
    private final UINT16 dataType;
    private final boolean isReadonly;
    private final byte[] defaultValue;
    private final byte[] currentValue;
    private final FormFlag formFlag;
    private final RangeForm rangeForm;
    private final EnumForm enumForm;

    // Constructor

    public DevicePropDesc(UINT16 devicePropCode, UINT16 dataType, boolean isReadonly,
                          byte[] defaultValue, byte[] currentValue,
                          FormFlag formFlag, RangeForm rangeForm, EnumForm enumForm
    ) {
        this.devicePropCode = devicePropCode;
        this.dataType = dataType;
        this.isReadonly = isReadonly;
        this.defaultValue = defaultValue.clone();
        this.currentValue = currentValue.clone();
        this.formFlag = formFlag;
        this.rangeForm = rangeForm;
        this.enumForm = enumForm;
    }

    // Getter

    public UINT16 getDevicePropCode() {
        return devicePropCode;
    }

    public UINT16 getDataType() {
        return dataType;
    }

    public boolean isReadonly() {
        return isReadonly;
    }

    public byte[] getDefaultValue() {
        return defaultValue.clone();
    }

    public byte[] getCurrentValue() {
        return currentValue.clone();
    }

    public FormFlag getFormFlag() {
        return formFlag;
    }

    public RangeForm getRangeForm() {
        return rangeForm;
    }

    public EnumForm getEnumForm() {
        return enumForm;
    }

    // Static Factory Method

    public static DevicePropDesc valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DevicePropDesc read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        throw new UnsupportedOperationException();
    }

    // Related Class and Enum

    public enum FormFlag {
        NONE(0x00),
        RANGE_FORM(0x01),
        ENUM_FORM(0x02);

        private final byte value;

        private FormFlag(int value) {
            this.value = (byte) value;
        }

        // valueOf
    }

    public static class RangeForm {

    }

    public static class EnumForm {

    }
}
