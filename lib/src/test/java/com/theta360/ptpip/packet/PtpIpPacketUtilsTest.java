package com.theta360.ptpip.packet;

import com.theta360.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PtpIpPacketUtilsTest {
    // Constructor

    @Test
    public void isUtilClass() {
        assertTrue(TestUtils.isUtilClass(PtpIpPacketUtils.class));
    }
}
