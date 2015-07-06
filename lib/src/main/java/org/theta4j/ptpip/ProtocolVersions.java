package org.theta4j.ptpip;

import org.theta4j.ptp.type.UINT32;

/**
 * The protocol version list of PTP-IP
 */
class ProtocolVersions {
    /**
     * Rev 1.0
     */
    public static final UINT32 REV_1_0 = new UINT32(0x00000100L);
    
    private ProtocolVersions() {
        throw new AssertionError();
    }
}
