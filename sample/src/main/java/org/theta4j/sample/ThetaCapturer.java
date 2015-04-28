package org.theta4j.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.Theta;
import org.theta4j.ThetaEventListener;
import org.theta4j.ThetaException;
import org.theta4j.ptp.data.DeviceInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public final class ThetaCapturer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaCapturer.class);

    private static long objectHandle;
    private static final CountDownLatch waitObjectAdded = new CountDownLatch(1);

    private static ThetaEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(long objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
            ThetaCapturer.objectHandle = objectHandle;
            waitObjectAdded.countDown();
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

    public static void main(String[] args) throws IOException, ThetaException, InterruptedException {
        if (args.length != 1) {
            System.out.println("theta-capture.jar captures image from THETA.");
            System.out.println("java -jar theta-capture-x.x.x.jar <dst-file-name>");
            return;
        }

        try (Theta theta = new Theta()) {
            theta.addListener(listener);

            // Get the model name from THETA
            DeviceInfo deviceInfo = theta.getDeviceInfo();
            System.out.println(deviceInfo.getModel());

            // Capture, wait, and save to the file
            theta.initiateCapture();
            waitObjectAdded.await();
            File file = new File(args[0]);
            try (OutputStream output = new FileOutputStream(file)) {
                theta.getResizedImageObject(objectHandle, output);
            }
        }
    }

    private ThetaCapturer() {
        throw new AssertionError();
    }
}
