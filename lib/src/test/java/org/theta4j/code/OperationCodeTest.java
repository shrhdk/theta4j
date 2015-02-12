package org.theta4j.code;

import org.theta4j.ptp.type.UINT16;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OperationCodeTest {
    @Test
    public void value() {
        assertThat(OperationCode.GET_RESIZED_IMAGE_OBJECT.value(), is(new UINT16(0x1022)));
    }
}
