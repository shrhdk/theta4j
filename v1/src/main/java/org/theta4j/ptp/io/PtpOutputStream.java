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
    private final OutputStream out;

    // Constructor

    /**
     * Wrap OutputStream by PtpOutputStream.
     *
     * @param out The underlying output stream.
     * @throws NullPointerException if out is null.
     */
    public PtpOutputStream(OutputStream out) {
        Validators.notNull("out", out);

        this.out = out;
    }

    // PTP Generic Type

    /**
     * Write PTP Integer value to the stream.
     *
     * @param integer the integer to write to stream.
     * @throws IOException          if an I/O error occurs while writing the stream.
     * @throws NullPointerException if integer is null.
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
     * @param str the string to write to stream.
     * @throws IOException          if an I/O error occurs while writing the stream.
     * @throws NullPointerException if str is null.
     */
    public void write(String str) throws IOException {
        write(STR.toBytes(str));
    }

    // OutputStream

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {
        out.write(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        out.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        out.close();
    }
}
