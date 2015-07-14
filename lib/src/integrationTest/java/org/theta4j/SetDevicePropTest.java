/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.data.*;
import org.theta4j.ptp.type.UINT32;

import java.io.IOException;
import java.util.Date;

public class SetDevicePropTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetDevicePropTest.class);
    private static final UINT32 SESSION_ID = new UINT32(1);

    private static final ThetaEventListener LISTENER = new ThetaEventListener() {
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
        public void onCaptureComplete() {
            LOGGER.info("onCaptureComplete");
        }
    };

    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
        theta.addListener(LISTENER);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Test

    @Test
    public void setWhiteBalance() throws IOException {
        theta.setWhiteBalance(WhiteBalance.COOL_WHITE_FLUORESCENT_LAMP);
    }

    @Test
    public void setExposureIndex() throws IOException {
        theta.setExposureIndex(ISOSpeed.ISO_100);
    }

    @Test
    public void setExposureBiasCompensation() throws IOException {
        theta.setExposureBiasCompensation(300);
    }

    @Test
    public void setDateTime() throws IOException {
        theta.setDateTime(new Date());
    }

    @Test
    public void setStillCaptureMode() throws IOException {
        theta.setStillCaptureMode(StillCaptureMode.SINGLE_SHOT);
    }

    @Test
    public void setTimelapseNumber() throws IOException {
        theta.setTimelapseNumber(0);
    }

    @Test
    public void setTimelapseInterval() throws IOException {
        theta.setTimelapseInterval(5000);
    }

    @Test
    public void setAudioVolume() throws IOException {
        theta.setAudioVolume(0);
    }

    @Test
    public void setShutterSpeed() throws IOException {
        theta.setShutterSpeed(ShutterSpeed.SS_1_10);
    }

    @Test
    public void setGPSInfo() throws IOException {
        theta.setGPSInfo("35.671190,139.764642+000.00m@19630103T000000+0900,WGS84");
    }

    @Test
    public void setAutoPowerOffDelay() throws IOException {
        theta.setAutoPowerOffDelay(0);
    }

    @Test
    public void setSleepDelay() throws IOException {
        theta.setSleepDelay(0);
    }

    @Test
    public void setChannelNumber() throws IOException {
        theta.setChannelNumber(ChannelNumber.RANDOM);
    }
}
