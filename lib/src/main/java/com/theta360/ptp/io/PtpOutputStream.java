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

public class PtpOutputStream extends OutputStream {
    private static Logger LOGGER = LoggerFactory.getLogger(PtpInputStream.class);

    private final OutputStream os;

    public PtpOutputStream(OutputStream outputStream) {
        Validators.validateNonNull("outputStream", outputStream);

        this.os = outputStream;
    }

    // PTP Generic Type

    public void write(UINT16 uint16) throws IOException {
        os.write(uint16.bytes());
    }

    public void write(UINT32 uint32) throws IOException {
        os.write(uint32.bytes());
    }

    public void write(UINT64 uint64) throws IOException {
        os.write(uint64.bytes());
    }

    public void write(String str) throws IOException {
        os.write(STR.toBytes(str));
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
