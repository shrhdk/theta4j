package com.theta360.ptp.io;

import com.theta360.ptp.data.TransactionID;
import com.theta360.ptp.packet.PtpIpPacket;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream of PTP-IP Packet.
 */
public final class PacketOutputStream implements Closeable {
    private final OutputStream os;

    /**
     * Wrap OutputStream by PacketOutputStream
     *
     * @param os
     */
    public PacketOutputStream(OutputStream os) {
        this.os = os;
    }

    /**
     * Write PTP-IP Packet to the stream.
     *
     * @param packet
     * @throws IOException
     */
    public void write(PtpIpPacket packet) throws IOException {
        os.write(packet.bytes());
        os.flush();
    }

    /**
     * Write data to the stream as content of DataPhase (StartData -> [Data] -> EndData)
     *
     * @param transactionID
     * @param data
     * @throws IOException
     */
    public void writeData(TransactionID transactionID, byte[] data) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
