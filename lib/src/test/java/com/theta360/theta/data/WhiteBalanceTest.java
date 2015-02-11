package com.theta360.theta.data;

import com.theta360.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WhiteBalanceTest {
    @Test
    public void value() {
        assertThat(WhiteBalance.AUTO.value(), is(new UINT16(0x0002)));
    }
}
