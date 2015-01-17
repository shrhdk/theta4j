package com.theta360.ptp.io;

import com.theta360.ptp.PtpException;
import com.theta360.ptp.code.ResponseCode;
import com.theta360.ptp.type.UINT32;
import com.theta360.ptpip.packet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * InputStream of PTP-IP Packet.
 */
public final class PacketInputStream implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketInputStream.class);

    private final PtpInputStream pis;

    /**
     * Wrap InputStream by PtpIpInputStream.
     *
     * @param is
     */
    public PacketInputStream(InputStream is) {
        this.pis = new PtpInputStream(is);
    }

    // Check Next

    /**
     * Get length of next PTP-IP Packet.
     * This method does not change the position of stream.
     *
     * @throws IOException
     */
    public long nextLength() throws IOException {
        pis.mark(UINT32.SIZE);
        UINT32 length = pis.readUINT32();
        pis.reset();

        return length.longValue();
    }

    /**
     * Get type of next PTP-IP Packet.
     * This method does not change the position of stream.
     *
     * @throws IOException
     */
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

    /**
     * Read InitCommandRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitCommandRequestPacket.
     * @throws IOException
     */
    public InitCommandRequestPacket readInitCommandRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_COMMAND_REQUEST);

        return InitCommandRequestPacket.read(pis);
    }

    /**
     * Read InitCommandAckPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitCommandAckPacket.
     * @throws IOException
     */
    public InitCommandAckPacket readInitCommandAckPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_COMMAND_ACK);

        return InitCommandAckPacket.read(pis);
    }

    /**
     * Read InitEventRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitEventRequestPacket.
     * @throws IOException
     */
    public InitEventRequestPacket readInitEventRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_EVENT_REQUEST);

        return InitEventRequestPacket.read(pis);
    }

    /**
     * Read InitEventAckPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitEventAckPacket.
     * @throws IOException
     */
    public InitEventAckPacket readInitEventAckPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_EVENT_ACK);

        return InitEventAckPacket.read(pis);
    }

    /**
     * Read InitFailPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitFailPacket.
     * @throws IOException
     */
    public InitFailPacket readInitFailPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.INIT_FAIL);

        return InitFailPacket.read(pis);
    }

    /**
     * Read OperationRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not OperationRequestPacket.
     * @throws IOException
     */
    public OperationRequestPacket readOperationRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.OPERATION_REQUEST);

        return OperationRequestPacket.read(pis);
    }

    /**
     * Read OperationResponsePacket from the stream.
     *
     * @throws RuntimeException if next packet is not OperationResponsePacket.
     * @throws IOException
     */
    public OperationResponsePacket readOperationResponsePacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.OPERATION_RESPONSE);

        return OperationResponsePacket.read(pis);
    }

    /**
     * Read EventPacket from the stream.
     *
     * @throws RuntimeException if next packet is not EventPacket.
     * @throws IOException
     */
    public EventPacket readEventPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.EVENT);

        return EventPacket.read(pis);
    }

    /**
     * Read StartDataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not StartDataPacket.
     * @throws IOException
     */
    public StartDataPacket readStartDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.START_DATA);

        return StartDataPacket.read(pis);
    }

    /**
     * Read DataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not DataPacket.
     * @throws IOException
     */
    public DataPacket readDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.DATA);

        return DataPacket.read(pis);
    }

    /**
     * Read EndDataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not EndDataPacket.
     * @throws IOException
     */
    public EndDataPacket readEndDataPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.END_DATA);

        return EndDataPacket.read(pis);
    }

    /**
     * Read CancelPacket from the stream.
     *
     * @throws RuntimeException if next packet is not CancelPacket.
     * @throws IOException
     */
    public CancelPacket readCancelPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.CANCEL);

        return CancelPacket.read(pis);
    }

    /**
     * Read ProbeRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not ProbeRequestPacket.
     * @throws IOException
     */
    public ProbeRequestPacket readProbeRequestPacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.PROBE_REQUEST);

        return ProbeRequestPacket.read(pis);
    }

    /**
     * Read ProbeResponsePacket from the stream.
     *
     * @throws RuntimeException if next packet is not ProbeResponsePacket.
     * @throws IOException
     */
    public ProbeResponsePacket readProbeResponsePacket() throws IOException {
        assertNextTypeIs(PtpIpPacket.Type.PROBE_RESPONSE);

        return ProbeResponsePacket.read(pis);
    }

    // Read Data

    /**
     * Process Data Phase (StartData -> [Data] -> EndData) and returns all data as byte array.
     *
     * @throws IOException
     */
    public byte[] readData() throws IOException, PtpException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        readData(baos);
        return baos.toByteArray();
    }

    /**
     * Process Data Phase (StartData -> [Data] -> EndData) and writes all data to dst.
     *
     * @param dst
     * @throws RuntimeException if it is not Data Phase.
     * @throws IOException
     */
    public void readData(OutputStream dst) throws IOException, PtpException {
        if (nextType() == PtpIpPacket.Type.OPERATION_RESPONSE) {
            OperationResponsePacket response = readOperationResponsePacket();

            if (response.getResponseCode().equals(ResponseCode.OK.value())) {
                throw new RuntimeException("Expected StartData but was OperationResponse(OK)");
            } else {
                throw new PtpException(response.getResponseCode().intValue());
            }
        }

        readStartDataPacket();

        for (; ; ) {
            switch (nextType()) {
                case DATA:
                    dst.write(readDataPacket().getDataPayload());
                    break;
                case END_DATA:
                    dst.write(readEndDataPacket().getDataPayload());
                    return;
                default:
                    throw new IOException("Expected Data or EndData but was " + nextType());
            }
        }
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
            throw new RuntimeException(String.format("Expected %s but was %s", expected, actual));
        }
    }
}
