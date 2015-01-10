package com.theta360.ptp.io;

import com.theta360.ptp.packet.*;
import com.theta360.ptp.type.UINT32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public final class PacketInputStream implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketInputStream.class);

    private final PtpInputStream pis;

    public PacketInputStream(InputStream is) {
        this.pis = new PtpInputStream(is);
    }

    // Check Next

    public long nextLength() throws IOException {
        pis.mark(UINT32.SIZE);
        UINT32 length = pis.readUINT32();
        pis.reset();

        return length.longValue();
    }

    public PtpIpPacket.Type nextType() throws IOException {
        pis.mark(UINT32.SIZE + UINT32.SIZE);

        // Skip Length Value on Header
        long sizeToSkip = UINT32.SIZE;
        if (pis.skip(sizeToSkip) != sizeToSkip) {
            throw new IOException();
        }

        UINT32 typeValue = pis.readUINT32();

        pis.reset();

        return PtpIpPacket.Type.valueOf(typeValue);
    }

    // Read Packet

    public InitCommandRequestPacket readInitCommandRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_COMMAND_REQUEST);

        return InitCommandRequestPacket.read(pis);
    }

    public InitCommandAckPacket readInitCommandAckPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_COMMAND_ACK);

        return InitCommandAckPacket.read(pis);
    }

    public InitEventRequestPacket readInitEventRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_EVENT_REQUEST);

        return InitEventRequestPacket.read(pis);
    }

    public InitEventAckPacket readInitEventAckPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_EVENT_ACK);

        return InitEventAckPacket.read(pis);
    }

    public InitFailPacket readInitFailPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_FAIL);

        return InitFailPacket.read(pis);
    }

    public OperationRequestPacket readOperationRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.OPERATION_REQUEST);

        return OperationRequestPacket.read(pis);
    }

    public OperationResponsePacket readOperationResponsePacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.OPERATION_RESPONSE);

        return OperationResponsePacket.read(pis);
    }

    public EventPacket readEventPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.EVENT);

        return EventPacket.read(pis);
    }

    public StartDataPacket readStartDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.START_DATA);

        return StartDataPacket.read(pis);
    }

    public DataPacket readDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.DATA);

        return DataPacket.read(pis);
    }

    public EndDataPacket readEndDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.END_DATA);

        return EndDataPacket.read(pis);
    }

    public CancelPacket readCancelPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.CANCEL);

        return CancelPacket.read(pis);
    }

    public ProbeRequestPacket readProbeRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.PROBE_REQUEST);

        return ProbeRequestPacket.read(pis);
    }

    public ProbeResponsePacket readProbeResponsePacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.PROBE_RESPONSE);

        return ProbeResponsePacket.read(pis);
    }

    // Public Utility

    public byte[] readData() throws IOException {
        StartDataPacket startDataPacket = readStartDataPacket();

        // Receive Data
        int length = startDataPacket.getTotalDataLength().bigInteger().intValue();
        int pos = 0;
        byte[] data = new byte[length];
        for (; ; ) {
            PtpIpPacket.Type type = nextType();
            byte[] dataPayload = readDataPayload();
            System.arraycopy(dataPayload, 0, data, pos, dataPayload.length);
            pos += dataPayload.length;

            if (type == PtpIpPacket.Type.END_DATA) {
                break;
            }
        }

        return data;
    }

    // Closeable

    @Override
    public void close() throws IOException {
        pis.close();
    }

    // Utility

    private void assertNextTypeIs(PtpIpPacket.Type expected) throws IOException {
        PtpIpPacket.Type actual = nextType();

        if (actual != expected) {
            throw new RuntimeException(String.format("Unexpected packet type: Actual=%s, Expected=%s.", actual, expected));
        }
    }

    private byte[] readDataPayload() throws IOException {
        PtpIpPacket.Type type = nextType();

        switch (type) {
            case DATA:
                DataPacket data = readDataPacket();
                LOGGER.info("Received Data: " + data);
                return data.getDataPayload();
            case END_DATA:
                EndDataPacket endData = readEndDataPacket();
                LOGGER.info("Received EndData: " + endData);
                return endData.getDataPayload();
            default:
                throw new IOException("Unexpected Packet Type: " + nextType());
        }
    }
}
