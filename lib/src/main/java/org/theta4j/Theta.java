package org.theta4j;

import org.theta4j.code.OperationCode;
import org.theta4j.data.*;
import org.theta4j.ptp.PtpEventListener;
import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.PtpInitiator;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.data.ObjectInfo;
import org.theta4j.ptp.data.StorageInfo;
import org.theta4j.ptpip.PtpIpInitiator;
import org.theta4j.code.DevicePropCode;
import org.theta4j.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.type.*;

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
     * @throws PtpException
     */
    public DeviceInfo getDeviceInfo() throws IOException, PtpException {
        return ptpInitiator.getDeviceInfo();
    }

    /**
     * Returns a list of the currently valid StorageIDs.
     *
     * @throws IOException
     * @throws PtpException
     */
    public List<Long> getStorageIDs() throws IOException, PtpException {
        return PrimitiveUtils.convert(ptpInitiator.getStorageIDs());
    }

    /**
     * Returns a StorageInfo of the storage area indicated in the storageID.
     *
     * @param storageID The StorageID of the storage area to acquire the StorageInfo.
     * @throws IOException
     * @throws PtpException
     */
    public StorageInfo getStorageInfo(long storageID) throws IOException, PtpException {
        return ptpInitiator.getStorageInfo(new UINT32(storageID));
    }

    /**
     * Returns the total number of objects present in the all storage.
     *
     * @throws IOException
     * @throws PtpException
     */
    public long getNumObjects() throws IOException, PtpException {
        return ptpInitiator.getNumObjects().longValue();
    }

    /**
     * Returns a list of the object handles.
     *
     * @throws IOException
     * @throws PtpException
     */
    public List<Long> getObjectHandles() throws IOException, PtpException {
        return PrimitiveUtils.convert(ptpInitiator.getObjectHandles());
    }

    /**
     * Returns a ObjectInfo for the object specified by the objectHandle.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the ObjectInfo.
     * @throws IOException
     * @throws PtpException
     */
    public ObjectInfo getObjectInfo(long objectHandle) throws IOException, PtpException {
        return ptpInitiator.getObjectInfo(new UINT32(objectHandle));
    }

    /**
     * Retrieves the object's data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the data.
     * @param dst          The destination for the object's data.
     * @throws IOException
     * @throws PtpException
     */
    public void getObject(long objectHandle, OutputStream dst) throws IOException, PtpException {
        ptpInitiator.getObject(new UINT32(objectHandle), dst);
    }

    /**
     * Retrieves the object's thumbnail data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the thumbnail data.
     * @param dst          The destination for the object's thumbnail data.
     * @throws IOException
     * @throws PtpException
     */
    public void getThumb(long objectHandle, OutputStream dst) throws IOException, PtpException {
        ptpInitiator.getThumb(new UINT32(objectHandle), dst);
    }

    /**
     * Deletes the object specified by the ObjectHandle.
     *
     * @param objectHandle The ObjectHandle of the object to delete.
     * @throws IOException
     * @throws PtpException
     */
    public void deleteObject(long objectHandle) throws IOException, PtpException {
        ptpInitiator.deleteObject(new UINT32(objectHandle));
    }

    /**
     * Starts shooting.
     *
     * @throws IOException
     * @throws PtpException
     */
    public void initiateCapture() throws IOException, PtpException {
        ptpInitiator.initiateCapture();
    }

    /**
     * Exits the all continuous shooting.
     *
     * @throws IOException
     * @throws PtpException
     */
    public void terminateOpenCapture() throws IOException, PtpException {
        ptpInitiator.terminateOpenCapture();
    }

    /**
     * Exits a continuous shooting specified by TransactionID.
     *
     * @param transactionID The TransactionID returned by Theta#initiateOpenCapture().
     * @throws IOException
     * @throws PtpException
     * @see #initiateOpenCapture()
     */
    public void terminateOpenCapture(long transactionID) throws IOException, PtpException {
        ptpInitiator.terminateOpenCapture(new UINT32(transactionID));
    }

    /**
     * Starts the video recording or the interval shooting.
     * <p/>
     * After starts, it can exit by the #terminateOpenCapture(long)
     *
     * @throws IOException
     * @throws PtpException
     * @see #terminateOpenCapture(long)
     */
    public long initiateOpenCapture() throws IOException, PtpException {
        return ptpInitiator.initiateOpenCapture().longValue();
    }

    /**
     * Retrieves the object's resized data and writes to the dst.
     *
     * @param objectHandle The ObjectHandle of the object to acquire the resized data.
     * @param dst          The destination for the object's resized data.
     * @throws IOException
     */
    public void getResizedImageObject(long objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        ptpInitiator.sendOperation(OperationCode.GET_RESIZED_IMAGE_OBJECT, new UINT32(objectHandle), new UINT32(2048), new UINT32(1024));
        ptpInitiator.receiveData(dst);
        ptpInitiator.checkResponse();
    }

    /**
     * Turns off the Wireless LAN.
     *
     * @throws IOException
     */
    public void turnOffWLAN() throws IOException, PtpException {
        ptpInitiator.sendOperation(OperationCode.WLAN_POWER_CONTROL);
        ptpInitiator.checkResponse();
    }

    // Property

    /**
     * Acquires the battery charge level.
     *
     * @throws IOException
     */
    public BatteryLevel getBatteryLevel() throws IOException, PtpException {
        UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.BATTERY_LEVEL);
        return BatteryLevel.valueOf(value);
    }

    /**
     * Acquires the white balance.
     *
     * @throws IOException
     */
    public WhiteBalance getWhiteBalance() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.WHITE_BALANCE);
        return WhiteBalance.valueOf(value);
    }

    /**
     * Sets the white balance.
     * <p/>
     * Returns to the default value when the power is turned off.
     *
     * @throws IOException
     */
    public void setWhiteBalance(WhiteBalance whiteBalance) throws IOException, PtpException {
        Validators.validateNonNull("whiteBalance", whiteBalance);

        ptpInitiator.setDevicePropValue(DevicePropCode.WHITE_BALANCE, whiteBalance.value());
    }

    /**
     * Acquires the ISO sensitivity.
     *
     * @throws IOException
     * @throws PtpException
     */
    public ISOSpeed getExposureIndex() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_INDEX);
        return ISOSpeed.valueOf(value);
    }

    /**
     * Sets the ISO sensitivity.
     * <p/>
     * Returns to the default value when the power is turned off.
     * <p/>
     * ISO sensitivity can be changed when ShutterSpeed is AUTO.
     *
     * @param isoSpeed An ISO speed
     * @throws IOException
     * @throws PtpException
     */
    public void setExposureIndex(ISOSpeed isoSpeed) throws IOException, PtpException {
        Validators.validateNonNull("isoSpeed", isoSpeed);

        ptpInitiator.setDevicePropValue(DevicePropCode.EXPOSURE_INDEX, isoSpeed.value());
    }

    /**
     * Acquires or set the exposure bias compensation value.
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getExposureBiasCompensation() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_BIAS_COMPENSATION);
        return value.intValue();
    }

    /**
     * Sets the exposure bias compensation value.
     * <p/>
     * Returns to the default value when the power is turned off.
     *
     * @param exposureBiasCompensation An exposure bias compensation value to set.
     * @throws IOException
     * @throws PtpException
     */
    public void setExposureBiasCompensation(int exposureBiasCompensation) throws IOException, PtpException {
        UINT16 value = new UINT16(exposureBiasCompensation);
        ptpInitiator.setDevicePropValue(DevicePropCode.EXPOSURE_BIAS_COMPENSATION, value);
    }

    /**
     * Acquires the date and time.
     *
     * @throws IOException
     * @throws PtpException
     */
    public Date getDateTime() throws IOException, PtpException {
        String str = ptpInitiator.getDevicePropValueAsString(DevicePropCode.DATE_TIME);
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
     * @throws PtpException
     */
    public void setDateTime(Date dateTime) throws IOException, PtpException {
        Validators.validateNonNull("dateTime", dateTime);

        String str = new SimpleDateFormat(DATE_TIME_FORMAT).format(dateTime);
        ptpInitiator.setDevicePropValue(DevicePropCode.DATE_TIME, str);
    }

    /**
     * Acquires the still image shooting method.¥
     *
     * @throws IOException
     * @throws PtpException
     */
    public StillCaptureMode getStillCaptureMode() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.STILL_CAPTURE_MODE);
        return StillCaptureMode.valueOf(value);
    }

    /**
     * Sets the still image shooting method.
     * <p/>
     * Returns to the default value when the power is turned off or when #initiateOpenCapture() ends.
     *
     * @param stillCaptureMode A still capture mode to set.
     * @throws IOException
     * @throws PtpException
     */
    public void setStillCaptureMode(StillCaptureMode stillCaptureMode) throws IOException, PtpException {
        Validators.validateNonNull("stillCaptureMode", stillCaptureMode);

        ptpInitiator.setDevicePropValue(DevicePropCode.STILL_CAPTURE_MODE, stillCaptureMode.value());
    }

    /**
     * Acquires the upper limit value for interval shooting.
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getTimelapseNumber() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.TIMELAPSE_NUMBER);
        return value.intValue();
    }

    /**
     * Sets the upper limit value for interval shooting.
     * <p/>
     * Returns to the default value when the power is turned off.
     * <p/>
     * This property cannot be set when the StillCaptureMode is interval shooting mode.
     * So, this property has to be set before switching the StillCaptureMode to interval shooting mode.
     *
     * @param timelapseNumber The upper limit value for interval shooting. The valid range is in 0 or 2-65535. The 0 means unlimited.
     * @throws IOException
     * @throws PtpException
     */
    public void setTimelapseNumber(int timelapseNumber) throws IOException, PtpException {
        if (timelapseNumber < 0 || timelapseNumber == 1 || 65535 < timelapseNumber) {
            throw new IllegalArgumentException(
                    String.format("Timelapse number is not work with %d. Set 0 or 2-65535.", timelapseNumber));
        }

        UINT16 value = new UINT16(timelapseNumber);
        ptpInitiator.setDevicePropValue(DevicePropCode.TIMELAPSE_NUMBER, value);
    }

    /**
     * Acquires the shooting interval in msec for interval shooting.
     *
     * @throws IOException
     * @throws PtpException
     */
    public long getTimelapseInterval() throws IOException, PtpException {
        UINT32 value = ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.TIMELAPSE_INTERVAL);
        return value.longValue();
    }

    /**
     * Sets the shooting interval in msec for interval shooting.
     * <p/>
     * Returns to the default value when the power is turned off.
     * <p/>
     * This property cannot be set when the StillCaptureMode is interval shooting mode.
     * So, this property has to be set before switching the StillCaptureMode to interval shooting mode.
     *
     * @param timelapseInterval The shooting interval in msec for interval shooting. The valid range is in 5000-3600000.
     * @throws IOException
     * @throws PtpException
     */
    public void setTimelapseInterval(long timelapseInterval) throws IOException, PtpException {
        if (timelapseInterval < 5000 || 3600000 < timelapseInterval) {
            throw new IllegalArgumentException(
                    String.format("Timelapse interval is not work with %d. Set 5000-3600000.", timelapseInterval));
        }

        UINT32 value = new UINT32(timelapseInterval);
        ptpInitiator.setDevicePropValue(DevicePropCode.TIMELAPSE_INTERVAL, value);
    }

    /**
     * Acquires or set the volume for the shutter sound.
     *
     * @throws IOException
     * @throws PtpException
     */
    public long getAudioVolume() throws IOException, PtpException {
        UINT32 value = ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.AUDIO_VOLUME);
        return value.longValue();
    }

    /**
     * Set the volume for the shutter sound.
     * <p/>
     * Returns to the default value when the power is turned off. // TODO: Confirm the actual behavior.
     *
     * @param audioVolume The volume for the shutter sound. The valid range is in 0-100.
     * @throws IOException
     * @throws PtpException
     */
    public void setAudioVolume(long audioVolume) throws IOException, PtpException {
        if (audioVolume < 0 || 100 < audioVolume) {
            throw new IllegalArgumentException(
                    String.format("Audio volume is not work with %d. Set 0-100.", audioVolume));
        }

        UINT32 value = new UINT32(audioVolume);
        ptpInitiator.setDevicePropValue(DevicePropCode.AUDIO_VOLUME, value);
    }

    /**
     * Acquires the error information.
     *
     * @throws IOException
     * @throws PtpException
     */
    public ErrorInfo getErrorInfo() throws IOException, PtpException {
        UINT32 value = ptpInitiator.getDevicePropValueAsUINT32(DevicePropCode.ERROR_INFO);
        return ErrorInfo.valueOf(value);
    }

    /**
     * Acquires the shutter speed.
     *
     * @throws IOException
     * @throws PtpException
     */
    public ShutterSpeed getShutterSpeed() throws IOException, PtpException {
        UINT64 value = ptpInitiator.getDevicePropValueAsUINT64(DevicePropCode.SHUTTER_SPEED);
        return ShutterSpeed.valueOf(Rational.valueOf(value.bytes()));
    }

    /**
     * Sets the shutter speed.
     * <p/>
     * Returns to the default value when the power is turned off.
     *
     * @param shutterSpeed The shutter speed to set.
     * @throws IOException
     * @throws PtpException
     */
    public void setShutterSpeed(ShutterSpeed shutterSpeed) throws IOException, PtpException {
        Validators.validateNonNull("shutterSpeed", shutterSpeed);

        ptpInitiator.setDevicePropValue(DevicePropCode.SHUTTER_SPEED, shutterSpeed.value().bytes());
    }

    // TODO: Add the GPSInfo class and replace String with GPSInfo.

    /**
     * Acquires the GPS information.
     *
     * @throws IOException
     * @throws PtpException
     */
    public String getGPSInfo() throws IOException, PtpException {
        return STR.read(ptpInitiator.getDevicePropValue(DevicePropCode.GPS_INFO));
    }

    // TODO: Add the GPSInfo class and replace String with GPSInfo.

    /**
     * Sets the GPS information.
     * <p/>
     * Returns to the default value when the power is turned off.
     *
     * @param gpsInfo The GPS information to set.
     * @throws IOException
     * @throws PtpException
     */
    public void setGPSInfo(String gpsInfo) throws IOException, PtpException {
        Validators.validateNonNull("gpsInfo", gpsInfo);

        ptpInitiator.setDevicePropValue(DevicePropCode.GPS_INFO, gpsInfo);
    }

    /**
     * Acquires the time in minutes to start the auto power off.
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getAutoPowerOffDelay() throws IOException, PtpException {
        return ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.AUTO_POWER_OFF_DELAY).intValue();
    }

    /**
     * Sets the time in minutes to start the auto power off.
     *
     * @param autoPowerOffDelay The time in minutes to start the auto power off. The valid range is in 0-30. The 0 disables the auto power off.
     * @throws IOException
     * @throws PtpException
     */
    public void setAutoPowerOffDelay(int autoPowerOffDelay) throws IOException, PtpException {
        if (autoPowerOffDelay < 0 || 30 < autoPowerOffDelay) {
            throw new IllegalArgumentException(
                    String.format("Auto power off delay is not work with %d. Set 0-30.", autoPowerOffDelay));
        }

        ptpInitiator.setDevicePropValue(DevicePropCode.AUTO_POWER_OFF_DELAY, (byte) autoPowerOffDelay);
    }

    /**
     * Acquires the time in seconds to start sleep.
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getSleepDelay() throws IOException, PtpException {
        return ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.SLEEP_DELAY).intValue();
    }

    /**
     * Sets the time in seconds to start sleep.
     *
     * @param sleepDelay The time in seconds to start sleep. The valid range is in 0-1800. Does not switch to sleep mode with 0.
     * @throws IOException
     * @throws PtpException
     */
    public void setSleepDelay(int sleepDelay) throws IOException, PtpException {
        if (sleepDelay < 0 || 1800 < sleepDelay) {
            throw new IllegalArgumentException(
                    String.format("Sleep delay is not work with %d. Set 0-1800.", sleepDelay));
        }

        ptpInitiator.setDevicePropValue(DevicePropCode.SLEEP_DELAY, new UINT16(sleepDelay));
    }

    /**
     * Acquires the wireless LAN channel number.
     *
     * @throws IOException
     * @throws PtpException
     */
    public ChannelNumber getChannelNumber() throws IOException, PtpException {
        UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.CHANNEL_NUMBER);
        return ChannelNumber.valueOf(value);
    }

    /**
     * Sets the wireless LAN channel number.
     * <p/>
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
     * @throws PtpException
     */
    public CaptureStatus getCaptureStatus() throws IOException, PtpException {
        UINT8 value = ptpInitiator.getDevicePropValueAsUINT8(DevicePropCode.CAPTURE_STATUS);
        return CaptureStatus.valueOf(value);
    }

    /**
     * Acquires the video recording time in seconds. (Model: RICOH THETA m15)
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getRecordingTime() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.RECORDING_TIME);
        return value.intValue();
    }

    /**
     * Acquires the amount of time remaining in seconds for recording video. (Model: RICOH THETA m15)
     *
     * @throws IOException
     * @throws PtpException
     */
    public int getRemainingRecordingTime() throws IOException, PtpException {
        UINT16 value = ptpInitiator.getDevicePropValueAsUINT16(DevicePropCode.REMAINING_RECORDING_TIME);
        return value.intValue();
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
