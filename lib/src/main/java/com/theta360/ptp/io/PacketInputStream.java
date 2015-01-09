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

    private final InputStream is;

    public PacketInputStream(InputStream is) {
        this.is = is;
    }

    public PtpIpPacket read() throws IOException {
        // Read length
        byte[] lengthBytes = new byte[4];
        for (int pos = 0; pos < 4; pos++) {
            lengthBytes[pos] = (byte) is.read();
        }
        long packetLength = new UINT32(lengthBytes).longValue();
        long payloadLength = packetLength - 8;

        // Read type
        byte[] typeBytes = new byte[4];
        for (int pos = 0; pos < 4; pos++) {
            typeBytes[pos] = (byte) is.read();
        }
        PtpIpPacket.Type type = PtpIpPacket.Type.valueOf(typeBytes);

        // Read payload
        byte[] payload = new byte[(int) payloadLength];
        for (int pos = 0; pos < payloadLength; pos++) {
            payload[pos] = (byte) is.read();
        }

        return new PtpIpPacket(type, payload);
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
