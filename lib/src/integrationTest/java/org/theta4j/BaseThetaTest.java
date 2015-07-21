/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.theta4j.data.StillCaptureMode;

import java.io.IOException;

public class BaseThetaTest {
    protected static Theta theta;

    protected static void changeStillCaptureModeFast(StillCaptureMode mode) throws IOException {
        StillCaptureMode current = theta.getStillCaptureMode();

        if (current == mode) {
            return;
        }

        theta.setStillCaptureMode(mode);

        try {
            Thread.sleep(TestParameters.INTERVAL_AFTER_OPERATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void connect() throws IOException {
        try {
            theta = new Theta();
        } catch (IOException e) {
            // retry one more
            theta = new Theta();
        }

        theta.setAudioVolume(100);
        changeStillCaptureModeFast(StillCaptureMode.SINGLE);
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        if (theta != null) {
            theta.close();
            Thread.sleep(TestParameters.INTERVAL_AFTER_CLOSE);
        }
    }
}
