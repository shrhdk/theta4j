package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.data.ObjectInfo;

import java.io.IOException;
import java.util.List;

public class ThetaTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaTest.class);

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

    // Operations

    @Test
    public void getDeviceInfo() throws IOException {
        LOGGER.info("Device Info" + theta.getDeviceInfo());
    }

    @Test
    public void getObjectHandles() throws IOException {
        LOGGER.info("Object Handles: " + theta.getObjectHandles());
    }

    @Test
    public void getObjectInfo() throws IOException {
        List<Long> objectHandles = theta.getObjectHandles();
        ObjectInfo objectInfo = theta.getObjectInfo(objectHandles.get(0));
        LOGGER.info("Object Info: " + objectInfo);
    }
}
