/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The device information defined in PTP standard.
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

    /**
     * Constructs new device information object.
     *
     * @param standardVersion           The standard version value of the device.
     * @param vendorExtensionID         The vendor extension ID of the device.
     * @param vendorExtensionVersion    The vendor extension version of the device.
     * @param vendorExtensionDesc       The vendor extension description of the device.
     * @param functionalMode            The functional mode value of the device.
     * @param operationsSupported       The list of operations supported by the device.
     * @param eventsSupported           The list of events supported by the device.
     * @param devicePropertiesSupported The list of device properties supported by the device.
     * @param captureFormats            The list of capture formats supported by the device.
     * @param imageFormats              The list of image formats supported by the device.
     * @param manufacturer              The manufacturer name of the device.
     * @param model                     The model name of the device.
     * @param deviceVersion             The device description of the device.
     * @param serialNumber              The serial number of the device.
     * @throws NullPointerException if an argument is null.
     */
    public DeviceInfo(
            UINT16 standardVersion,
            UINT32 vendorExtensionID, UINT16 vendorExtensionVersion, String vendorExtensionDesc,
            UINT16 functionalMode,
            List<UINT16> operationsSupported, List<UINT16> eventsSupported, List<UINT16> devicePropertiesSupported,
            List<UINT16> captureFormats, List<UINT16> imageFormats,
            String manufacturer, String model, String deviceVersion, String serialNumber) {
        Validators.notNull("standardVersion", standardVersion);
        Validators.notNull("vendorExtensionID", vendorExtensionID);
        Validators.notNull("vendorExtensionVersion", vendorExtensionVersion);
        Validators.notNull("vendorExtensionDesc", vendorExtensionDesc);
        Validators.notNull("functionalMode", functionalMode);
        Validators.notNull("operationsSupported", operationsSupported);
        Validators.notNull("eventsSupported", eventsSupported);
        Validators.notNull("devicePropertiesSupported", devicePropertiesSupported);
        Validators.notNull("captureFormats", captureFormats);
        Validators.notNull("imageFormats", imageFormats);
        Validators.notNull("manufacturer", manufacturer);
        Validators.notNull("model", model);
        Validators.notNull("deviceVersion", deviceVersion);
        Validators.notNull("serialNumber", serialNumber);

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
     * Construct new device information object from InputStream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public static DeviceInfo read(InputStream is) throws IOException {
        try (PtpInputStream pis = new PtpInputStream(is)) {
            return read(pis);
        }
    }

    /**
     * Constructs new device information object from PtpInputStream.
     *
     * @throws IOException          if an I/O error occurs while reading the stream.
     * @throws NullPointerException if an argument is null.
     */
    public static DeviceInfo read(PtpInputStream pis) throws IOException {
        Validators.notNull("pis", pis);

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

    /**
     * Returns the standard version value of the device.
     */
    public UINT16 getStandardVersion() {
        return standardVersion;
    }

    /**
     * Returns the vendor extension ID of the device.
     */
    public UINT32 getVendorExtensionID() {
        return vendorExtensionID;
    }

    /**
     * Returns the vendor extension version of the device.
     */
    public UINT16 getVendorExtensionVersion() {
        return vendorExtensionVersion;
    }

    /**
     * Returns the vendor extension description of the device.
     */
    public String getVendorExtensionDesc() {
        return vendorExtensionDesc;
    }

    /**
     * Returns the functional mode value of the device.
     */
    public UINT16 getFunctionalMode() {
        return functionalMode;
    }

    /**
     * Returns the list of operations supported by the device.
     */
    public List<UINT16> getOperationsSupported() {
        return operationsSupported;
    }

    /**
     * Returns the list of events supported by the device.
     */
    public List<UINT16> getEventsSupported() {
        return eventsSupported;
    }

    /**
     * Returns the list of device properties supported by the device.
     */
    public List<UINT16> getDevicePropertiesSupported() {
        return devicePropertiesSupported;
    }

    /**
     * Returns the list of capture formats supported by the device.
     */
    public List<UINT16> getCaptureFormats() {
        return captureFormats;
    }

    /**
     * Returns the list of image formats supported by the device.
     */
    public List<UINT16> getImageFormats() {
        return imageFormats;
    }

    /**
     * Returns the manufacturer name of the device.
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Returns the model name of the device.
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the device description.
     */
    public String getDeviceVersion() {
        return deviceVersion;
    }

    /**
     * Returns the serial number of the device.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    // Basic Method

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
