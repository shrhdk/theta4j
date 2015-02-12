package com.theta360.theta;

import com.theta360.ptp.PtpException;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.data.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public class SetDevicePropTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetDevicePropTest.class);
    private static final UINT32 SESSION_ID = new UINT32(1);

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
    public static void connect() throws IOException, PtpException {
        theta = new Theta();
        theta.addListener(listener);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException, PtpException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Test

    @Test
    public void setWhiteBalance() throws IOException, PtpException {
        theta.setWhiteBalance(WhiteBalance.COOL_WHITE_FLUORESCENT_LAMP);
    }

    @Test
    public void setExposureIndex() throws IOException, PtpException {
        theta.setExposureIndex(ISOSpeed.ISO_100);
    }

    @Test
    public void setExposureBiasCompensation() throws IOException, PtpException {
        theta.setExposureBiasCompensation(300);
    }

    @Test
    public void setDateTime() throws IOException, PtpException {
        theta.setDateTime(new Date());
    }

    @Test
    public void setStillCaptureMode() throws IOException, PtpException {
        theta.setStillCaptureMode(StillCaptureMode.SINGLE_SHOT);
    }

    @Test
    public void setTimelapseNumber() throws IOException, PtpException {
        theta.setTimelapseNumber(0);
    }

    @Test
    public void setTimelapseInterval() throws IOException, PtpException {
        theta.setTimelapseInterval(5000);
    }

    @Test
    public void setAudioVolume() throws IOException, PtpException {
        theta.setAudioVolume(0);
    }

    @Test
    public void setShutterSpeed() throws IOException, PtpException {
        theta.setShutterSpeed(ShutterSpeed.SS_1_10);
    }

    @Test
    public void setGPSInfo() throws IOException, PtpException {
        theta.setGPSInfo("35.671190,139.764642+000.00m@19630103T000000+0900,WGS84");
    }

    @Test
    public void setAutoPowerOffDelay() throws IOException, PtpException {
        theta.setAutoPowerOffDelay(0);
    }

    @Test
    public void setSleepDelay() throws IOException, PtpException {
        theta.setSleepDelay(0);
    }

    @Test
    public void setChannelNumber() throws IOException, PtpException {
        theta.setChannelNumber(ChannelNumber.RANDOM);
    }
}
