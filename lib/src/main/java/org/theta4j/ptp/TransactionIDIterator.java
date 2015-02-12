package org.theta4j.ptp;

import org.theta4j.ptp.type.UINT32;

import java.util.Iterator;

/**
 * Iterator of Transaction ID for PTP-IP Packet
 */
public final class TransactionIDIterator implements Iterator<UINT32> {
    private long current = 0;

    /**
     * Always returns true.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the following values from initial state.
     * 0, 1, 2, ..., 0xFFFFFFFE, 0, 1, 2, ...
     */
    @Override
    public UINT32 next() {
        if (0xFFFF_FFFFL <= current) {
            current = 0;
        }

        return new UINT32(current++);
    }

    /**
     * Unsupported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
