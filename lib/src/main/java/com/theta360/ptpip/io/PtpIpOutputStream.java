package com.theta360.ptpip.io;

import com.theta360.ptp.io.PtpOutputStream;
import com.theta360.ptpip.packet.PtpIpPacket;
import com.theta360.ptpip.packet.StartDataPacket;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptp.type.UINT64;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream of PTP-IP.
 */
public final class PtpIpOutputStream implements Closeable {
    private final PtpOutputStream os;

    /**
     * Wrap OutputStream by PacketOutputStream
     *
     * @param os
     */
    public PtpIpOutputStream(OutputStream os) {
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
    public void writeData(UINT32 transactionID, byte[] data) throws IOException {
        // Send StartData
        StartDataPacket startDataPacket = new StartDataPacket(transactionID, new UINT64(data.length));
        write(startDataPacket);

        long packetLength = UINT32.SIZE + PtpIpPacket.Type.SIZE + UINT32.SIZE + data.length;

        // Send EndData
        os.write(new UINT32(packetLength));             // Length
        os.write(PtpIpPacket.Type.END_DATA.value());    // Packet Type
        os.write(transactionID);                        // Transaction ID
        os.write(data);                                 // Data Payload
        os.flush();
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
