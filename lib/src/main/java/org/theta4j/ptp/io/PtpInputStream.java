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
    private final InputStream is;

    // Constructor

    /**
     * Wrap InputStream by PtpInputStream.
     *
     * @param is
     */
    public PtpInputStream(InputStream is) {
        Validators.notNull("is", is);

        this.is = new BufferedInputStream(is);
    }

    // PTP Generic Type (Integer)

    /**
     * Read INT8 value from the stream.
     *
     * @throws IOException
     */
    public INT8 readINT8() throws IOException {
        return INT8.read(is);
    }

    /**
     * Read UINT8 value from the stream.
     *
     * @throws IOException
     */
    public UINT8 readUINT8() throws IOException {
        return UINT8.read(is);
    }

    /**
     * Read INT16 value from the stream.
     *
     * @throws IOException
     */
    public INT16 readINT16() throws IOException {
        return INT16.read(is);
    }

    /**
     * Read UINT16 value from the stream.
     *
     * @throws IOException
     */
    public UINT16 readUINT16() throws IOException {
        return UINT16.read(is);
    }

    /**
     * Read INT32 value from the stream.
     *
     * @throws IOException
     */
    public INT32 readINT32() throws IOException {
        return INT32.read(is);
    }

    /**
     * Read UINT32 value from the stream.
     *
     * @throws IOException
     */
    public UINT32 readUINT32() throws IOException {
        return UINT32.read(is);
    }

    /**
     * Read INT64 value from the stream.
     *
     * @throws IOException
     */
    public INT64 readINT64() throws IOException {
        return INT64.read(is);
    }

    /**
     * Read UINT64 value from the stream.
     *
     * @throws IOException
     */
    public UINT64 readUINT64() throws IOException {
        return UINT64.read(is);
    }

    /**
     * Read INT128 value from the stream.
     *
     * @throws IOException
     */
    public INT128 readINT128() throws IOException {
        return INT128.read(is);
    }

    /**
     * Read UINT128 value from the stream.
     *
     * @throws IOException
     */
    public UINT128 readUINT128() throws IOException {
        return UINT128.read(is);
    }

    // PTP Generic Type (Array)

    /**
     * Read list of INT8 from the stream.
     *
     * @throws IOException
     */
    public List<INT8> readAINT8() throws IOException {
        return AINT8.read(is);
    }

    /**
     * Read list of UINT8 from the stream.
     *
     * @throws IOException
     */
    public List<UINT8> readAUINT8() throws IOException {
        return AUINT8.read(is);
    }

    /**
     * Read list of INT16 from the stream.
     *
     * @throws IOException
     */
    public List<INT16> readAINT16() throws IOException {
        return AINT16.read(is);
    }

    /**
     * Read list of UINT16 from the stream.
     *
     * @throws IOException
     */
    public List<UINT16> readAUINT16() throws IOException {
        return AUINT16.read(is);
    }

    /**
     * Read list of INT32 from the stream.
     *
     * @throws IOException
     */
    public List<INT32> readAINT32() throws IOException {
        return AINT32.read(is);
    }

    /**
     * Read list of UINT16 from the stream.
     *
     * @throws IOException
     */
    public List<UINT32> readAUINT32() throws IOException {
        return AUINT32.read(is);
    }

    /**
     * Read list of INT64 from the stream.
     *
     * @throws IOException
     */
    public List<INT64> readAINT64() throws IOException {
        return AINT64.read(is);
    }

    /**
     * Read list of UINT64 from the stream.
     *
     * @throws IOException
     */
    public List<UINT64> readAUINT64() throws IOException {
        return AUINT64.read(is);
    }

    /**
     * Read list of INT128 from the stream.
     *
     * @throws IOException
     */
    public List<INT128> readAINT128() throws IOException {
        return AINT128.read(is);
    }

    /**
     * Read list of UINT128 from the stream.
     *
     * @throws IOException
     */
    public List<UINT128> readAUINT128() throws IOException {
        return AUINT128.read(is);
    }

    // // PTP Generic Type (String)

    /**
     * Read list of String from the stream as PTP String.
     *
     * @throws IOException
     */
    public String readString() throws IOException {
        return STR.read(is);
    }

    // read *

    /**
     * Read data as DataType of specified code.
     *
     * @param dataType
     * @throws IOException
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

    @Override
    public int read() throws IOException {
        return is.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return is.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return is.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return is.skip(n);
    }

    @Override
    public int available() throws IOException {
        return is.available();
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    @Override
    public void mark(int readlimit) {
        is.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        is.reset();
    }

    @Override
    public boolean markSupported() {
        return is.markSupported();
    }
}
