/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.sample;

import org.theta4j.Theta;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.type.UINT32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ThetaCapturer {

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

            // Capture
            System.out.println("Start to capture...");
            UINT32 objectHandle = theta.initiateCapture();
            System.out.println("Finish to capture.");

            // Download
            try (OutputStream output = new FileOutputStream(new File(args[0]))) {
                System.out.println("Start to download image...");
                theta.getResizedImageObject(objectHandle, output);
                System.out.println("Finish to download image.");
            } catch (IOException e) {
                System.out.println("Error occurred while downloading image: " + e);
            }
        }
    }

    private ThetaCapturer() {
        throw new AssertionError();
    }
}
