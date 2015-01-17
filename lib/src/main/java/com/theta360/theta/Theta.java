package com.theta360.theta;

import com.theta360.ptp.Ptp;
import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpException;
import com.theta360.ptp.data.DeviceInfo;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.data.ObjectInfo;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptpip.PtpIpInitiator;
import com.theta360.theta.code.DevicePropCode;
import com.theta360.theta.code.OperationCode;
import com.theta360.theta.property.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class Theta implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Theta.class);

    private static final UINT32 SESSION_ID = new UINT32(1);
    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int TCP_PORT = 15740;

    private final Ptp ptp;
    private final ThetaEventListenerSet listenerSet = new ThetaEventListenerSet();

    public Theta() throws IOException {
        GUID guid = new GUID(UUID.randomUUID());
        ptp = new PtpIpInitiator(guid, IP_ADDRESS, TCP_PORT);

        ptp.addListener(new PtpEventListener() {
            @Override
            public void onCancelTransaction() {
                LOGGER.warn("Unsupported Event: CancelTransaction");
            }

            @Override
            public void onObjectAdded(UINT32 objectHandle) {
                listenerSet.onObjectAdded(objectHandle);
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
            public void onDevicePropChanged(UINT32 devicePropCode) {
                listenerSet.onDevicePropChanged(devicePropCode);
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
                listenerSet.onStoreFull(storageID);
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
                listenerSet.onCaptureComplete(transactionID);
            }

            @Override
            public void onUnreportedStatus() {
                LOGGER.warn("Unsupported Event: UnreportedStatus");
            }

            @Override
            public void onVendorExtendedCode(UINT16 eventCode, UINT32 p1, UINT32 p2, UINT32 p3) {
                LOGGER.warn("Unsupported Event: VendorExtendedCode");
            }

            @Override
            public void onError(Exception e) {
                LOGGER.warn("Unsupported Event: Error");
            }
        });

        try {
            ptp.openSession(SESSION_ID);
        } catch (PtpException e) {
            throw new RuntimeException(e);
        }
    }

    // Operation

    /**
     * Get the device information.
     *
     * @throws IOException
     * @throws PtpException
     */
    public DeviceInfo getDeviceInfo() throws IOException, PtpException {
        return ptp.getDeviceInfo();
    }

    public List<UINT32> getObjectHandles() throws IOException, PtpException {
        return ptp.getObjectHandles();
    }

    public ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException, PtpException {
        return ptp.getObjectInfo(objectHandle);
    }

    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        ptp.getObject(objectHandle, dst);
    }

    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        ptp.getThumb(objectHandle, dst);
    }

    public void deleteObject(UINT32 objectHandle) throws IOException, PtpException {
        ptp.deleteObject(objectHandle);
    }

    public void initiateCapture() throws IOException, PtpException {
        ptp.initiateCapture();
    }

    /**
     * Get resized image.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    public void getResizedImageObject(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        ptp.sendOperation(OperationCode.GET_RESIZED_IMAGE_OBJECT, objectHandle, new UINT32(2048), new UINT32(1024));
        ptp.receiveData(dst);
        ptp.checkResponse();
    }

    /**
     * Turn of the wireless LAN
     *
     * @throws IOException
     */
    public void turnOffWLAN() throws IOException, PtpException {
        ptp.sendOperation(OperationCode.WLAN_POWER_CONTROL);
        ptp.checkResponse();
    }

    // Property

    /**
     * Get the battery level
     *
     * @throws IOException
     */
    public int getBatteryLevel() throws IOException, PtpException {
        return ptp.getDevicePropValueAsUINT8(DevicePropCode.BATTERY_LEVEL);
    }

    /**
     * Get white balance
     *
     * @throws IOException
     */
    public WhiteBalance getWhiteBalance() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.WHITE_BALANCE);
        return WhiteBalance.valueOf(value);
    }

    /**
     * Set white balance
     *
     * @throws IOException
     */
    public void setWhiteBalance(WhiteBalance whiteBalance) throws IOException, PtpException {
        Validators.validateNonNull("whiteBalance", whiteBalance);

        ptp.setDevicePropValue(DevicePropCode.WHITE_BALANCE, whiteBalance.getValue());
    }

    public int getExposureIndex() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_INDEX);
        return value.intValue();
    }

    public void setExposureIndex(int exposureIndex) throws IOException, PtpException {
        UINT16 value = new UINT16(exposureIndex);
        ptp.setDevicePropValue(DevicePropCode.EXPOSURE_INDEX, value);
    }

    public int getExposureBiasCompensation() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_BIAS_COMPENSATION);
        return value.intValue();
    }

    public void setExposureBiasCompensation(int exposureBiasCompensation) throws IOException, PtpException {
        UINT16 value = new UINT16(exposureBiasCompensation);
        ptp.setDevicePropValue(DevicePropCode.EXPOSURE_BIAS_COMPENSATION, value);
    }

    public Date getDateTime() throws IOException, PtpException {
        String str = ptp.getDevicePropValueAsString(DevicePropCode.DATE_TIME);
        try {
            return new SimpleDateFormat("yyyyMMdd'T'hhmmss").parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDateTime(Date dateTime) throws IOException, PtpException {
        Validators.validateNonNull("dateTime", dateTime);

        String str = new SimpleDateFormat("yyyyMMdd'T'hhmmss").format(dateTime);
        ptp.setDevicePropValue(DevicePropCode.DATE_TIME, str);
    }

    public StillCaptureMode getStillCaptureMode() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.STILL_CAPTURE_MODE);
        return StillCaptureMode.valueOf(value);
    }

    public void setStillCaptureMode(StillCaptureMode stillCaptureMode) throws IOException, PtpException {
        Validators.validateNonNull("stillCaptureMode", stillCaptureMode);

        ptp.setDevicePropValue(DevicePropCode.STILL_CAPTURE_MODE, stillCaptureMode.getValue());
    }

    public int getTimelapseNumber() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.TIMELAPSE_NUMBER);
        return value.intValue();
    }

    public void setTimelapseNumber(int timelapseNumber) throws IOException, PtpException {
        if (timelapseNumber == 1) {
            throw new IllegalArgumentException("Timelapse is not work with 1. Set 0 or 2 and over.");
        }

        UINT16 value = new UINT16(timelapseNumber);
        ptp.setDevicePropValue(DevicePropCode.TIMELAPSE_NUMBER, value);
    }

    public long getTimelapseInterval() throws IOException, PtpException {
        UINT32 value = ptp.getDevicePropValueAsUINT32(DevicePropCode.TIMELAPSE_INTERVAL);
        return value.longValue();
    }

    public void setTimelapseInterval(long timelapseInterval) throws IOException, PtpException {
        UINT32 value = new UINT32(timelapseInterval);
        ptp.setDevicePropValue(DevicePropCode.TIMELAPSE_INTERVAL, value);
    }

    public long getAudioVolume() throws IOException, PtpException {
        UINT32 value = ptp.getDevicePropValueAsUINT32(DevicePropCode.AUDIO_VOLUME);
        return value.longValue();
    }

    public void setAudioVolume(long audioVolume) throws IOException, PtpException {
        if (audioVolume < 0 || 100 < audioVolume) {
            throw new IllegalArgumentException();
        }

        UINT32 value = new UINT32(audioVolume);
        ptp.setDevicePropValue(DevicePropCode.AUDIO_VOLUME, value);
    }

    public ErrorInfo getErrorInfo() throws IOException, PtpException {
        UINT32 value = ptp.getDevicePropValueAsUINT32(DevicePropCode.ERROR_INFO);
        return ErrorInfo.valueOf(value);
    }

    public Rational getShutterSpeed() throws IOException, PtpException {
        byte[] value = ptp.getDevicePropValue(DevicePropCode.SHUTTER_SPEED);
        return Rational.valueOf(value);
    }

    public void setShutterSpeed(Rational shutterSpeed) throws IOException, PtpException {
        Validators.validateNonNull("shutterSpeed", shutterSpeed);

        ptp.setDevicePropValue(DevicePropCode.SHUTTER_SPEED, shutterSpeed.bytes());
    }

    public GPSInfo getGPSInfo() throws IOException {
        // TODO Implement
        throw new UnsupportedOperationException();
    }

    public void setGPSInfo(GPSInfo gpsInfo) throws IOException {
        Validators.validateNonNull("gpsInfo", gpsInfo);

        // TODO Implement
        throw new UnsupportedOperationException();
    }

    public short getAutoPowerOffDelay() throws IOException, PtpException {
        return ptp.getDevicePropValueAsUINT8(DevicePropCode.AUTO_POWER_OFF_DELAY);
    }

    public void setAutoPowerOffDelay(short autoPowerOffDelay) throws IOException, PtpException {
        if (autoPowerOffDelay < 0) {
            throw new IllegalArgumentException();
        }

        ptp.setDevicePropValue(DevicePropCode.AUTO_POWER_OFF_DELAY, (byte) autoPowerOffDelay);
    }

    public short getSleepDelay() throws IOException, PtpException {
        return ptp.getDevicePropValueAsUINT8(DevicePropCode.SLEEP_DELAY);
    }

    public void setSleepDelay(short sleepDelay) throws IOException, PtpException {
        if (sleepDelay < 0) {
            throw new IllegalArgumentException();
        }

        ptp.setDevicePropValue(DevicePropCode.SLEEP_DELAY, (byte) sleepDelay);
    }

    public ChannelNumber getChannelNumber() throws IOException, PtpException {
        byte value = ptp.getDevicePropValueAsUINT8(DevicePropCode.CHANNEL_NUMBER);
        return ChannelNumber.valueOf(value);
    }

    public void setChannelNumber(ChannelNumber channelNumber) throws IOException {
        Validators.validateNonNull("channelNumber", channelNumber);
    }

    public CaptureStatus getCaptureStatus() throws IOException, PtpException {
        byte value = ptp.getDevicePropValueAsUINT8(DevicePropCode.CAPTURE_STATUS);
        return CaptureStatus.valueOf(value);
    }

    public int getRecordingTime() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.RECORDING_TIME);
        return value.intValue();
    }

    public int getRemainingRecordingTime() throws IOException, PtpException {
        UINT16 value = ptp.getDevicePropValueAsUINT16(DevicePropCode.REMAINING_RECORDING_TIME);
        return value.intValue();
    }

    // Listener

    public boolean addListener(ThetaEventListener listener) {
        return listenerSet.add(listener);
    }

    public boolean removeListener(ThetaEventListener listener) {
        return listenerSet.remove(listener);
    }

    // Closeable

    @Override
    public void close() throws IOException {
        listenerSet.clear();

        try {
            ptp.closeSession();
        } catch (PtpException e) {
            // Ignore
        } finally {
            ptp.close();
        }
    }
}
