package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT32;

/**
 * The protocol version list of PTP-IP
 */
public class ProtocolVersions {
    private ProtocolVersions() {
    }

    /**
     * Rev 1.0
     */
    public static final UINT32 REV_1_0 = new UINT32(0x00000100L);
}
