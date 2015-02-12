package org.theta4j.ptp.code;

import org.theta4j.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EventCodeTest {
    @Test
    public void value() {
        assertThat(EventCode.UNDEFINED.value(), is(new UINT16(0x4000)));
    }
}
