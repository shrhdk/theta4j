package org.theta4j.ptpip.packet;

import org.junit.Test;
import org.theta4j.TestUtils;

import static org.junit.Assert.assertTrue;

public class PtpIpPacketUtilsTest {
    // Constructor

    @Test
    public void isUtilClass() {
        assertTrue(TestUtils.isUtilClass(PtpIpPacketUtils.class));
    }
}
