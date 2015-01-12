package com.theta360.theta;

import com.theta360.ptp.PtpInitiator;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.packet.OperationRequestPacket;
import com.theta360.ptp.packet.OperationResponsePacket;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.code.OperationCode;
import com.theta360.theta.code.PropertyCode;
import com.theta360.theta.property.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

public final class Theta extends PtpInitiator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Theta.class);

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int TCP_PORT = 15740;

    public Theta() throws IOException {
        super(new GUID(UUID.randomUUID()), IP_ADDRESS, TCP_PORT);
    }

    // Operation

    public void getResizedImageObject(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetResizedImageObject)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_RESIZED_IMAGE_OBJECT.getCode(),
                transactionID.next(),
                objectHandle,
                new UINT32(2048),
                new UINT32(1024)
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetResizedImageObject): " + operationRequest);

        // Receive Data
        ci.readData(dst);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    // Property

    /**
     * Get the battery level
     *
     * @throws IOException
     */
    public int getBatteryLevel() throws IOException {
        return getDevicePropValueAsUINT8(PropertyCode.BATTERY_LEVEL.getCode());
    }

    /**
     * Get white balance
     *
     * @throws IOException
     */
    public WhiteBalance getWhiteBalance() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.WHITE_BALANCE.getCode());
        return WhiteBalance.valueOf(value);
    }

    /**
     * Set white balance
     *
     * @throws IOException
     */
    public void setWhiteBalance(WhiteBalance whiteBalance) throws IOException {
        Validators.validateNonNull("whiteBalance", whiteBalance);

        setDevicePropValue(PropertyCode.WHITE_BALANCE.getCode(), whiteBalance.getValue());
    }

    public int getExposureIndex() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.EXPOSURE_INDEX.getCode());
        return value.intValue();
    }

    public void setExposureIndex(int exposureIndex) throws IOException {
        UINT16 value = new UINT16(exposureIndex);
        setDevicePropValue(PropertyCode.EXPOSURE_INDEX.getCode(), value);
    }

    public int getExposureBiasCompensation() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.EXPOSURE_BIAS_COMPENSATION.getCode());
        return value.intValue();
    }

    public void setExposureBiasCompensation(int exposureBiasCompensation) throws IOException {
        UINT16 value = new UINT16(exposureBiasCompensation);
        setDevicePropValue(PropertyCode.EXPOSURE_BIAS_COMPENSATION.getCode(), value);
    }

    public Date getDateTime() throws IOException {
        // TODO Implement
        throw new UnsupportedOperationException();
    }

    public void setDateTime(Date dateTime) throws IOException {
        Validators.validateNonNull("dateTime", dateTime);

        // TODO Implement
        throw new UnsupportedOperationException();
    }

    public StillCaptureMode getStillCaptureMode() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.STILL_CAPTURE_MODE.getCode());
        return StillCaptureMode.valueOf(value);
    }

    public void setStillCaptureMode(StillCaptureMode stillCaptureMode) throws IOException {
        Validators.validateNonNull("stillCaptureMode", stillCaptureMode);

        setDevicePropValue(PropertyCode.STILL_CAPTURE_MODE.getCode(), stillCaptureMode.getValue());
    }

    public int getTimelapseNumber() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.TIMELAPSE_NUMBER.getCode());
        return value.intValue();
    }

    public void setTimelapseNumber(int timelapseNumber) throws IOException {
        if (timelapseNumber == 1) {
            throw new IllegalArgumentException("Timelapse is not work with 1. Set 0 or 2 and over.");
        }

        UINT16 value = new UINT16(timelapseNumber);
        setDevicePropValue(PropertyCode.TIMELAPSE_NUMBER.getCode(), value);
    }

    public long getTimelapseInterval() throws IOException {
        UINT32 value = getDevicePropValueAsUINT32(PropertyCode.TIMELAPSE_INTERVAL.getCode());
        return value.longValue();
    }

    public void setTimelapseInterval(long timelapseInterval) throws IOException {
        UINT32 value = new UINT32(timelapseInterval);
        setDevicePropValue(PropertyCode.TIMELAPSE_INTERVAL.getCode(), value);
    }

    public int getAudioVolume() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.AUDIO_VOLUME.getCode());
        return value.intValue();
    }

    public void setAudioVolume(int audioVolume) throws IOException {
        if (audioVolume < 0 || 100 < audioVolume) {
            throw new IllegalArgumentException();
        }

        UINT16 value = new UINT16(audioVolume);
        setDevicePropValue(PropertyCode.AUDIO_VOLUME.getCode(), value);
    }

    public ErrorInfo getErrorInfo() throws IOException {
        UINT32 value = getDevicePropValueAsUINT32(PropertyCode.ERROR_INFO.getCode());
        return ErrorInfo.valueOf(value);
    }

    public Rational getShutterSpeed() throws IOException {
        byte[] value = getDevicePropValue(PropertyCode.SHUTTER_SPEED.getCode());
        return Rational.valueOf(value);
    }

    public void setShutterSpeed(Rational shutterSpeed) throws IOException {
        Validators.validateNonNull("shutterSpeed", shutterSpeed);

        setDevicePropValue(PropertyCode.SHUTTER_SPEED.getCode(), shutterSpeed.bytes());
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

    public short getAutoPowerOffDelay() throws IOException {
        return getDevicePropValueAsUINT8(PropertyCode.AUTO_POWER_OFF_DELAY.getCode());
    }

    public void setAutoPowerOffDelay(short autoPowerOffDelay) throws IOException {
        if (autoPowerOffDelay < 0) {
            throw new IllegalArgumentException();
        }

        setDevicePropValue(PropertyCode.AUTO_POWER_OFF_DELAY.getCode(), (byte) autoPowerOffDelay);
    }

    public short getSleepDelay() throws IOException {
        return getDevicePropValueAsUINT8(PropertyCode.SLEEP_DELAY.getCode());
    }

    public void setSleepDelay(short sleepDelay) throws IOException {
        if (sleepDelay < 0) {
            throw new IllegalArgumentException();
        }

        setDevicePropValue(PropertyCode.SLEEP_DELAY.getCode(), (byte) sleepDelay);
    }

    public ChannelNumber getChannelNumber() throws IOException {
        byte value = getDevicePropValueAsUINT8(PropertyCode.CHANNEL_NUMBER.getCode());
        return ChannelNumber.valueOf(value);
    }

    public void setChannelNumber(ChannelNumber channelNumber) throws IOException {
        Validators.validateNonNull("channelNumber", channelNumber);
    }

    public CaptureStatus getCaptureStatus() throws IOException {
        byte value = getDevicePropValueAsUINT8(PropertyCode.CAPTURE_STATUS.getCode());
        return CaptureStatus.valueOf(value);
    }

    public int getRecordingTime() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.RECORDING_TIME.getCode());
        return value.intValue();
    }

    public int getRemainingRecordingTime() throws IOException {
        UINT16 value = getDevicePropValueAsUINT16(PropertyCode.REMAINING_RECORDING_TIME.getCode());
        return value.intValue();
    }
}
