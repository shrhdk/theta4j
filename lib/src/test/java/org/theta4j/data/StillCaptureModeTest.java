/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.junit.Test;
import org.theta4j.ptp.type.UINT16;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StillCaptureModeTest {
    @Test
    public void value() {
        assertThat(StillCaptureMode.VIDEO.value(), is(UINT16.ZERO));
    }
}
