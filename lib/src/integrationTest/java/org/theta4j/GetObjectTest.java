/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.type.UINT32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GetObjectTest {
    private static Theta theta;

    @BeforeClass
    public static void connect() throws IOException {
        theta = new Theta();
    }

    @AfterClass
    public static void close() throws IOException, InterruptedException {
        theta.close();
        Thread.sleep(TestParameters.INTERVAL_MS);
    }

    // Operations

    @Test
    public void getObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("raw.jpg"))) {
            theta.getObject(objectHandles.get(2), file);
        }
    }

    @Test
    public void getThumb() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("thumb.jpg"))) {
            theta.getThumb(objectHandles.get(2), file);
        }
    }

    @Test
    public void getResizedImageObject() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        try (FileOutputStream file = new FileOutputStream(new File("resized.jpg"))) {
            theta.getResizedImageObject(objectHandles.get(2), file);
        }
    }
}
