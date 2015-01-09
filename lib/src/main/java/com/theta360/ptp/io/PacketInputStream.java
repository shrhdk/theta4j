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

    private final PtpInputStream is;

    public PacketInputStream(InputStream is) {
        this.is = new PtpInputStream(is);
    }

    public long nextLength() throws IOException {
        is.mark(UINT32.SIZE);
        UINT32 length = is.readUINT32();
        is.reset();

        return length.longValue();
    }

    public PtpIpPacket.Type nextType() throws IOException {
        is.mark(UINT32.SIZE + UINT32.SIZE);
        is.skip(UINT32.SIZE); // skip length header
        UINT32 typeValue = is.readUINT32();
        is.reset();

        return PtpIpPacket.Type.valueOf(typeValue);
    }

    public PtpIpPacket read() throws IOException {
        // Read length
        long packetLength = is.readUINT32().longValue();
        long payloadLength = packetLength - 8;

        // Read type
        UINT32 typeValue = is.readUINT32();
        PtpIpPacket.Type type = PtpIpPacket.Type.valueOf(typeValue);

        // Read payload
        byte[] payload = new byte[(int) payloadLength];
        for (int pos = 0; pos < payloadLength; pos++) {
            payload[pos] = (byte) is.read();
        }

        return new PtpIpPacket(type, payload);
    }

    public InitCommandRequestPacket readInitCommandRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_COMMAND_REQUEST);

        throw new UnsupportedOperationException();
    }

    public byte[] readData() throws IOException {
        // Receive StartData
        PtpIpPacket startDataPacket = read();
        StartDataPacket startData;
        try {
            startData = StartDataPacket.valueOf(startDataPacket);
        } catch (PacketException e) {
            throw new RuntimeException("Unexpected response from responder: " + startDataPacket, e);
        }
        LOGGER.info("Received StartData: " + startData);

        // Receive Data
        int length = startData.getTotalDataLength().bigInteger().intValue();
        int pos = 0;
        byte[] data = new byte[length];
        for (; ; ) {
            PtpIpPacket packet = read();
            byte[] dataPayload = getDataPayload(packet);
            System.arraycopy(dataPayload, 0, data, pos, dataPayload.length);
            pos += dataPayload.length;

            if (packet.getType() == PtpIpPacket.Type.END_DATA) {
                break;
            }
        }

        return data;
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    private void assertNextTypeIs(PtpIpPacket.Type expected) throws IOException {
        if (nextType() != expected) {
            throw new RuntimeException();
        }
    }

    private static byte[] getDataPayload(PtpIpPacket packet) {
        try {
            switch (packet.getType()) {
                case DATA:
                    DataPacket data = DataPacket.valueOf(packet);
                    LOGGER.info("Received EndData: " + data);
                    return data.getDataPayload();
                case END_DATA:
                    EndDataPacket endData = EndDataPacket.valueOf(packet);
                    LOGGER.info("Received EndData: " + endData);
                    return endData.getDataPayload();
                default:
                    throw new RuntimeException("Unexpected Packet: " + packet);
            }
        } catch (PacketException e) {
            throw new RuntimeException(e);
        }
    }
}
