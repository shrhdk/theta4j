/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Closer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ObjectTest extends BaseThetaTest {
    private static UINT32 objectHandle;

    @BeforeClass
    public static void capture() throws IOException, InterruptedException {
        objectHandle = theta.initiateCapture();
    }

    @AfterClass
    public static void delete() throws IOException, InterruptedException {
        if (theta != null && objectHandle != null) {
            theta.deleteObject(objectHandle);
        }
    }

    @Test
    public void getObjectInfo() throws IOException {
        theta.getObjectInfo(objectHandle);
    }

    @Test
    public void getObjectHandles() throws IOException {
        List<UINT32> objectHandles = theta.getObjectHandles();
        assertThat(objectHandles, hasItems(objectHandle));
    }

    @Test
    public void getNumObjects() throws IOException {
        // numObjects is always 1 or more because THETA always has directory,
        // and in this case we captured new image at BeforeClass.
        // Therefore, numObjects must be 2 or more;
        assertTrue(2 <= theta.getNumObjects());
    }

    @Test
    public void getObject() throws IOException {
        final Closer closer = new Closer();
        try {
            final OutputStream out = closer.push(new ByteArrayOutputStream());
            theta.getObject(objectHandle, out);
//            TestUtils.isValidJPEG(tempFile);
        } finally {
            closer.close();
        }
    }

    @Test
    public void getThumb() throws IOException {
        final Closer closer = new Closer();
        try {
            final OutputStream out = closer.push(new ByteArrayOutputStream());
            theta.getThumb(objectHandle, out);
//            TestUtils.isValidJPEG(tempFile);
        } finally {
            closer.close();
        }
    }

    @Test
    public void getResizedImageObject() throws IOException {
        final Closer closer = new Closer();
        try {
            final OutputStream out = closer.push(new ByteArrayOutputStream());
            theta.getResizedImageObject(objectHandle, out);
//            TestUtils.isValidJPEG(tempFile);
        } finally {
            closer.close();
        }
    }
}
