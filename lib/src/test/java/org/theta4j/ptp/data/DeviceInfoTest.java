/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.data;

import org.junit.Test;
import org.theta4j.ptp.type.STR;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class DeviceInfoTest {
    private static final List<UINT16> LIST_1 = Arrays.asList(UINT16.MIN_VALUE);
    private static final List<UINT16> LIST_2 = Arrays.asList(UINT16.MAX_VALUE);

    private static final UINT16 STANDARD_VERSION = new UINT16(0);
    private static final UINT32 VENDOR_EXTENSION_ID = new UINT32(1);
    private static final UINT16 VENDOR_EXTENSION_VERSION = new UINT16(2);
    private static final String VENDOR_EXTENSION_DESC = "VendorExtensionDesc";
    private static final UINT16 FUNCTIONAL_MODE = new UINT16(3);
    private static final List<UINT16> OPERATIONS_SUPPORTED = new ArrayList<>();
    private static final List<UINT16> EVENTS_SUPPORTED = new ArrayList<>();
    private static final List<UINT16> DEVICE_PROPERTIES_SUPPORTED = new ArrayList<>();
    private static final List<UINT16> CAPTURE_FORMATS = new ArrayList<>();
    private static final List<UINT16> IMAGE_FORMATS = new ArrayList<>();
    private static final String MANUFACTURER = "Manufacturer";
    private static final String MODEL = "Model";
    private static final String DEVICE_VERSION = "DeviceVersion";
    private static final String SERIAL_NUMBER = "SerialNumber";

    // Construct with error

    @Test(expected = NullPointerException.class)
    public void withNullStandardVersion() {
        // act
        new DeviceInfo(
                null, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullVendorExtensionID() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, null, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullVendorExtensionVersion() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, null, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullVendorExtensionDesc() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, null,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullFunctionalMode() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                null, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullOperationsSupported() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, null, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullEventsSupported() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, null, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullDevicePropertiesSupported() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, null,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullCaptureFormats() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                null, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullManufacturer() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, null, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullModel() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, null, DEVICE_VERSION, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullDeviceVersion() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, null, SERIAL_NUMBER);
    }

    @Test(expected = NullPointerException.class)
    public void withNullSerialNumber() {
        // act
        new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, null);
    }

    // Construct

    @Test
    public void constructAndGet() {
        // act
        DeviceInfo actual = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // verify
        assertThat(actual.getStandardVersion(), is(STANDARD_VERSION));
        assertThat(actual.getVendorExtensionID(), is(VENDOR_EXTENSION_ID));
        assertThat(actual.getVendorExtensionVersion(), is(VENDOR_EXTENSION_VERSION));
        assertThat(actual.getVendorExtensionDesc(), is(VENDOR_EXTENSION_DESC));
        assertThat(actual.getFunctionalMode(), is(FUNCTIONAL_MODE));
        assertThat(actual.getOperationsSupported(), is(OPERATIONS_SUPPORTED));
        assertThat(actual.getEventsSupported(), is(EVENTS_SUPPORTED));
        assertThat(actual.getDevicePropertiesSupported(), is(DEVICE_PROPERTIES_SUPPORTED));
        assertThat(actual.getCaptureFormats(), is(CAPTURE_FORMATS));
        assertThat(actual.getImageFormats(), is(IMAGE_FORMATS));
        assertThat(actual.getManufacturer(), is(MANUFACTURER));
        assertThat(actual.getModel(), is(MODEL));
        assertThat(actual.getDeviceVersion(), is(DEVICE_VERSION));
        assertThat(actual.getSerialNumber(), is(SERIAL_NUMBER));
    }

    // read

    @Test
    public void read() throws IOException {
        // given
        byte[] given = ArrayUtils.join(
                STANDARD_VERSION.bytes(),
                VENDOR_EXTENSION_ID.bytes(),
                VENDOR_EXTENSION_VERSION.bytes(),
                STR.toBytes(VENDOR_EXTENSION_DESC),
                FUNCTIONAL_MODE.bytes(),
                UINT32.ZERO.bytes(),
                UINT32.ZERO.bytes(),
                UINT32.ZERO.bytes(),
                UINT32.ZERO.bytes(),
                UINT32.ZERO.bytes(),
                STR.toBytes(MANUFACTURER),
                STR.toBytes(MODEL),
                STR.toBytes(DEVICE_VERSION),
                STR.toBytes(SERIAL_NUMBER)
        );

        // expected
        DeviceInfo expected = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // arrange
        InputStream givenInputStream = new ByteArrayInputStream(given);

        // act
        DeviceInfo actual = DeviceInfo.read(givenInputStream);

        // verify
        assertThat(actual, is(expected));
    }

    // hashCode

    @Test
    public void hashCodeOfDifferentStandardVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                new UINT16(0), VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                new UINT16(1), VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentVendorExtensionID() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, new UINT32(0), VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, new UINT32(1), VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentVendorExtensionVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, new UINT16(0), VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, new UINT16(1), VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentVendorExtensionDesc() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, "foo",
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, "bar",
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentFunctionalMode() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                new UINT16(0), OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                new UINT16(1), OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentOperationsSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, LIST_1, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, LIST_2, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentEventsSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, LIST_1, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, LIST_2, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentDevicePropertiesSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, LIST_1,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, LIST_2,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentCaptureFormats() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                LIST_1, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                LIST_2, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentImageFormats() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, LIST_1, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, LIST_2, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentManufacturer() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, "foo", MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, "bar", MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentModel() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, "foo", DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, "bar", DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentDeviceVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, "foo", SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, "bar", SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void hashCodeOfDifferentSerialNumber() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, "foo");
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, "bar");

        // act & verify
        assertThat(deviceInfo1.hashCode(), not(deviceInfo2.hashCode()));
    }

    @Test
    public void testHashCode() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertThat(deviceInfo1.hashCode(), is(deviceInfo2.hashCode()));
    }

    // not equals

    @Test
    public void notEqualsWithNull() {
        // given
        DeviceInfo deviceInfo = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo.equals(null));
    }

    @Test
    public void notEqualsWithDifferentClass() {
        // given
        DeviceInfo deviceInfo = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo.equals("foo"));
    }

    @Test
    public void notEqualsWithStandardVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                new UINT16(0), VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                new UINT16(1), VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithVendorExtensionID() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, new UINT32(0), VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, new UINT32(1), VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithVendorExtensionVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, new UINT16(0), VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, new UINT16(1), VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithVendorExtensionDesc() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, "foo",
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, "bar",
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithFunctionalMode() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                new UINT16(0), OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                new UINT16(1), OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithOperationsSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, LIST_1, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, LIST_2, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithEventsSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, LIST_1, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, LIST_2, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithDevicePropertiesSupported() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, LIST_1,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, LIST_2,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithCaptureFormats() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                LIST_1, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                LIST_2, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithImageFormats() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, LIST_1, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, LIST_2, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithManufacturer() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, "foo", MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, "bar", MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithModel() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, "foo", DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, "bar", DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithDeviceVersion() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, "foo", SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, "bar", SERIAL_NUMBER);

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    @Test
    public void notEqualsWithSerialNumber() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, "foo");
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, "bar");

        // act & verify
        assertFalse(deviceInfo1.equals(deviceInfo2));
    }

    // equals

    @Test
    public void equalsWithSameInstance() {
        // given
        DeviceInfo deviceInfo = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertTrue(deviceInfo.equals(deviceInfo));
    }

    @Test
    public void equals() {
        // given
        DeviceInfo deviceInfo1 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);
        DeviceInfo deviceInfo2 = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act & verify
        assertTrue(deviceInfo1.equals(deviceInfo2));
    }

    // toString

    @Test
    public void testToString() {
        // given
        DeviceInfo deviceInfo = new DeviceInfo(
                STANDARD_VERSION, VENDOR_EXTENSION_ID, VENDOR_EXTENSION_VERSION, VENDOR_EXTENSION_DESC,
                FUNCTIONAL_MODE, OPERATIONS_SUPPORTED, EVENTS_SUPPORTED, DEVICE_PROPERTIES_SUPPORTED,
                CAPTURE_FORMATS, IMAGE_FORMATS, MANUFACTURER, MODEL, DEVICE_VERSION, SERIAL_NUMBER);

        // act
        String actual = deviceInfo.toString();

        // verify
        assertTrue(actual.contains(DeviceInfo.class.getSimpleName()));
        assertTrue(actual.contains("standardVersion"));
        assertTrue(actual.contains("vendorExtensionID"));
        assertTrue(actual.contains("vendorExtensionVersion"));
        assertTrue(actual.contains("vendorExtensionDesc"));
        assertTrue(actual.contains("functionalMode"));
        assertTrue(actual.contains("operationsSupported"));
        assertTrue(actual.contains("eventsSupported"));
        assertTrue(actual.contains("devicePropertiesSupported"));
        assertTrue(actual.contains("captureFormats"));
        assertTrue(actual.contains("imageFormats"));
        assertTrue(actual.contains("manufacturer"));
        assertTrue(actual.contains("model"));
        assertTrue(actual.contains("deviceVersion"));
        assertTrue(actual.contains("serialNumber"));
    }
}
