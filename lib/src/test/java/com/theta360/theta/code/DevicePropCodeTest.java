package com.theta360.theta.code;

import com.theta360.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DevicePropCodeTest {
    @Test
    public void value() {
        assertThat(DevicePropCode.BATTERY_LEVEL.value(), is(new UINT16(0x5001)));
    }
}
