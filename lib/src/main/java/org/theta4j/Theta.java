package org.theta4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.data.*;
import org.theta4j.ptp.PtpEventListener;
import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.PtpInitiator;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.data.ObjectInfo;
import org.theta4j.ptp.data.StorageInfo;
import org.theta4j.ptp.type.*;
import org.theta4j.ptpip.PtpIpInitiator;
import org.theta4j.util.Validators;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * This class provides the interface for RICOH THETA on PTP-IP.
 */
public final class Theta implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Theta.class);

    private static final UINT32 SESSION_ID = new UINT32(1);
    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int TCP_PORT = 15740;

    private static final String DATE_TIME_FORMAT = "yyyyMMdd'T'HHmmss";

    private final PtpInitiator ptpInitiator;
    private final ThetaEventListenerSet listenerSet = new ThetaEventListenerSet();

    public Theta() throws IOException {
        ptpInitiator = new PtpIpInitiator(UUID.randomUUID(), IP_ADDRESS, TCP_PORT);

        ptpInitiator.addListener(new PtpEventListener() {
            @Override
            public void onCancelTransaction() {
                LOGGER.warn("Unsupported Event: CancelTransaction");
            }

            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                listenerSet.onObjectAdded(objectHandle.longValue());
            }

            @Override
            public void onObjectRemoved(UINT32 objectHandle) {
                LOGGER.warn("Unsupported Event: ObjectRemoved");
            }

            @Override
            public void onStoreAdded(UINT32 storageID) {
                LOGGER.warn("Unsupported Event: StoreAdded");
            }

            @Override
            public void onStoreRemoved(UINT32 storageID) {
                LOGGER.warn("Unsupported Event: StoreRemoved");
            }

            @Override
            public void onDevicePropChanged(UINT16 devicePropCode) {
                if (DevicePropCode.CAPTURE_STATUS.value().equals(devicePropCode)) {
                    listenerSet.onCaptureStatusChanged();
                } else if (DevicePropCode.RECORDING_TIME.value().equals(devicePropCode)) {
                    listenerSet.onRecordingTimeChanged();
                } else if (DevicePropCode.REMAINING_RECORDING_TIME.value().equals(devicePropCode)) {
                    listenerSet.onRemainingRecordingTimeChanged();
                } else {
                    LOGGER.warn("Unknown DevicePropCode: " + devicePropCode);
                }
            }

            @Override
            public void onObjectInfoChanged(UINT32 objectHandle) {
                LOGGER.warn("Unsupported Event: ObjectInfoChanged");
            }

            @Override
            public void onDeviceInfoChanged() {
                LOGGER.warn("Unsupported Event: DeviceInfoChanged");
            }

            @Override
            public void onRequestObjectTransfer(UINT32 objectHandle) {
                LOGGER.warn("Unsupported Event: RequestObjectTransfer");
            }

            @Override
            public void onStoreFull(UINT32 storageID) {
                listenerSet.onStoreFull(storageID.longValue());
            }

            @Override
            public void onDeviceReset() {
                LOGGER.warn("Unsupported Event: DeviceReset");
            }

            @Override
            public void onStorageInfoChanged(UINT32 storageID) {
                LOGGER.warn("Unsupported Event: StorageInfoChanged");
            }

            @Override
            public void onCaptureComplete(UINT32 transactionID) {
                listenerSet.onCaptureComplete(transactionID.longValue());
            }

            @Override
            public void onUnreportedStatus() {
                LOGGER.warn("Unsupported Event: UnreportedStatus");
            }

            @Override
            public void onVendorExtendedCode(Event event) {
                LOGGER.warn("Unsupported Event: VendorExtendedCode");
            }

            @Override
            public void onError(Exception e) {
                LOGGER.warn("Unsupported Event: Error");
            }
        });

        try {
            ptpInitiator.openSession(SESSION_ID);
        } catch (PtpException e) {
            throw new RuntimeException(e);
        }
    }

    // Operation

    /**
     * Returns information and capabilities about the Responder device.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public DeviceInfo getDeviceInfo() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDeviceInfo();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Returns a list of the currently valid StorageIDs.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public List<Long> getStorageIDs() throws IOException, ThetaException {
        try {
            return PrimitiveUtils.convert(ptpInitiator.getStorageIDs());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Returns a StorageInfo of the storage area indicated in the storageID.
     *
     * @param storageID The StorageID of the storage area to acquire the StorageInfo.
     * @throws IOException
     * @throws ThetaException
     */
    public StorageInfo getStorageInfo(long storageID) throws IOException, ThetaException {
        try {
            return ptpInitiator.getStorageInfo(new UINT32(storageID));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Returns the total number of objects present in the all storage.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public long getNumObjects() throws IOException, ThetaException {
        try {
            return ptpInitiator.getNumObjects().longValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Returns a list of the object handles.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public List<Long> getObjectHandles() throws IOException, ThetaException {
        try {
            return PrimitiveUtils.convert(ptpInitiator.getObjectHandles());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Returns a ObjectInfo for the object specified by the objectHandle.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the ObjectInfo.
     * @throws IOException
     * @throws ThetaException
     */
    public ObjectInfo getObjectInfo(long objectHandle) throws IOException, ThetaException {
        try {
            return ptpInitiator.getObjectInfo(new UINT32(objectHandle));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Retrieves the object's data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the data.
     * @param dst          The destination for the object's data.
     * @throws IOException
     * @throws ThetaException
     */
    public void getObject(long objectHandle, OutputStream dst) throws IOException, ThetaException {
        try {
            ptpInitiator.getObject(new UINT32(objectHandle), dst);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Retrieves the object's thumbnail data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the thumbnail data.
     * @param dst          The destination for the object's thumbnail data.
     * @throws IOException
     * @throws ThetaException
     */
    public void getThumb(long objectHandle, OutputStream dst) throws IOException, ThetaException {
        try {
            ptpInitiator.getThumb(new UINT32(objectHandle), dst);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Deletes the object specified by the ObjectHandle.
     *
     * @param objectHandle The ObjectHandle of the object to delete.
     * @throws IOException
     * @throws ThetaException
     */
    public void deleteObject(long objectHandle) throws IOException, ThetaException {
        try {
            ptpInitiator.deleteObject(new UINT32(objectHandle));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Starts shooting.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public void initiateCapture() throws IOException, ThetaException {
        try {
            ptpInitiator.initiateCapture();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Exits the all continuous shooting.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public void terminateOpenCapture() throws IOException, ThetaException {
        try {
            ptpInitiator.terminateOpenCapture();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Exits a continuous shooting specified by TransactionID.
     *
     * @param transactionID The TransactionID returned by Theta#initiateOpenCapture().
     * @throws IOException
     * @throws ThetaException
     * @see #initiateOpenCapture()
     */
    public void terminateOpenCapture(long transactionID) throws IOException, ThetaException {
        try {
            ptpInitiator.terminateOpenCapture(new UINT32(transactionID));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Starts the video recording or the interval shooting.
     *
     * After starts, it can exit by the #terminateOpenCapture(long)
     *
     * @throws IOException
     * @throws ThetaException
     * @see #terminateOpenCapture(long)
     */
    public long initiateOpenCapture() throws IOException, ThetaException {
        try {
            return ptpInitiator.initiateOpenCapture().longValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Retrieves the object's resized data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the resized data.
     * @param dst          The destination for the object's resized data.
     * @throws IOException
     */
    public void getResizedImageObject(long objectHandle, OutputStream dst) throws IOException, ThetaException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        ptpInitiator.sendOperation(OperationCode.GET_RESIZED_IMAGE_OBJECT, new UINT32(objectHandle), new UINT32(2048), new UINT32(1024));

        try {
            ptpInitiator.receiveData(dst);
            ptpInitiator.checkResponse();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Turns off the Wireless LAN.
     *
     * @throws IOException
     */
    public void turnOffWLAN() throws IOException, ThetaException {
        ptpInitiator.sendOperation(OperationCode.WLAN_POWER_CONTROL);

        try {
            ptpInitiator.checkResponse();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    // Property

    /**
     * Acquires the battery charge level.
     *
     * @throws IOException
     */
    public BatteryLevel getBatteryLevel() throws IOException, ThetaException {
        try {
            UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.BATTERY_LEVEL);
            return BatteryLevel.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the white balance.
     *
     * @throws IOException
     */
    public WhiteBalance getWhiteBalance() throws IOException, ThetaException {
        try {
            UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.WHITE_BALANCE);
            return WhiteBalance.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the white balance.
     *
     * Returns to the default value when the power is turned off.
     *
     * @throws IOException
     */
    public void setWhiteBalance(WhiteBalance whiteBalance) throws IOException, ThetaException {
        Validators.validateNonNull("whiteBalance", whiteBalance);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.WHITE_BALANCE, whiteBalance.value());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the ISO sensitivity.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public ISOSpeed getExposureIndex() throws IOException, ThetaException {
        try {
            UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_INDEX);
            return ISOSpeed.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the ISO sensitivity.
     *
     * Returns to the default value when the power is turned off.
     *
     * ISO sensitivity can be changed when ShutterSpeed is AUTO.
     *
     * @param isoSpeed An ISO speed
     * @throws IOException
     * @throws ThetaException
     */
    public void setExposureIndex(ISOSpeed isoSpeed) throws IOException, ThetaException {
        Validators.validateNonNull("isoSpeed", isoSpeed);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.EXPOSURE_INDEX, isoSpeed.value());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires or set the exposure bias compensation value.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getExposureBiasCompensation() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_BIAS_COMPENSATION).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the exposure bias compensation value.
     *
     * Returns to the default value when the power is turned off.
     *
     * @param exposureBiasCompensation An exposure bias compensation value to set.
     * @throws IOException
     * @throws ThetaException
     */
    public void setExposureBiasCompensation(int exposureBiasCompensation) throws IOException, ThetaException {
        UINT16 value = new UINT16(exposureBiasCompensation);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.EXPOSURE_BIAS_COMPENSATION, value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the date and time.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public Date getDateTime() throws IOException, ThetaException {
        String str;
        try {
            str = ptpInitiator.getDevicePropValueAsString(DevicePropCode.DATE_TIME);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }

        try {
            return new SimpleDateFormat(DATE_TIME_FORMAT).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the date and time.
     *
     * @param dateTime A date and time to set.
     * @throws IOException
     * @throws ThetaException
     */
    public void setDateTime(Date dateTime) throws IOException, ThetaException {
        Validators.validateNonNull("dateTime", dateTime);

        String str = new SimpleDateFormat(DATE_TIME_FORMAT).format(dateTime);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.DATE_TIME, str);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the still image shooting method.¥
     *
     * @throws IOException
     * @throws ThetaException
     */
    public StillCaptureMode getStillCaptureMode() throws IOException, ThetaException {
        try {
            UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.STILL_CAPTURE_MODE);
            return StillCaptureMode.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the still image shooting method.
     *
     * Returns to the default value when the power is turned off or when #initiateOpenCapture() ends.
     *
     * @param stillCaptureMode A still capture mode to set.
     * @throws IOException
     * @throws ThetaException
     */
    public void setStillCaptureMode(StillCaptureMode stillCaptureMode) throws IOException, ThetaException {
        Validators.validateNonNull("stillCaptureMode", stillCaptureMode);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.STILL_CAPTURE_MODE, stillCaptureMode.value());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the upper limit value for interval shooting.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getTimelapseNumber() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.TIMELAPSE_NUMBER).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the upper limit value for interval shooting.
     *
     * Returns to the default value when the power is turned off.
     *
     * This property cannot be set when the StillCaptureMode is interval shooting mode.
     * So, this property has to be set before switching the StillCaptureMode to interval shooting mode.
     *
     * @param timelapseNumber The upper limit value for interval shooting. The valid range is in 0 or 2-65535. The 0 means unlimited.
     * @throws IOException
     * @throws ThetaException
     */
    public void setTimelapseNumber(int timelapseNumber) throws IOException, ThetaException {
        if (timelapseNumber < 0 || timelapseNumber == 1 || 65535 < timelapseNumber) {
            throw new IllegalArgumentException(
                    String.format("Timelapse number is not work with %d. Set 0 or 2-65535.", timelapseNumber));
        }

        UINT16 value = new UINT16(timelapseNumber);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.TIMELAPSE_NUMBER, value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the shooting interval in msec for interval shooting.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public long getTimelapseInterval() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.TIMELAPSE_INTERVAL).longValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the shooting interval in msec for interval shooting.
     *
     * Returns to the default value when the power is turned off.
     *
     * This property cannot be set when the StillCaptureMode is interval shooting mode.
     * So, this property has to be set before switching the StillCaptureMode to interval shooting mode.
     *
     * @param timelapseInterval The shooting interval in msec for interval shooting. The valid range is in 5000-3600000.
     * @throws IOException
     * @throws ThetaException
     */
    public void setTimelapseInterval(long timelapseInterval) throws IOException, ThetaException {
        if (timelapseInterval < 5000 || 3600000 < timelapseInterval) {
            throw new IllegalArgumentException(
                    String.format("Timelapse interval is not work with %d. Set 5000-3600000.", timelapseInterval));
        }

        UINT32 value = new UINT32(timelapseInterval);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.TIMELAPSE_INTERVAL, value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires or set the volume for the shutter sound.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public long getAudioVolume() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.AUDIO_VOLUME).longValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Set the volume for the shutter sound.
     *
     * Returns to the default value when the power is turned off. // TODO: Confirm the actual behavior.
     *
     * @param audioVolume The volume for the shutter sound. The valid range is in 0-100.
     * @throws IOException
     * @throws ThetaException
     */
    public void setAudioVolume(long audioVolume) throws IOException, ThetaException {
        if (audioVolume < 0 || 100 < audioVolume) {
            throw new IllegalArgumentException(
                    String.format("Audio volume is not work with %d. Set 0-100.", audioVolume));
        }

        UINT32 value = new UINT32(audioVolume);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.AUDIO_VOLUME, value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the error information.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public ErrorInfo getErrorInfo() throws IOException, ThetaException {
        try {
            UINT32 value = ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.ERROR_INFO);
            return ErrorInfo.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the shutter speed.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public ShutterSpeed getShutterSpeed() throws IOException, ThetaException {
        try {
            UINT64 value = ptpInitiator.getDevicePropValueAsUINT64(DevicePropCode.SHUTTER_SPEED);
            return ShutterSpeed.valueOf(Rational.valueOf(value.bytes()));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the shutter speed.
     *
     * Returns to the default value when the power is turned off.
     *
     * @param shutterSpeed The shutter speed to set.
     * @throws IOException
     * @throws ThetaException
     */
    public void setShutterSpeed(ShutterSpeed shutterSpeed) throws IOException, ThetaException {
        Validators.validateNonNull("shutterSpeed", shutterSpeed);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.SHUTTER_SPEED, shutterSpeed.value().bytes());
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    // TODO: Add the GPSInfo class and replace String with GPSInfo.

    /**
     * Acquires the GPS information.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public String getGPSInfo() throws IOException, ThetaException {
        try {
            return STR.read(ptpInitiator.getDevicePropValue(DevicePropCode.GPS_INFO));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    // TODO: Add the GPSInfo class and replace String with GPSInfo.

    /**
     * Sets the GPS information.
     *
     * Returns to the default value when the power is turned off.
     *
     * @param gpsInfo The GPS information to set.
     * @throws IOException
     * @throws ThetaException
     */
    public void setGPSInfo(String gpsInfo) throws IOException, ThetaException {
        Validators.validateNonNull("gpsInfo", gpsInfo);

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.GPS_INFO, gpsInfo);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the time in minutes to start the auto power off.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getAutoPowerOffDelay() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.AUTO_POWER_OFF_DELAY).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the time in minutes to start the auto power off.
     *
     * @param autoPowerOffDelay The time in minutes to start the auto power off. The valid range is in 0-30. The 0 disables the auto power off.
     * @throws IOException
     * @throws ThetaException
     */
    public void setAutoPowerOffDelay(int autoPowerOffDelay) throws IOException, ThetaException {
        if (autoPowerOffDelay < 0 || 30 < autoPowerOffDelay) {
            throw new IllegalArgumentException(
                    String.format("Auto power off delay is not work with %d. Set 0-30.", autoPowerOffDelay));
        }

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.AUTO_POWER_OFF_DELAY, (byte) autoPowerOffDelay);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the time in seconds to start sleep.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getSleepDelay() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.SLEEP_DELAY).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the time in seconds to start sleep.
     *
     * @param sleepDelay The time in seconds to start sleep. The valid range is in 0-1800. Does not switch to sleep mode with 0.
     * @throws IOException
     * @throws ThetaException
     */
    public void setSleepDelay(int sleepDelay) throws IOException, ThetaException {
        if (sleepDelay < 0 || 1800 < sleepDelay) {
            throw new IllegalArgumentException(
                    String.format("Sleep delay is not work with %d. Set 0-1800.", sleepDelay));
        }

        try {
            ptpInitiator.setDevicePropValue(DevicePropCode.SLEEP_DELAY, new UINT16(sleepDelay));
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the wireless LAN channel number.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public ChannelNumber getChannelNumber() throws IOException, ThetaException {
        try {
            UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.CHANNEL_NUMBER);
            return ChannelNumber.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Sets the wireless LAN channel number.
     *
     * This operation effects after wireless LAN OFF/ON.
     *
     * @param channelNumber The wireless LAN channel number to set.
     * @throws IOException
     */
    public void setChannelNumber(ChannelNumber channelNumber) throws IOException {
        Validators.validateNonNull("channelNumber", channelNumber);
    }

    /**
     * Acquires the camera shooting execution status.
     *
     * @throws IOException
     * @throws ThetaException
     */
    public CaptureStatus getCaptureStatus() throws IOException, ThetaException {
        try {
            UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.CAPTURE_STATUS);
            return CaptureStatus.valueOf(value);
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the video recording time in seconds. (Model: RICOH THETA m15)
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getRecordingTime() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.RECORDING_TIME).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    /**
     * Acquires the amount of time remaining in seconds for recording video. (Model: RICOH THETA m15)
     *
     * @throws IOException
     * @throws ThetaException
     */
    public int getRemainingRecordingTime() throws IOException, ThetaException {
        try {
            return ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.REMAINING_RECORDING_TIME).intValue();
        } catch (PtpException e) {
            throw new ThetaException(e);
        }
    }

    // Listener

    /**
     * Add an event listener.
     *
     * @param listener An event listener to add.
     * @return true if this instance did not already contain the specified listener.
     */
    public boolean addListener(ThetaEventListener listener) {
        return listenerSet.add(listener);
    }

    /**
     * Remove an event listener.
     *
     * @param listener An event listener to add.
     * @return true if the instance contained the specified listener.
     */
    public boolean removeListener(ThetaEventListener listener) {
        return listenerSet.remove(listener);
    }

    // Closeable

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        listenerSet.clear();

        try {
            ptpInitiator.closeSession();
        } catch (PtpException e) {
            // Ignore
            LOGGER.error(e.getMessage(), e);
        } finally {
            ptpInitiator.close();
        }
    }
}
