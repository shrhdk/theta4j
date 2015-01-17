package com.theta360.ptp.io;

import com.theta360.ptp.type.*;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * InputStream of the generic data type of PTP.
 */
public final class PtpInputStream extends InputStream {
    private static Logger LOGGER = LoggerFactory.getLogger(PtpInputStream.class);

    private final InputStream is;

    public PtpInputStream(byte[] bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    /**
     * Wrap InputStream by PtpInputStream.
     *
     * @param is
     */
    public PtpInputStream(InputStream is) {
        Validators.validateNonNull("is", is);

        this.is = new BufferedInputStream(is);
    }

    // PTP Generic Type

    public short readINT16() throws IOException {
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
     * Read UINT32 value from the stream.
     *
     * @throws IOException
     */
    public UINT32 readUINT32() throws IOException {
        return UINT32.read(is);
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
     * Read list of UINT16 from the stream.
     *
     * @throws IOException
     */
    public List<UINT16> readAUINT16() throws IOException {
        return AUINT16.read(is);
    }

    /**
     * Read list of UINT32 from the stream.
     *
     * @throws IOException
     */
    public List<UINT32> readAUINT32() throws IOException {
        return AUINT32.read(is);
    }

    /**
     * Read list of String from the stream as PTP String.
     *
     * @throws IOException
     */
    public String readString() throws IOException {
        return STR.read(is);
    }

    /**
     * Read data as DataType of specified code.
     *
     * @param dataType
     * @throws IOException
     */
    public Object readAs(DataType dataType) throws IOException {
        switch (dataType) {
            case UINT8:
                return read();
            case INT16:
                return readINT16();
            case UINT16:
                return readUINT16();
            case UINT32:
                return readUINT32();
            case UINT64:
                return readUINT64();
            case STR:
                return readString();
            default:
                throw new RuntimeException(dataType + " is not supported.");
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
