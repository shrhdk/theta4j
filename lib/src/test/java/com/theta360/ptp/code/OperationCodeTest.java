package com.theta360.ptp.code;

import com.theta360.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OperationCodeTest {
    @Test
    public void value() {
        assertThat(OperationCode.UNDEFINED.value(), is(new UINT16(0x1000)));
    }
}
