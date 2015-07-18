/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.type.UINT32;

import java.util.Iterator;

/**
 * Iterator of Transaction ID defined in PTP
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
     * 0, 1, 2, ..., 0xFFFFFFFE, 1, 2, ...
     */
    @Override
    public UINT32 next() {
        if (0xFFFF_FFFFL <= current) {
            // Initial value is 0 but cyclic initial value is 1
            current = 1;
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
