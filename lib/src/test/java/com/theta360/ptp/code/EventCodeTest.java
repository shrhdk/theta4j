package com.theta360.ptp.code;

import com.theta360.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EventCodeTest {
    @Test
    public void value() {
        assertThat(EventCode.UNDEFINED.value(), is(new UINT16(0x4000)));
    }
}
