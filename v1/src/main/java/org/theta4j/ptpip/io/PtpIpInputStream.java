/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.io;

import org.theta4j.ptp.PtpException;
import org.theta4j.ptp.code.ResponseCode;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptpip.packet.*;

import java.io.*;

import static org.theta4j.ptpip.packet.PtpIpPacket.Type.*;

/**
 * InputStream of PTP-IP.
 */
public final class PtpIpInputStream implements Closeable {
    private final PtpInputStream pis;

    /**
     * Wrap InputStream by PtpIpInputStream.
     *
     * @param is
     */
    public PtpIpInputStream(InputStream is) {
        this.pis = new PtpInputStream(is);
    }

    // Check Next

    /**
     * Get type of next PTP-IP Packet.
     * This method does not change the position of stream.
     *
     * @throws IOException
     */
    public PtpIpPacket.Type nextType() throws IOException {
        pis.mark(UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES);

        // Skip Length Value on Header
        long sizeToSkip = UINT32.SIZE_IN_BYTES;
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
        assertNextTypeIs(INIT_COMMAND_REQUEST);

        return InitCommandRequestPacket.read(pis);
    }

    /**
     * Read InitCommandAckPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitCommandAckPacket.
     * @throws IOException
     */
    public InitCommandAckPacket readInitCommandAckPacket() throws IOException {
        assertNextTypeIs(INIT_COMMAND_ACK);

        return InitCommandAckPacket.read(pis);
    }

    /**
     * Read InitEventRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitEventRequestPacket.
     * @throws IOException
     */
    public InitEventRequestPacket readInitEventRequestPacket() throws IOException {
        assertNextTypeIs(INIT_EVENT_REQUEST);

        return InitEventRequestPacket.read(pis);
    }

    /**
     * Read InitEventAckPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitEventAckPacket.
     * @throws IOException
     */
    public InitEventAckPacket readInitEventAckPacket() throws IOException {
        assertNextTypeIs(INIT_EVENT_ACK);

        return InitEventAckPacket.read(pis);
    }

    /**
     * Read InitFailPacket from the stream.
     *
     * @throws RuntimeException if next packet is not InitFailPacket.
     * @throws IOException
     */
    public InitFailPacket readInitFailPacket() throws IOException {
        assertNextTypeIs(INIT_FAIL);

        return InitFailPacket.read(pis);
    }

    /**
     * Read OperationRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not OperationRequestPacket.
     * @throws IOException
     */
    public OperationRequestPacket readOperationRequestPacket() throws IOException {
        assertNextTypeIs(OPERATION_REQUEST);

        return OperationRequestPacket.read(pis);
    }

    /**
     * Read OperationResponsePacket from the stream.
     *
     * @throws RuntimeException if next packet is not OperationResponsePacket.
     * @throws IOException
     */
    public OperationResponsePacket readOperationResponsePacket() throws IOException {
        assertNextTypeIs(OPERATION_RESPONSE);

        return OperationResponsePacket.read(pis);
    }

    /**
     * Read EventPacket from the stream.
     *
     * @throws RuntimeException if next packet is not EventPacket.
     * @throws IOException
     */
    public EventPacket readEventPacket() throws IOException {
        assertNextTypeIs(EVENT);

        return EventPacket.read(pis);
    }

    /**
     * Read StartDataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not StartDataPacket.
     * @throws IOException
     */
    public StartDataPacket readStartDataPacket() throws IOException {
        assertNextTypeIs(START_DATA);

        return StartDataPacket.read(pis);
    }

    /**
     * Read DataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not DataPacket.
     * @throws IOException
     */
    public DataPacket readDataPacket() throws IOException {
        assertNextTypeIs(DATA);

        return DataPacket.read(pis);
    }

    /**
     * Read EndDataPacket from the stream.
     *
     * @throws RuntimeException if next packet is not EndDataPacket.
     * @throws IOException
     */
    public EndDataPacket readEndDataPacket() throws IOException {
        assertNextTypeIs(END_DATA);

        return EndDataPacket.read(pis);
    }

    /**
     * Read CancelPacket from the stream.
     *
     * @throws RuntimeException if next packet is not CancelPacket.
     * @throws IOException
     */
    public CancelPacket readCancelPacket() throws IOException {
        assertNextTypeIs(CANCEL);

        return CancelPacket.read(pis);
    }

    /**
     * Read ProbeRequestPacket from the stream.
     *
     * @throws RuntimeException if next packet is not ProbeRequestPacket.
     * @throws IOException
     */
    public ProbeRequestPacket readProbeRequestPacket() throws IOException {
        assertNextTypeIs(PROBE_REQUEST);

        return ProbeRequestPacket.read(pis);
    }

    /**
     * Read ProbeResponsePacket from the stream.
     *
     * @throws RuntimeException if next packet is not ProbeResponsePacket.
     * @throws IOException
     */
    public ProbeResponsePacket readProbeResponsePacket() throws IOException {
        assertNextTypeIs(PROBE_RESPONSE);

        return ProbeResponsePacket.read(pis);
    }

    // Read Data

    /**
     * Process Data Phase (StartData -&gt; [Data] -&gt; EndData) and returns all data as byte array.
     *
     * @throws IOException
     */
    public byte[] readData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        readData(baos);
        return baos.toByteArray();
    }

    /**
     * Process Data Phase (StartData -&gt; [Data] -&gt; EndData) and writes all data to dst.
     *
     * @param dst
     * @throws RuntimeException if it is not Data Phase.
     * @throws IOException
     */
    public void readData(OutputStream dst) throws IOException {
        if (nextType() == OPERATION_RESPONSE) {
            OperationResponsePacket response = readOperationResponsePacket();

            if (response.getResponseCode().equals(ResponseCode.OK.value())) {
                throw new IOException("Expected StartData but was OperationResponse(OK)");
            } else {
                throw new PtpException(response.getResponseCode());
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

        if (expected == PROBE_REQUEST || expected == PROBE_RESPONSE) {
            if (actual != PROBE_REQUEST && actual != PROBE_RESPONSE) {
                throw new RuntimeException(String.format("Expected %s but was %s", expected, actual));
            }

            return;
        }

        if (actual != expected) {
            throw new RuntimeException(String.format("Expected %s but was %s", expected, actual));
        }
    }
}
