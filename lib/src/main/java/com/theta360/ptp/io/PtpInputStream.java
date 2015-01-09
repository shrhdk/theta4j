package com.theta360.ptp.io;

import com.theta360.ptp.type.ConvertException;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class PtpInputStream implements Closeable {
    private static Logger LOGGER = LoggerFactory.getLogger(PtpInputStream.class);

    private final InputStream is;

    public PtpInputStream(byte[] bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    public PtpInputStream(InputStream inputStream) {
        Validators.validateNonNull("inputStream", inputStream);

        this.is = inputStream;
    }

    public int read() throws IOException {
        return is.read();
    }

    public UINT16 readUINT16() throws IOException {
        byte b0 = (byte) is.read();
        byte b1 = (byte) is.read();

        return new UINT16(b0, b1);
    }

    public UINT32 readUINT32() throws IOException {
        byte b0 = (byte) is.read();
        byte b1 = (byte) is.read();
        byte b2 = (byte) is.read();
        byte b3 = (byte) is.read();

        return new UINT32(b0, b1, b2, b3);
    }

    public List<UINT16> readAUINT16() throws IOException {
        long length = readUINT32().longValue();

        List<UINT16> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(readUINT16());
        }

        return list;
    }

    public List<UINT32> readAUINT32() throws IOException {
        long length = readUINT32().longValue();

        List<UINT32> list = new ArrayList<>((int) length);

        for (int i = 0; i < length; i++) {
            list.add(readUINT32());
        }

        return list;
    }

    public String readString() throws IOException, ConvertException {
        int numChars = new UINT16(is.read(), 0x00).intValue();
        byte[] bytes = new byte[numChars * UINT16.SIZE];
        for (int i = 0; i < numChars * UINT16.SIZE; i++) {
            bytes[i] = (byte) is.read();
        }
        return STR.toString(bytes);
    }

    @Override
    public void close() throws IOException {
        is.close();
    }
}
