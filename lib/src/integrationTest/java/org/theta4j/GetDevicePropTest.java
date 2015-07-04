package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GetDevicePropTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetDevicePropTest.class);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
        }

        @Override
        public void onCaptureStatusChanged() {
            LOGGER.info("onCaptureStatusChanged");
        }

        @Override
        public void onRecordingTimeChanged() {
            LOGGER.info("onRecordingTimeChanged");
        }

        @Override
        public void onRemainingRecordingTimeChanged() {
            LOGGER.info("onRemainingRecordingTimeChanged");
        }

        @Override
        public void onStoreFull(long storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(long transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
        theta.addListener(listener);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Test

    @Test
    public void getBatteryLevel() throws IOException {
        LOGGER.info("Battery Level: " + theta.getBatteryLevel());
    }

    @Test
    public void getWhiteBalance() throws IOException {
        LOGGER.info("White Balance: " + theta.getWhiteBalance());
    }

    @Test
    public void getExposureIndex() throws IOException {
        LOGGER.info("Exposure Index: " + theta.getExposureIndex());
    }

    @Test
    public void getExposureBiasCompensation() throws IOException {
        LOGGER.info("Exposure Bias Compensation: " + theta.getExposureIndex());
    }

    @Test
    public void getDateTime() throws IOException {
        LOGGER.info("DateTime: " + theta.getDateTime());
    }

    @Test
    public void getStillCaptureMode() throws IOException {
        LOGGER.info("Still Capture Mode: " + theta.getStillCaptureMode());
    }

    @Test
    public void getTimelapseNumber() throws IOException {
        LOGGER.info("Timelapse Number: " + theta.getTimelapseNumber());
    }

    @Test
    public void getTimelapseInterval() throws IOException {
        LOGGER.info("Timelapse Interval: " + theta.getTimelapseInterval());
    }

    @Test
    public void getAudioVolume() throws IOException {
        LOGGER.info("Audio Volume: " + theta.getAudioVolume());
    }

    @Test
    public void getErrorInfo() throws IOException {
        LOGGER.info("Error Info: " + theta.getErrorInfo());
    }

    @Test
    public void getShutterSpeed() throws IOException {
        LOGGER.info("Shutter Speed: " + theta.getShutterSpeed());
    }

    @Test
    public void getGPSInfo() throws IOException {
        LOGGER.info("GPS Info: " + theta.getGPSInfo());
    }

    @Test
    public void getAutoPowerOffDelay() throws IOException {
        LOGGER.info("Auto Power Delay: " + theta.getAutoPowerOffDelay());
    }

    @Test
    public void getSleepDelay() throws IOException {
        LOGGER.info("Sleep Delay: " + theta.getSleepDelay());
    }

    @Test
    public void getChannelNumber() throws IOException {
        LOGGER.info("Channel Number: " + theta.getChannelNumber());
    }

    @Test
    public void getCaptureStatus() throws IOException {
        LOGGER.info("Capture Status: " + theta.getCaptureStatus());
    }

    @Test
    public void getRecordingTime() throws IOException {
        LOGGER.info("Recording Time: " + theta.getRecordingTime());
    }

    @Test
    public void getRemainingRecordingTime() throws IOException {
        LOGGER.info("Remaining Recording Time: " + theta.getRemainingRecordingTime());
    }
}
