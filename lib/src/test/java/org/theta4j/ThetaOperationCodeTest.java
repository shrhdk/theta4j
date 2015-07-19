/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j;

import org.junit.Test;
import org.theta4j.ptp.type.UINT16;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThetaOperationCodeTest {
    @Test
    public void value() {
        assertThat(ThetaOperationCode.GET_RESIZED_IMAGE_OBJECT.value(), is(new UINT16(0x1022)));
    }
}
