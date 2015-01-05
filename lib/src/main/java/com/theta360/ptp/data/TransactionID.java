package com.theta360.ptp.data;

import com.theta360.ptp.type.UINT32;

import java.util.Iterator;

public final class TransactionID implements Iterator<UINT32> {
    private UINT32 current = new UINT32(0);

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public UINT32 next() {
        if (current.longValue() == UINT32.MAX_VALUE) {
            current = new UINT32(1);
        } else {
            current = new UINT32(current.longValue() + 1);
        }

        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
