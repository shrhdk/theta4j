package com.theta360.theta;

import com.theta360.ptp.PtpException;
import com.theta360.ptpip.PtpIpInitiator;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.code.DevicePropCode;
import com.theta360.theta.code.OperationCode;
import com.theta360.theta.property.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class Theta extends PtpIpInitiator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Theta.class);

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int TCP_PORT = 15740;

    public Theta() throws IOException {
        super(new GUID(UUID.randomUUID()), IP_ADDRESS, TCP_PORT);
    }

    // Operation

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

        sendOperationRequest(OperationCode.GET_RESIZED_IMAGE_OBJECT, objectHandle, new UINT32(2048), new UINT32(1024));
        ci.readData(dst);
        receiveOperationResponse();
    }

    /**
     * Turn of the wireless LAN
     *
     * @throws IOException
     */
    public void turnOffWLAN() throws IOException, PtpException {
        sendOperationRequest(OperationCode.WLAN_POWER_CONTROL);
        receiveOperationResponse();
    }

    // Property

    /**
     * Get the battery level
     *
     * @throws IOException
     */
    public int getBatteryLevel() throws IOException, PtpException {
        return getDevicePropValueAsUINT8(DevicePropCode.BATTERY_LEVEL);
    }

    /**
     * Get white balance
     *
     * @throws IOException
     */
    public WhiteBalance getWhiteBalance() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.WHITE_BALANCE);
        return WhiteBalance.valueOf(value);
    }

    /**
     * Set white balance
     *
     * @throws IOException
     */
    public void setWhiteBalance(WhiteBalance whiteBalance) throws IOException, PtpException {
        Validators.validateNonNull("whiteBalance", whiteBalance);

        setDevicePropValue(DevicePropCode.WHITE_BALANCE, whiteBalance.getValue());
    }

    public int getExposureIndex() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_INDEX);
        return value.intValue();
    }

    public void setExposureIndex(int exposureIndex) throws IOException, PtpException {
        UINT16 value = new UINT16(exposureIndex);
        setDevicePropValue(DevicePropCode.EXPOSURE_INDEX, value);
    }

    public int getExposureBiasCompensation() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.EXPOSURE_BIAS_COMPENSATION);
        return value.intValue();
    }

    public void setExposureBiasCompensation(int exposureBiasCompensation) throws IOException, PtpException {
        UINT16 value = new UINT16(exposureBiasCompensation);
        setDevicePropValue(DevicePropCode.EXPOSURE_BIAS_COMPENSATION, value);
    }

    public Date getDateTime() throws IOException, PtpException {
        String str = getDevicePropValueAsString(DevicePropCode.DATE_TIME);
        try {
            return new SimpleDateFormat("yyyyMMdd'T'hhmmss").parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDateTime(Date dateTime) throws IOException, PtpException {
        Validators.validateNonNull("dateTime", dateTime);

        String str = new SimpleDateFormat("yyyyMMdd'T'hhmmss").format(dateTime);
        setDevicePropValue(DevicePropCode.DATE_TIME, str);
    }

    public StillCaptureMode getStillCaptureMode() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.STILL_CAPTURE_MODE);
        return StillCaptureMode.valueOf(value);
    }

    public void setStillCaptureMode(StillCaptureMode stillCaptureMode) throws IOException, PtpException {
        Validators.validateNonNull("stillCaptureMode", stillCaptureMode);

        setDevicePropValue(DevicePropCode.STILL_CAPTURE_MODE, stillCaptureMode.getValue());
    }

    public int getTimelapseNumber() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.TIMELAPSE_NUMBER);
        return value.intValue();
    }

    public void setTimelapseNumber(int timelapseNumber) throws IOException, PtpException {
        if (timelapseNumber == 1) {
            throw new IllegalArgumentException("Timelapse is not work with 1. Set 0 or 2 and over.");
        }

        UINT16 value = new UINT16(timelapseNumber);
        setDevicePropValue(DevicePropCode.TIMELAPSE_NUMBER, value);
    }

    public long getTimelapseInterval() throws IOException, PtpException {
        UINT32 value = getDevicePropValueAsUINT32(DevicePropCode.TIMELAPSE_INTERVAL);
        return value.longValue();
    }

    public void setTimelapseInterval(long timelapseInterval) throws IOException, PtpException {
        UINT32 value = new UINT32(timelapseInterval);
        setDevicePropValue(DevicePropCode.TIMELAPSE_INTERVAL, value);
    }

    public long getAudioVolume() throws IOException, PtpException {
        UINT32 value = getDevicePropValueAsUINT32(DevicePropCode.AUDIO_VOLUME);
        return value.longValue();
    }

    public void setAudioVolume(long audioVolume) throws IOException, PtpException {
        if (audioVolume < 0 || 100 < audioVolume) {
            throw new IllegalArgumentException();
        }

        UINT32 value = new UINT32(audioVolume);
        setDevicePropValue(DevicePropCode.AUDIO_VOLUME, value);
    }

    public ErrorInfo getErrorInfo() throws IOException, PtpException {
        UINT32 value = getDevicePropValueAsUINT32(DevicePropCode.ERROR_INFO);
        return ErrorInfo.valueOf(value);
    }

    public Rational getShutterSpeed() throws IOException, PtpException {
        byte[] value = getDevicePropValue(DevicePropCode.SHUTTER_SPEED);
        return Rational.valueOf(value);
    }

    public void setShutterSpeed(Rational shutterSpeed) throws IOException, PtpException {
        Validators.validateNonNull("shutterSpeed", shutterSpeed);

        setDevicePropValue(DevicePropCode.SHUTTER_SPEED, shutterSpeed.bytes());
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
        return getDevicePropValueAsUINT8(DevicePropCode.AUTO_POWER_OFF_DELAY);
    }

    public void setAutoPowerOffDelay(short autoPowerOffDelay) throws IOException, PtpException {
        if (autoPowerOffDelay < 0) {
            throw new IllegalArgumentException();
        }

        setDevicePropValue(DevicePropCode.AUTO_POWER_OFF_DELAY, (byte) autoPowerOffDelay);
    }

    public short getSleepDelay() throws IOException, PtpException {
        return getDevicePropValueAsUINT8(DevicePropCode.SLEEP_DELAY);
    }

    public void setSleepDelay(short sleepDelay) throws IOException, PtpException {
        if (sleepDelay < 0) {
            throw new IllegalArgumentException();
        }

        setDevicePropValue(DevicePropCode.SLEEP_DELAY, (byte) sleepDelay);
    }

    public ChannelNumber getChannelNumber() throws IOException, PtpException {
        byte value = getDevicePropValueAsUINT8(DevicePropCode.CHANNEL_NUMBER);
        return ChannelNumber.valueOf(value);
    }

    public void setChannelNumber(ChannelNumber channelNumber) throws IOException {
        Validators.validateNonNull("channelNumber", channelNumber);
    }

    public CaptureStatus getCaptureStatus() throws IOException, PtpException {
        byte value = getDevicePropValueAsUINT8(DevicePropCode.CAPTURE_STATUS);
        return CaptureStatus.valueOf(value);
    }

    public int getRecordingTime() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.RECORDING_TIME);
        return value.intValue();
    }

    public int getRemainingRecordingTime() throws IOException, PtpException {
        UINT16 value = getDevicePropValueAsUINT16(DevicePropCode.REMAINING_RECORDING_TIME);
        return value.intValue();
    }
}
