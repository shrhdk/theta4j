/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.sample;

import org.theta4j.Theta;
import org.theta4j.ThetaEventAdapter;
import org.theta4j.ThetaEventListener;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.type.UINT32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public final class ThetaCapturer {
    private static final AtomicReference<UINT32> OBJECT_HANDLE = new AtomicReference<>();
    private static final CountDownLatch WAIT_OBJECT_ADDED = new CountDownLatch(1);

    private static final ThetaEventListener LISTENER = new ThetaEventAdapter() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            OBJECT_HANDLE.set(objectHandle);
            WAIT_OBJECT_ADDED.countDown();
        }
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 1) {
            System.out.println("theta-capture.jar captures image from THETA.");
            System.out.println("java -jar theta-capture-x.x.x.jar <dst-file-name>");
            return;
        }

        try (Theta theta = new Theta()) {
            theta.addListener(LISTENER);

            // Get the model name from THETA
            DeviceInfo deviceInfo = theta.getDeviceInfo();
            System.out.println("Connected to " + deviceInfo.getModel());

            // Start capture, wait, and save to the file
            theta.initiateCapture();
            WAIT_OBJECT_ADDED.await();
            File file = new File(args[0]);
            try (OutputStream output = new FileOutputStream(file)) {
                theta.getResizedImageObject(OBJECT_HANDLE.get(), output);
            }
        }
    }

    private ThetaCapturer() {
        throw new AssertionError();
    }
}
