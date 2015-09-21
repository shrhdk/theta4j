/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp.io;

import org.theta4j.ptp.type.*;
import org.theta4j.util.Validators;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * InputStream of the generic data type of PTP.
 */
public final class PtpInputStream extends InputStream {
    private final InputStream in;

    // Constructor

    /**
     * Wrap InputStream by PtpInputStream.
     *
     * @param in The underlying input stream.
     * @throws NullPointerException if in is null.
     */
    public PtpInputStream(InputStream in) {
        Validators.notNull("in", in);

        this.in = new BufferedInputStream(in);
    }

    // PTP Generic Type (Integer)

    /**
     * Read INT8 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public INT8 readINT8() throws IOException {
        return INT8.read(in);
    }

    /**
     * Read UINT8 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public UINT8 readUINT8() throws IOException {
        return UINT8.read(in);
    }

    /**
     * Read INT16 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public INT16 readINT16() throws IOException {
        return INT16.read(in);
    }

    /**
     * Read UINT16 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public UINT16 readUINT16() throws IOException {
        return UINT16.read(in);
    }

    /**
     * Read INT32 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public INT32 readINT32() throws IOException {
        return INT32.read(in);
    }

    /**
     * Read UINT32 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public UINT32 readUINT32() throws IOException {
        return UINT32.read(in);
    }

    /**
     * Read INT64 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public INT64 readINT64() throws IOException {
        return INT64.read(in);
    }

    /**
     * Read UINT64 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public UINT64 readUINT64() throws IOException {
        return UINT64.read(in);
    }

    /**
     * Read INT128 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public INT128 readINT128() throws IOException {
        return INT128.read(in);
    }

    /**
     * Read UINT128 value from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public UINT128 readUINT128() throws IOException {
        return UINT128.read(in);
    }

    // PTP Generic Type (Array)

    /**
     * Read list of INT8 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<INT8> readAINT8() throws IOException {
        return AINT8.read(in);
    }

    /**
     * Read list of UINT8 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<UINT8> readAUINT8() throws IOException {
        return AUINT8.read(in);
    }

    /**
     * Read list of INT16 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<INT16> readAINT16() throws IOException {
        return AINT16.read(in);
    }

    /**
     * Read list of UINT16 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<UINT16> readAUINT16() throws IOException {
        return AUINT16.read(in);
    }

    /**
     * Read list of INT32 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<INT32> readAINT32() throws IOException {
        return AINT32.read(in);
    }

    /**
     * Read list of UINT16 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<UINT32> readAUINT32() throws IOException {
        return AUINT32.read(in);
    }

    /**
     * Read list of INT64 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<INT64> readAINT64() throws IOException {
        return AINT64.read(in);
    }

    /**
     * Read list of UINT64 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<UINT64> readAUINT64() throws IOException {
        return AUINT64.read(in);
    }

    /**
     * Read list of INT128 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<INT128> readAINT128() throws IOException {
        return AINT128.read(in);
    }

    /**
     * Read list of UINT128 from the stream.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public List<UINT128> readAUINT128() throws IOException {
        return AUINT128.read(in);
    }

    // // PTP Generic Type (String)

    /**
     * Read list of String from the stream as PTP String.
     *
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public String readString() throws IOException {
        return STR.read(in);
    }

    // read *

    /**
     * Read data as DataType of specified code.
     *
     * @param dataType The type of data to read.
     * @throws IOException if an I/O error occurs while reading the stream.
     */
    public Object readAs(DataType dataType) throws IOException {
        Validators.notNull("dataType", dataType);

        switch (dataType) {
            case INT8:
                return readINT8();
            case UINT8:
                return readUINT8();
            case INT16:
                return readINT16();
            case UINT16:
                return readUINT16();
            case INT32:
                return readINT32();
            case UINT32:
                return readUINT32();
            case INT64:
                return readINT64();
            case UINT64:
                return readUINT64();
            case INT128:
                return readINT128();
            case UINT128:
                return readUINT128();
            case AINT8:
                return readAINT8();
            case AUINT8:
                return readAUINT8();
            case AINT16:
                return readAINT16();
            case AUINT16:
                return readAUINT16();
            case AINT32:
                return readAINT32();
            case AUINT32:
                return readAUINT32();
            case AINT64:
                return readAINT64();
            case AUINT64:
                return readAUINT64();
            case AINT128:
                return readAINT128();
            case AUINT128:
                return readAUINT128();
            case STR:
                return readString();
            default:
                throw new UnsupportedOperationException(dataType + " is not supported.");
        }
    }

    // InputStream

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b) throws IOException {
        return in.read(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() throws IOException {
        return in.available();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        in.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mark(int readlimit) {
        in.mark(readlimit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() throws IOException {
        in.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean markSupported() {
        return in.markSupported();
    }
}
