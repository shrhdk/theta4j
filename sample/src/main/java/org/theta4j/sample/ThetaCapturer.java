/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.sample;

import org.theta4j.InitiateCaptureCallback;
import org.theta4j.Theta;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.type.UINT32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public final class ThetaCapturer {
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(final String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("theta-capture.jar captures image from THETA.");
            System.out.println("java -jar theta-capture-x.x.x.jar <dst-file-name>");
            return;
        }

        try (Theta theta = new Theta()) {
            // Get the model name from THETA
            DeviceInfo deviceInfo = theta.getDeviceInfo();
            System.out.println("Connected to " + deviceInfo.getModel());

            // Start capture, wait, and save to the file
            theta.initiateCapture(new InitiateCaptureCallback() {
                @Override
                public void onObjectAdded(UINT32 objectHandle) {
                    System.out.println("Object Added");
                    File file = new File(args[0]);
                    try (OutputStream output = new FileOutputStream(file)) {
                        theta.getResizedImageObject(objectHandle, output);
                    } catch (IOException e) {
                        System.out.println("Error occurred while downloading image: " + e);
                    }
                }

                @Override
                public void onStoreFull() {
                    System.out.println("Store Full");
                    latch.countDown();
                }

                @Override
                public void onCaptureComplete() {
                    System.out.println("Capture Complete");
                    latch.countDown();
                }
            });

            latch.await();
        }
    }

    private ThetaCapturer() {
        throw new AssertionError();
    }
}
