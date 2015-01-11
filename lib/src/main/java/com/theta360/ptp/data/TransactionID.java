package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT32;

import java.util.Iterator;

/**
 * Iterator of Transaction ID for PTP-IP Packet
 */
public final class TransactionID implements Iterator<UINT32> {
    private UINT32 current = new UINT32(0);

    /**
     * Always returns true.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the following values from initial state.
     * 1, 2, ..., [Max value of UINT32], 1, 2, ...
     */
    @Override
    public UINT32 next() {
        if (current.longValue() == UINT32.MAX_VALUE) {
            current = new UINT32(1);
        } else {
            current = new UINT32(current.longValue() + 1);
        }

        return current;
    }

    /**
     * Unsupported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
