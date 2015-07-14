/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.junit.Test;
import org.theta4j.ptp.type.UINT8;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CaptureStatusTest {
    @Test
    public void value() {
        assertThat(CaptureStatus.IDLE.value(), is(UINT8.ZERO));
    }
}
