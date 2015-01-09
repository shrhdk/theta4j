package com.theta360.ptp.data;

import com.theta360.ptp.type.ConvertException;
import com.theta360.ptp.io.GenericDataTypeInputStream;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.io.IOException;
import java.util.List;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceInfo that = (DeviceInfo) o;

        if (!captureFormats.equals(that.captureFormats)) return false;
        if (!devicePropertiesSupported.equals(that.devicePropertiesSupported)) return false;
        if (!deviceVersion.equals(that.deviceVersion)) return false;
        if (!eventsSupported.equals(that.eventsSupported)) return false;
        if (!functionalMode.equals(that.functionalMode)) return false;
        if (!imageFormats.equals(that.imageFormats)) return false;
        if (!manufacturer.equals(that.manufacturer)) return false;
        if (!model.equals(that.model)) return false;
        if (!operationsSupported.equals(that.operationsSupported)) return false;
        if (!serialNumber.equals(that.serialNumber)) return false;
        if (!standardVersion.equals(that.standardVersion)) return false;
        if (!vendorExtensionDesc.equals(that.vendorExtensionDesc)) return false;
        if (!vendorExtensionID.equals(that.vendorExtensionID)) return false;
        if (!vendorExtensionVersion.equals(that.vendorExtensionVersion)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = standardVersion.hashCode();
        result = 31 * result + vendorExtensionID.hashCode();
        result = 31 * result + vendorExtensionVersion.hashCode();
        result = 31 * result + vendorExtensionDesc.hashCode();
        result = 31 * result + functionalMode.hashCode();
        result = 31 * result + operationsSupported.hashCode();
        result = 31 * result + eventsSupported.hashCode();
        result = 31 * result + devicePropertiesSupported.hashCode();
        result = 31 * result + captureFormats.hashCode();
        result = 31 * result + imageFormats.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + model.hashCode();
        result = 31 * result + deviceVersion.hashCode();
        result = 31 * result + serialNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "standardVersion=" + standardVersion +
                ", vendorExtensionID=" + vendorExtensionID +
                ", vendorExtensionVersion=" + vendorExtensionVersion +
                ", vendorExtensionDesc='" + vendorExtensionDesc + '\'' +
                ", functionalMode=" + functionalMode +
                ", operationsSupported=" + operationsSupported +
                ", eventsSupported=" + eventsSupported +
                ", devicePropertiesSupported=" + devicePropertiesSupported +
                ", captureFormats=" + captureFormats +
                ", imageFormats=" + imageFormats +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", deviceVersion='" + deviceVersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }

    public static DeviceInfo valueOf(byte[] bytes) throws ConvertException {
        GenericDataTypeInputStream is = new GenericDataTypeInputStream(bytes);

        try {
            UINT16 standardVersion = is.readUINT16();
            UINT32 vendorExtensionID = is.readUINT32();
            UINT16 vendorExtensionVersion = is.readUINT16();
            String vendorExtensionDesc = is.readString();
            UINT16 functionalMode = is.readUINT16();
            List<UINT16> operationsSupported = is.readAUINT16();
            List<UINT16> eventsSupported = is.readAUINT16();
            List<UINT16> devicePropertiesSupported = is.readAUINT16();
            List<UINT16> captureFormats = is.readAUINT16();
            List<UINT16> imageFormats = is.readAUINT16();
            String manufacturer = is.readString();
            String model = is.readString();
            String deviceVersion = is.readString();
            String serialNumber = is.readString();

            return new DeviceInfo(standardVersion,
                    vendorExtensionID, vendorExtensionVersion, vendorExtensionDesc,
                    functionalMode, operationsSupported, eventsSupported, devicePropertiesSupported,
                    captureFormats, imageFormats,
                    manufacturer, model, deviceVersion, serialNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
