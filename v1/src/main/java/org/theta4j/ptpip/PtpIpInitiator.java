/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.theta4j.ptp.AbstractPtpInitiator;
import org.theta4j.ptp.TransactionIDIterator;
import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.data.Event;
import org.theta4j.ptp.data.Response;
import org.theta4j.ptp.type.UINT16;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.ptpip.io.PtpIpInputStream;
import org.theta4j.ptpip.io.PtpIpOutputStream;
import org.theta4j.ptpip.packet.*;
import org.theta4j.util.Validators;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * PTP Initiator implementation according to PTP-IP standard.
 */
public final class PtpIpInitiator extends AbstractPtpInitiator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpIpInitiator.class);

    // Property

    private final UUID guid;
    private final String host;
    private final int port;

    // State

    private volatile boolean isClosed = false;
    private final TransactionIDIterator transactionIDIterator = new TransactionIDIterator();

    // Command Data Connection

    private final Socket commandDataConnection;
    private final PtpIpInputStream ci;
    private final PtpIpOutputStream co;

    // Event Connection

    private final Socket eventConnection;
    private final PtpIpInputStream ei;
    private final PtpIpOutputStream eo;

    // Connect

    public PtpIpInitiator(UUID guid, String host, int port) throws IOException {
        Validators.notNull("guid", guid);
        Validators.notNull("host", host);
        Validators.portNumber(port);

        this.guid = guid;
        this.host = host;
        this.port = port;

        // Establish Command Data Connection
        this.commandDataConnection = new Socket(host, port);
        this.ci = new PtpIpInputStream(commandDataConnection.getInputStream());
        this.co = new PtpIpOutputStream(commandDataConnection.getOutputStream());
        UINT32 connectionNumber = establishCommandDataConnection();

        // Establish Event Connection
        this.eventConnection = new Socket(host, port);
        this.ei = new PtpIpInputStream(eventConnection.getInputStream());
        this.eo = new PtpIpOutputStream(eventConnection.getOutputStream());
        establishEventConnection(connectionNumber);

        startEventHandlerThread();
    }

    private UINT32 establishCommandDataConnection() throws IOException {
        InitCommandRequestPacket initCommandRequest = new InitCommandRequestPacket(guid, "test", ProtocolVersions.REV_1_0);
        co.write(initCommandRequest);
        LOGGER.debug("Sent InitCommandRequest: " + initCommandRequest);

        InitCommandAckPacket initCommandAck = ci.readInitCommandAckPacket();
        LOGGER.debug("Command Data Connection is established: " + initCommandAck);

        return initCommandAck.getConnectionNumber();
    }

    private void establishEventConnection(UINT32 connectionNumber) throws IOException {
        InitEventRequestPacket initEventRequest = new InitEventRequestPacket(connectionNumber);
        eo.write(initEventRequest);
        LOGGER.debug("Sent InitEventRequest: " + initEventRequest);

        InitEventAckPacket initEventAck = ei.readInitEventAckPacket();
        LOGGER.debug("Event Connection is established: " + initEventAck);
    }

    private void startEventHandlerThread() {
        new Thread(() -> {
            for (; ; ) {
                final EventPacket eventPacket;
                try {
                    eventPacket = ei.readEventPacket();
                } catch (final IOException e) {
                    if (isClosed) {
                        LOGGER.debug("Finished Event Listener Thread.");
                        return;
                    }

                    try {
                        LOGGER.error("Error occurred while receiving Event packet: " + e);
                        LOGGER.error("Try to close PtpIpInitiator");
                        close();
                    } catch (IOException e1) {
                        LOGGER.error("Error occurred while closing in event receiving thread: " + e1);
                    }

                    return;
                }

                listenerSet.onEvent(new Event(
                        eventPacket.getEventCode(),
                        getSessionID(),
                        eventPacket.getTransactionID(),
                        eventPacket.getP1(),
                        eventPacket.getP2(),
                        eventPacket.getP3()
                ));
            }
        }).start();
    }

    // Getter

    /**
     * Returns the GUID of current session.
     */
    public UUID getGUID() {
        return guid;
    }

    /**
     * Returns the host of responder which the initiator is connecting of current session.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the TCP port of responder which the initiator is connecting of current session.
     */
    public int getPort() {
        return port;
    }

    // AbstractPtpInitiator

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4, UINT32 p5) throws IOException {
        Validators.notNull("operationCode", operationCode);
        Validators.notNull("p1", p1);
        Validators.notNull("p2", p2);
        Validators.notNull("p3", p3);
        Validators.notNull("p4", p4);
        Validators.notNull("p5", p5);

        UINT32 transactionID = transactionIDIterator.next();

        OperationRequestPacket operationRequestPacket = new OperationRequestPacket(
                new UINT32(1),
                operationCode.value(),
                transactionID,
                p1, p2, p3, p4, p5
        );
        co.write(operationRequestPacket);
        LOGGER.debug("Sent OperationRequest: " + operationRequestPacket);

        return transactionID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response receiveResponse() throws IOException {
        if (ci.nextType() != PtpIpPacket.Type.OPERATION_RESPONSE) {
            throw new IllegalStateException("Expected OperationResponse but was " + ci.nextType());
        }

        OperationResponsePacket operationResponsePacket = ci.readOperationResponsePacket();

        return new Response(
                operationResponsePacket.getResponseCode(),
                getSessionID(),
                operationResponsePacket.getTransactionID(),
                operationResponsePacket.getP1(),
                operationResponsePacket.getP2(),
                operationResponsePacket.getP3(),
                operationResponsePacket.getP4(),
                operationResponsePacket.getP5()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(byte[] data) throws IOException {
        Validators.notNull("data", data);

        co.writeData(transactionIDIterator.next(), data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveData(OutputStream dst) throws IOException {
        Validators.notNull("dst", dst);

        ci.readData(dst);
    }

    // Closeable

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        isClosed = true;
        listenerSet.clear();

        // (1) Close Event Connection
        if (eventConnection != null) {
            eventConnection.close();
        }

        // (2) Close Command Data Connection
        if (commandDataConnection != null) {
            commandDataConnection.close();
        }
    }
}
