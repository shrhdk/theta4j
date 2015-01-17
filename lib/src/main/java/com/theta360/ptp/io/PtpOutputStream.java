package com.theta360.ptp.io;

import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream of the generic data type of PTP.
 */
public class PtpOutputStream extends OutputStream {
    private static Logger LOGGER = LoggerFactory.getLogger(PtpOutputStream.class);

    private final OutputStream os;

    /**
     * Wrap OutputStream by PtpOutputStream.
     *
     * @param os
     */
    public PtpOutputStream(OutputStream os) {
        Validators.validateNonNull("os", os);

        this.os = os;
    }

    // PTP Generic Type

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
     * @param uint32
     * @throws IOException
     */
    public void write(UINT32 uint32) throws IOException {
        write(uint32.bytes());
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
