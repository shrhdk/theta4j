package com.theta360.ptp.data;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.util.List;

/**
 * DeviceInfo data set
 * <p/>
 * The DeviceInfo data set defined in PTP
 */
public final class DeviceInfo {
    private final UINT16 standardVersion;
    private final UINT32 vendorExtensionID;
    private final UINT16 vendorExtensionVersion;
    private final String vendorExtensionDesc;
    private final UINT16 functionalMode;
    private final List<UINT16> operationsSupported;
    private final List<UINT16> eventsSupported;
    private final List<UINT16> devicePropertiesSupported;
    private final List<UINT16> captureFormats;
    private final List<UINT16> imageFormats;
    private final String manufacturer;
    private final String model;
    private final String deviceVersion;
    private final String serialNumber;

    // Constructor

    public DeviceInfo(
            UINT16 standardVersion,
            UINT32 vendorExtensionID, UINT16 vendorExtensionVersion, String vendorExtensionDesc,
            UINT16 functionalMode,
            List<UINT16> operationsSupported, List<UINT16> eventsSupported, List<UINT16> devicePropertiesSupported,
            List<UINT16> captureFormats, List<UINT16> imageFormats,
            String manufacturer, String model, String deviceVersion, String serialNumber) {
        Validators.validateNonNull("standardVersion", standardVersion);
        Validators.validateNonNull("vendorExtensionID", vendorExtensionID);
        Validators.validateNonNull("vendorExtensionVersion", vendorExtensionVersion);
        Validators.validateNonNull("vendorExtensionDesc", vendorExtensionDesc);
        Validators.validateNonNull("functionalMode", functionalMode);
        Validators.validateNonNull("operationsSupported", operationsSupported);
        Validators.validateNonNull("eventsSupported", eventsSupported);
        Validators.validateNonNull("devicePropertiesSupported", devicePropertiesSupported);
        Validators.validateNonNull("captureFormats", captureFormats);
        Validators.validateNonNull("imageFormats", imageFormats);
        Validators.validateNonNull("manufacturer", manufacturer);
        Validators.validateNonNull("model", model);
        Validators.validateNonNull("deviceVersion", deviceVersion);
        Validators.validateNonNull("serialNumber", serialNumber);

        this.standardVersion = standardVersion;
        this.vendorExtensionID = vendorExtensionID;
        this.vendorExtensionVersion = vendorExtensionVersion;
        this.vendorExtensionDesc = vendorExtensionDesc;
        this.functionalMode = functionalMode;
        this.operationsSupported = operationsSupported;
        this.eventsSupported = eventsSupported;
        this.devicePropertiesSupported = devicePropertiesSupported;
        this.captureFormats = captureFormats;
        this.imageFormats = imageFormats;
        this.manufacturer = manufacturer;
        this.model = model;
        this.deviceVersion = deviceVersion;
        this.serialNumber = serialNumber;
    }

    // Static Factory Method

    /**
     * Construct DeviceInfo from byte array.
     */
    public static DeviceInfo valueOf(byte[] bytes) {
        Validators.validateNonNull("bytes", bytes);

        try (PtpInputStream pis = new PtpInputStream(bytes)) {
            return read(pis);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Construct DeviceInfo from PtpInputStream.
     *
     * @throws IOException
     */
    public static DeviceInfo read(PtpInputStream pis) throws IOException {
        Validators.validateNonNull("pis", pis);

        UINT16 standardVersion = pis.readUINT16();
        UINT32 vendorExtensionID = pis.readUINT32();
        UINT16 vendorExtensionVersion = pis.readUINT16();
        String vendorExtensionDesc = pis.readString();
        UINT16 functionalMode = pis.readUINT16();
        List<UINT16> operationsSupported = pis.readAUINT16();
        List<UINT16> eventsSupported = pis.readAUINT16();
        List<UINT16> devicePropertiesSupported = pis.readAUINT16();
        List<UINT16> captureFormats = pis.readAUINT16();
        List<UINT16> imageFormats = pis.readAUINT16();
        String manufacturer = pis.readString();
        String model = pis.readString();
        String deviceVersion = pis.readString();
        String serialNumber = pis.readString();

        return new DeviceInfo(standardVersion,
                vendorExtensionID, vendorExtensionVersion, vendorExtensionDesc,
                functionalMode, operationsSupported, eventsSupported, devicePropertiesSupported,
                captureFormats, imageFormats,
                manufacturer, model, deviceVersion, serialNumber);
    }

    // Getter

    public UINT16 getStandardVersion() {
        return standardVersion;
    }

    public UINT32 getVendorExtensionID() {
        return vendorExtensionID;
    }

    public UINT16 getVendorExtensionVersion() {
        return vendorExtensionVersion;
    }

    public String getVendorExtensionDesc() {
        return vendorExtensionDesc;
    }

    public UINT16 getFunctionalMode() {
        return functionalMode;
    }

    public List<UINT16> getOperationsSupported() {
        return operationsSupported;
    }

    public List<UINT16> getEventsSupported() {
        return eventsSupported;
    }

    public List<UINT16> getDevicePropertiesSupported() {
        return devicePropertiesSupported;
    }

    public List<UINT16> getCaptureFormats() {
        return captureFormats;
    }

    public List<UINT16> getImageFormats() {
        return imageFormats;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public String getSerialNumber() {
        return serialNumber;
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

        DeviceInfo rhs = (DeviceInfo) o;

        return new EqualsBuilder()
                .append(standardVersion, rhs.standardVersion)
                .append(vendorExtensionID, rhs.vendorExtensionID)
                .append(vendorExtensionVersion, rhs.vendorExtensionVersion)
                .append(vendorExtensionDesc, rhs.vendorExtensionDesc)
                .append(functionalMode, rhs.functionalMode)
                .append(operationsSupported, rhs.operationsSupported)
                .append(eventsSupported, rhs.eventsSupported)
                .append(devicePropertiesSupported, rhs.devicePropertiesSupported)
                .append(captureFormats, rhs.captureFormats)
                .append(imageFormats, rhs.imageFormats)
                .append(manufacturer, rhs.manufacturer)
                .append(model, rhs.model)
                .append(deviceVersion, rhs.deviceVersion)
                .append(serialNumber, rhs.serialNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(standardVersion)
                .append(vendorExtensionID)
                .append(vendorExtensionVersion)
                .append(vendorExtensionDesc)
                .append(functionalMode)
                .append(operationsSupported)
                .append(eventsSupported)
                .append(devicePropertiesSupported)
                .append(captureFormats)
                .append(imageFormats)
                .append(manufacturer)
                .append(model)
                .append(deviceVersion)
                .append(serialNumber)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
