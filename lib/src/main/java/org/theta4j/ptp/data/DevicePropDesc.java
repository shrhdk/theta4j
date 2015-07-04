package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.DataType;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;
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

    // Static Factory Method

    public static DevicePropDesc<?> read(InputStream is) throws IOException {
        try (PtpInputStream pis = new PtpInputStream(is)) {
            return read(pis);
        }
    }

    public static DevicePropDesc<?> read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

        UINT16 devicePropCode = pis.readUINT16();
        DataType dataType = DataType.valueOf(pis.readUINT16());
        boolean isReadonly = readGetSet(pis);
        Object defaultValue = pis.readAs(dataType);
        Object currentValue = pis.readAs(dataType);
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
                throw new IllegalArgumentException("Unknown FormFlag: " + formFlag);
        }

        return new DevicePropDesc<Object>(devicePropCode, dataType, isReadonly, defaultValue, currentValue, formFlag, rangeForm, enumForm);
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

    // Private Helper

    private static boolean readGetSet(PtpInputStream pis) throws IOException {
        switch (pis.read()) {
            case 0x00:
                return true;
            case 0x01:
                return false;
            default:
                throw new IllegalArgumentException("Unknown GetSet Value");
        }
    }

    private static RangeForm<Object> readRangeForm(DataType dataType, PtpInputStream pis) throws IOException {
        Object minValue = pis.readAs(dataType);
        Object maxValue = pis.readAs(dataType);
        Object stepSize = pis.readAs(dataType);

        return new RangeForm<>(minValue, maxValue, stepSize);
    }

    private static List<Object> readEnumForm(DataType dataType, PtpInputStream pis) throws IOException {
        int numOfValues = pis.readUINT16().intValue();

        List<? super Object> enumForm = new ArrayList<>();
        for (int i = 0; i < numOfValues; i++) {
            enumForm.add(pis.readAs(dataType));
        }

        return Collections.unmodifiableList(enumForm);
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DevicePropDesc rhs = (DevicePropDesc) o;

        return new EqualsBuilder()
                .append(devicePropCode, rhs.devicePropCode)
                .append(dataType, rhs.dataType)
                .append(isReadonly, rhs.isReadonly)
                .append(defaultValue, rhs.defaultValue)
                .append(currentValue, rhs.currentValue)
                .append(formFlag, rhs.formFlag)
                .append(rangeForm, rhs.rangeForm)
                .append(enumForm, rhs.enumForm)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(devicePropCode)
                .append(dataType)
                .append(isReadonly)
                .append(defaultValue)
                .append(currentValue)
                .append(formFlag)
                .append(rangeForm)
                .append(enumForm)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
            Validators.notNull("value", value);

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
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            RangeForm rhs = (RangeForm) o;

            return new EqualsBuilder()
                    .append(minValue, rhs.minValue)
                    .append(maxValue, rhs.maxValue)
                    .append(stepSize, rhs.stepSize)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(minValue)
                    .append(maxValue)
                    .append(stepSize)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }
}
