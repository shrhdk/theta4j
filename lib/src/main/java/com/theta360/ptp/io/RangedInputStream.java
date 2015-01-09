package com.theta360.ptp.io;

import java.io.IOException;
import java.io.InputStream;

class RangedInputStream extends InputStream {
    private final InputStream is;
    private final int length;
    private int counter = 0;

    public RangedInputStream(InputStream is, int length) {
        this.is = is;
        this.length = length;
    }

    @Override
    public int available() throws IOException {
        int max = length - counter;
        int actual = is.available();

        if(max < actual) {
            return max;
        }

        return actual;
    }

    @Override
    public int read() throws IOException {
        if(length <= counter++) {
            return -1;
        }

        return is.read();
    }
}
