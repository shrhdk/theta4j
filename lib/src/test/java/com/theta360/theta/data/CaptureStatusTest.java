package com.theta360.theta.data;

import com.theta360.ptp.type.UINT8;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CaptureStatusTest {
    @Test
    public void value() {
        assertThat(CaptureStatus.IDLE.value(), is(UINT8.ZERO));
    }
}
