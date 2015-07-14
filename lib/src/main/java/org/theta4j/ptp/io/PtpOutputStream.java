/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.io;

import org.theta4j.ptp.type.PtpInteger;
import org.theta4j.ptp.type.STR;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * OutputStream of the generic data type of PTP.
 */
public class PtpOutputStream extends OutputStream {
    private final OutputStream os;

    // Constructor

    /**
     * Wrap OutputStream by PtpOutputStream.
     *
     * @param os
     */
    public PtpOutputStream(OutputStream os) {
        Validators.notNull("os", os);

        this.os = os;
    }

    // PTP Generic Type

    /**
     * Write PTP Integer value to the stream.
     *
     * @param integer
     * @throws IOException
     */
    public void write(PtpInteger integer) throws IOException {
        write(integer.bytes());
    }

    /**
     * Write List of PTP Integer value to the stream.
     */
    public void write(List<? extends PtpInteger> aint) throws IOException {
        for (PtpInteger integer : aint) {
            write(integer.bytes());
        }
    }

    /**
     * Write String to the stream as PTP String.
     *
     * @param str
     * @throws IOException
     */
    public void write(String str) throws IOException {
        write(STR.toBytes(str));
    }

    // OutputStream

    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        os.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        os.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
