package com.theta360.ptp.io;

import com.theta360.ptp.packet.PtpIpPacket;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public final class PacketOutputStream implements Closeable {
    private final OutputStream os;

    public PacketOutputStream(OutputStream os) {
        this.os = os;
    }

    public void write(PtpIpPacket packet) throws IOException {
        os.write(packet.bytes());
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
