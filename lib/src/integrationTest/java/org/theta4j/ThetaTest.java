/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class ThetaTest {
    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_AFTER_CLOSE);
    }

    @Test
    public void getDeviceInfo() throws IOException {
        theta.getDeviceInfo();
    }
}
