package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.DataType;
import com.theta360.ptp.type.UINT16;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.List;

public class DevicePropDesc<T> {
    private final UINT16 devicePropCode;
    private final DataType dataType;
    private final boolean isReadonly;
    private final T defaultValue;
    private final T currentValue;
    private final FormFlag formFlag;
    private final RangeForm<T> rangeForm;
    private final List<T> enumForm;

    // Constructor

    public DevicePropDesc(UINT16 devicePropCode, DataType dataType, boolean isReadonly,
                          T defaultValue, T currentValue,
                          FormFlag formFlag, RangeForm<T> rangeForm, List<T> enumForm
    ) {
        this.devicePropCode = devicePropCode;
        this.dataType = dataType;
        this.isReadonly = isReadonly;
        this.defaultValue = defaultValue;
        this.currentValue = currentValue;
        this.formFlag = formFlag;
        this.rangeForm = rangeForm;
        this.enumForm = enumForm;
    }

    // Getter

    public UINT16 getDevicePropCode() {
        return devicePropCode;
    }

    public DataType getDataType() {
        return dataType;
    }

    public boolean isReadonly() {
        return isReadonly;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getCurrentValue() {
        return currentValue;
    }

    public FormFlag getFormFlag() {
        return formFlag;
    }

    public RangeForm getRangeForm() {
        return rangeForm;
    }

    public List<T> getEnumForm() {
        return enumForm;
    }

    // Static Factory Method

    public static DevicePropDesc<?> valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DevicePropDesc<?> read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        UINT16 devicePropCode = pis.readUINT16();
        DataType dataType = DataType.valueOf(pis.readUINT16());
        boolean isReadonly;
        switch (pis.read()) {
            case 0x00:
                isReadonly = true;
                break;
            case 0x01:
                isReadonly = false;
                break;
            default:
                throw new RuntimeException("Unknown GetSet Value");
        }

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

    public static class RangeForm<T> {
        private final T minValue;
        private final T maxValue;
        private final T stepSize;

        // Constructor

        public RangeForm(T minValue, T maxValue, T stepSize) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.stepSize = stepSize;
        }

        // Getter

        public T getMinValue() {
            return minValue;
        }

        public T getMaxValue() {
            return maxValue;
        }

        public T getStepSize() {
            return stepSize;
        }
    }
}
