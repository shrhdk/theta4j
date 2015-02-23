package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.PtpException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GetObjectTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetObjectTest.class);

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

    // Operations

    @Test
    public void getObject() throws IOException, ThetaException {
        List<Long> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("raw.jpg"))) {
            theta.getObject(objectHandles.get(2), file);
        }
    }

    @Test
    public void getThumb() throws IOException, ThetaException {
        List<Long> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("thumb.jpg"))) {
            theta.getThumb(objectHandles.get(2), file);
        }
    }

    @Test
    public void getResizedImageObject() throws IOException, ThetaException {
        List<Long> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("resized.jpg"))) {
            theta.getResizedImageObject(objectHandles.get(2), file);
        }
    }
}
