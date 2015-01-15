package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.DataType;
import com.theta360.ptp.type.UINT16;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.*;

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
        if (formFlag != FormFlag.RANGE_FORM) {
            throw new UnsupportedOperationException();
        }

        return rangeForm;
    }

    public List<T> getEnumForm() {
        if (formFlag != FormFlag.ENUM_FORM) {
            throw new UnsupportedOperationException();
        }

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
        boolean isReadonly = readGetSet(pis);
        Object defaultValue = readAs(dataType, pis);
        Object currentValue = readAs(dataType, pis);
        FormFlag formFlag = FormFlag.valueOf((byte) pis.read());

        RangeForm<Object> rangeForm;
        List<Object> enumForm;
        switch (formFlag) {
            case NONE:
                rangeForm = null;
                enumForm = null;
                break;
            case RANGE_FORM:
                rangeForm = readRangeForm(dataType, pis);
                enumForm = null;
                break;
            case ENUM_FORM:
                rangeForm = null;
                enumForm = readEnumForm(dataType, pis);
                break;
            default:
                throw new RuntimeException("Unknown FormFlag: " + formFlag);
        }

        return new DevicePropDesc<Object>(devicePropCode, dataType, isReadonly, defaultValue, currentValue, formFlag, rangeForm, enumForm);
    }

    // Private Helper

    private static boolean readGetSet(PtpInputStream pis) throws IOException {
        switch (pis.read()) {
            case 0x00:
                return true;
            case 0x01:
                return false;
            default:
                throw new RuntimeException("Unknown GetSet Value");
        }
    }

    private static Object readAs(DataType dataType, PtpInputStream pis) throws IOException {
        switch (dataType) {
            case UINT8:
                return pis.read();
            case INT16:
                return pis.readINT16();
            case UINT16:
                return pis.readUINT16();
            case UINT32:
                return pis.readUINT32();
            case UINT64:
                return pis.readUINT64();
            case STR:
                return pis.readString();
            default:
                throw new RuntimeException(dataType + " is not supported.");
        }
    }

    private static RangeForm<Object> readRangeForm(DataType dataType, PtpInputStream pis) throws IOException {
        Object minValue = readAs(dataType, pis);
        Object maxValue = readAs(dataType, pis);
        Object stepSize = readAs(dataType, pis);

        return new RangeForm<>(minValue, maxValue, stepSize);
    }

    private static List<Object> readEnumForm(DataType dataType, PtpInputStream pis) throws IOException {
        int numOfValues = pis.readUINT16().intValue();

        List<? super Object> enumForm = new ArrayList<>();
        for (int i = 0; i < numOfValues; i++) {
            enumForm.add(readAs(dataType, pis));
        }

        return Collections.unmodifiableList(enumForm);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DevicePropDesc that = (DevicePropDesc) o;

        if (isReadonly != that.isReadonly) return false;
        if (!currentValue.equals(that.currentValue)) return false;
        if (dataType != that.dataType) return false;
        if (!defaultValue.equals(that.defaultValue)) return false;
        if (!devicePropCode.equals(that.devicePropCode)) return false;
        if (enumForm != null ? !enumForm.equals(that.enumForm) : that.enumForm != null) return false;
        if (formFlag != that.formFlag) return false;
        if (rangeForm != null ? !rangeForm.equals(that.rangeForm) : that.rangeForm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = devicePropCode.hashCode();
        result = 31 * result + dataType.hashCode();
        result = 31 * result + (isReadonly ? 1 : 0);
        result = 31 * result + defaultValue.hashCode();
        result = 31 * result + currentValue.hashCode();
        result = 31 * result + formFlag.hashCode();
        result = 31 * result + (rangeForm != null ? rangeForm.hashCode() : 0);
        result = 31 * result + (enumForm != null ? enumForm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DevicePropDesc{" +
                "devicePropCode=" + devicePropCode +
                ", dataType=" + dataType +
                ", isReadonly=" + isReadonly +
                ", defaultValue=" + defaultValue +
                ", currentValue=" + currentValue +
                ", formFlag=" + formFlag +
                ", rangeForm=" + rangeForm +
                ", enumForm=" + enumForm +
                '}';
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

        private static Map<Byte, FormFlag> formFlagMap = new HashMap<>();

        static {
            for (FormFlag formFlag : FormFlag.values()) {
                formFlagMap.put(formFlag.value, formFlag);
            }
        }

        public static FormFlag valueOf(byte value) {
            if (!formFlagMap.containsKey(value)) {
                throw new IllegalArgumentException("Unknown Form Flag Value: " + value);
            }

            return formFlagMap.get(value);
        }
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

        // Generic Method

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RangeForm rangeForm = (RangeForm) o;

            if (!maxValue.equals(rangeForm.maxValue)) return false;
            if (!minValue.equals(rangeForm.minValue)) return false;
            if (!stepSize.equals(rangeForm.stepSize)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = minValue.hashCode();
            result = 31 * result + maxValue.hashCode();
            result = 31 * result + stepSize.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "RangeForm{" +
                    "minValue=" + minValue +
                    ", maxValue=" + maxValue +
                    ", stepSize=" + stepSize +
                    '}';
        }
    }
}
