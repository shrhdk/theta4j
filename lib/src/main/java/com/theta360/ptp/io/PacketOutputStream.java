package com.theta360.ptp.io;

import com.theta360.ptp.data.TransactionID;
import com.theta360.ptp.packet.PtpIpPacket;
import com.theta360.ptp.packet.StartDataPacket;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * OutputStream of PTP-IP Packet.
 */
public final class PacketOutputStream implements Closeable {
    private final PtpOutputStream os;

    /**
     * Wrap OutputStream by PacketOutputStream
     *
     * @param os
     */
    public PacketOutputStream(OutputStream os) {
        this.os = new PtpOutputStream(os);
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
        // Send StartData
        StartDataPacket startDataPacket = new StartDataPacket(transactionID.next(), new UINT64(BigInteger.valueOf(data.length)));
        write(startDataPacket);

        // Send EndData
        os.write(new UINT32(UINT32.SIZE + PtpIpPacket.Type.SIZE + UINT32.SIZE + data.length));
        os.write(PtpIpPacket.Type.END_DATA.getCode());
        os.write(transactionID.next());
        os.write(data);
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
