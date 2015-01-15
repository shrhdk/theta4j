package com.theta360.theta;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpException;
import com.theta360.ptp.type.UINT32;
import com.theta360.test.categories.IntegrationTest;
import com.theta360.theta.code.DevicePropCode;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Category(IntegrationTest.class)
public class DevicePropDescThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicePropDescThetaTest.class);
    private static final UINT32 SESSION_ID = new UINT32(1);

    private static PtpEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onDevicePropChanged(UINT32 devicePropCode) {
            LOGGER.info("onDevicePropChanged: " + devicePropCode);
        }

        @Override
        public void onStoreFull(UINT32 storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(UINT32 transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException, PtpException {
        theta = new Theta();
        theta.addListener(listener);
        theta.openSession(SESSION_ID);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException, PtpException {
        theta.closeSession();
        theta.close();
    }

    // Get DevicePropDesc

    @Test
    public void getBatteryLevel() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of BatteryLevel: " + theta.getDevicePropDesc(DevicePropCode.BATTERY_LEVEL));
    }

    @Test
    public void getWhiteBalance() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of WhiteBalance: " + theta.getDevicePropDesc(DevicePropCode.WHITE_BALANCE));
    }

    @Test
    public void getExposureIndex() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of ExposureIndex: " + theta.getDevicePropDesc(DevicePropCode.EXPOSURE_INDEX));
    }

    @Test
    public void getExposureBiasCompensation() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of ExposureBiasCompensation: " + theta.getDevicePropDesc(DevicePropCode.EXPOSURE_BIAS_COMPENSATION));
    }

    @Test
    public void getDateTime() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of DateTime: " + theta.getDevicePropDesc(DevicePropCode.DATE_TIME));
    }

    @Test
    public void getStillCaptureMode() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of StillCaptureMode(: " + theta.getDevicePropDesc(DevicePropCode.STILL_CAPTURE_MODE));
    }

    @Test
    public void getTimelapseNumber() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of TimelapseNumber: " + theta.getDevicePropDesc(DevicePropCode.TIMELAPSE_NUMBER));
    }

    @Test
    public void getTimelapseInterval() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of TimelapseInterval: " + theta.getDevicePropDesc(DevicePropCode.TIMELAPSE_INTERVAL));
    }

    @Test
    public void getAudioVolume() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of AudioVolume: " + theta.getDevicePropDesc(DevicePropCode.AUDIO_VOLUME));
    }

    @Test
    public void getErrorInfo() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of ErrorInfo: " + theta.getDevicePropDesc(DevicePropCode.ERROR_INFO));
    }

    @Test
    public void getShutterSpeed() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of ShutterSpeed: " + theta.getDevicePropDesc(DevicePropCode.SHUTTER_SPEED));
    }

    @Test
    public void getGpsInfo() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of GpsInfo: " + theta.getDevicePropDesc(DevicePropCode.GPS_INFO));
    }

    @Test
    public void getAutoPowerOffDelay() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of AutoPowerOffDelay: " + theta.getDevicePropDesc(DevicePropCode.AUTO_POWER_OFF_DELAY));
    }

    @Test
    public void getSleepDelay() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of SleepDelay: " + theta.getDevicePropDesc(DevicePropCode.SLEEP_DELAY));
    }

    @Test
    public void getChannelNumber() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of ChannelNumber: " + theta.getDevicePropDesc(DevicePropCode.CHANNEL_NUMBER));
    }

    @Test
    public void getCaptureStatus() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of CaptureStatus: " + theta.getDevicePropDesc(DevicePropCode.CAPTURE_STATUS));
    }

    @Test
    public void getRecordingTime() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of RecordingTime: " + theta.getDevicePropDesc(DevicePropCode.RECORDING_TIME));
    }

    @Test
    public void getRemainingRecordingTime() throws IOException, PtpException {
        LOGGER.info("DevicePropDesc of RemainingRecordingTime: " + theta.getDevicePropDesc(DevicePropCode.REMAINING_RECORDING_TIME));
    }
}
