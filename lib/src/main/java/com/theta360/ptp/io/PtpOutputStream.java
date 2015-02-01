package com.theta360.ptp.io;

import com.theta360.ptp.type.*;
import com.theta360.util.Validators;

import java.io.IOException;
import java.io.OutputStream;

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
        Validators.validateNonNull("os", os);

        this.os = os;
    }

    // PTP Generic Type (Signed Integer)

    /**
     * Write UINT8 value to the stream.
     *
     * @param int8
     * @throws IOException
     */
    public void write(INT8 int8) throws IOException {
        write(int8.bytes());
    }

    /**
     * Write UINT8 value to the stream.
     *
     * @param uint8
     * @throws IOException
     */
    public void write(UINT8 uint8) throws IOException {
        write(uint8.bytes());
    }

    /**
     * Write INT16 value to the stream.
     *
     * @param int16
     * @throws IOException
     */
    public void write(INT16 int16) throws IOException {
        write(int16.bytes());
    }

    /**
     * Write UINT16 value to the stream.
     *
     * @param uint16
     * @throws IOException
     */
    public void write(UINT16 uint16) throws IOException {
        write(uint16.bytes());
    }

    /**
     * Write UINT32 value to the stream.
     *
     * @param int32
     * @throws IOException
     */
    public void write(INT32 int32) throws IOException {
        write(int32.bytes());
    }

    /**
     * Write UINT32 value to the stream.
     *
     * @param uint32
     * @throws IOException
     */
    public void write(UINT32 uint32) throws IOException {
        write(uint32.bytes());
    }

    /**
     * Write UINT64 value to the stream.
     *
     * @param int64
     * @throws IOException
     */
    public void write(INT64 int64) throws IOException {
        write(int64.bytes());
    }

    /**
     * Write UINT64 value to the stream.
     *
     * @param uint64
     * @throws IOException
     */
    public void write(UINT64 uint64) throws IOException {
        write(uint64.bytes());
    }

    /**
     * Write INT128 value to the stream.
     *
     * @param int128
     * @throws IOException
     */
    public void write(INT128 int128) throws IOException {
        write(int128.bytes());
    }

    /**
     * Write UINT128 value to the stream.
     *
     * @param uint128
     * @throws IOException
     */
    public void write(UINT128 uint128) throws IOException {
        write(uint128.bytes());
    }

    // PTP Generic Type (Array)
    // TODO: Implement

    // PTP Generic Type (String)

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
