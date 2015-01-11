package com.theta360.ptp;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.code.OperationCode;
import com.theta360.ptp.code.PropertyCode;
import com.theta360.ptp.data.DeviceInfo;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.data.ProtocolVersions;
import com.theta360.ptp.data.TransactionID;
import com.theta360.ptp.io.PacketInputStream;
import com.theta360.ptp.io.PacketOutputStream;
import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.packet.*;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This test requires manual execution.
 * Execute after connection with RICOH THETA is established.
 */
public class PtpInitiator implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PtpInitiator.class);

    // Property

    private final GUID guid;
    private final String host;
    private final int port;

    // State

    private volatile boolean isClosed = false;
    protected TransactionID transactionID = new TransactionID();

    // EventListener

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final PtpEventListenerSet listenerSet = new PtpEventListenerSet();

    // Command Data Connection

    private Socket commandDataConnection;
    protected PacketInputStream ci;
    protected PacketOutputStream co;

    // Event Connection

    private Socket eventConnection;
    private PacketInputStream ei;

    // Connect

    public PtpInitiator(GUID guid, String host, int port) throws IOException {
        Validators.validateNonNull("guid", guid);
        Validators.validateNonNull("host", host);

        this.guid = guid;
        this.host = host;
        this.port = port;

        UINT32 connectionNumber = establishCommandDataConnection();
        establishEventConnection(connectionNumber);
        startEventHandlerThread();
    }

    private UINT32 establishCommandDataConnection() throws IOException {
        commandDataConnection = new Socket(host, port);
        ci = new PacketInputStream(commandDataConnection.getInputStream());
        co = new PacketOutputStream(commandDataConnection.getOutputStream());

        InitCommandRequestPacket initCommandRequest = new InitCommandRequestPacket(guid, "test", ProtocolVersions.REV_1_0);
        co.write(initCommandRequest);
        LOGGER.info("Sent InitCommandRequest: " + initCommandRequest);

        InitCommandAckPacket initCommandAck = ci.readInitCommandAckPacket();
        LOGGER.info("Command Data Connection is established: " + initCommandAck);

        return initCommandAck.getConnectionNumber();
    }

    private void establishEventConnection(UINT32 connectionNumber) throws IOException {
        eventConnection = new Socket(host, port);
        ei = new PacketInputStream(eventConnection.getInputStream());
        PacketOutputStream eo = new PacketOutputStream(eventConnection.getOutputStream());

        InitEventRequestPacket initEventRequest = new InitEventRequestPacket(connectionNumber);
        eo.write(initEventRequest);
        LOGGER.info("Sent InitEventRequest: " + initEventRequest);

        InitEventAckPacket initEventAck = ei.readInitEventAckPacket();
        LOGGER.info("Event Connection is established: " + initEventAck);
    }

    private void startEventHandlerThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    final EventPacket eventPacket;
                    try {
                        eventPacket = ei.readEventPacket();
                    } catch (final IOException e) {
                        if (isClosed) {
                            LOGGER.info("Finished Event Listener Thread.");
                            return;
                        }

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                listenerSet.onError(e);
                            }
                        });
                        continue;
                    }

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            listenerSet.onPacket(eventPacket);
                        }
                    });
                }
            }
        }).start();
    }

    // Getter

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    // Operations

    public DeviceInfo getDeviceInfo() throws IOException {
        // Send OperationRequest (GetDeviceInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_DEVICE_INFO.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDeviceInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        DeviceInfo deviceInfo;
        deviceInfo = DeviceInfo.valueOf(data);
        LOGGER.info("Received DeviceInfo: " + deviceInfo);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return deviceInfo;
    }

    public void openSession(UINT32 sessionID) throws IOException {
        Validators.validateNonNull("sessionID", sessionID);

        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        // Send OperationRequest (OpenSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.OPEN_SESSION.getCode(),
                transactionID.next(),
                sessionID
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (OpenSession): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void closeSession() throws IOException {
        // Send OperationRequest (CloseSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.CLOSE_SESSION.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (CloseSession): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public List<UINT32> getObjectHandles() throws IOException {
        return getObjectHandles(new UINT32(0xFFFFFFFFL));
    }

    public List<UINT32> getObjectHandles(UINT32 storageID) throws IOException {
        Validators.validateNonNull("storageID", storageID);

        // Send OperationRequest (GetDeviceInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_OBJECT_HANDLES.getCode(),
                transactionID.next(),
                storageID
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDeviceInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        List<UINT32> objectHandles;
        try (PtpInputStream is = new PtpInputStream(data)) {
            objectHandles = is.readAUINT32();
            LOGGER.info("Received Object Handles: " + objectHandles);
        }

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return objectHandles;
    }

    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetObject)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_OBJECT.getCode(),
                transactionID.next(),
                objectHandle
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetObject): " + operationRequest);

        // Receive Data
        ci.readData(dst);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetThumb)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_THUMB.getCode(),
                transactionID.next(),
                objectHandle
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetThumb): " + operationRequest);

        // Receive Data
        ci.readData(dst);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void initiateCapture() throws IOException {
        // Send OperationRequest (InitiateCapture)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.INITIATE_CAPTURE.getCode(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (InitiateCapture): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    // Properties

    private void sendGetDevicePropValue(UINT16 devicePropCode) throws IOException {
        // Send OperationRequest (InitiateCapture)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_DEVICE_PROP_VALUE.getCode(),
                transactionID.next(),
                new UINT32(devicePropCode.intValue())
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDevicePropValue): " + operationRequest);
    }

    public int getBatteryLevel() throws IOException {
        sendGetDevicePropValue(PropertyCode.BATTERY_LEVEL.getCode());

        byte[] data = ci.readData();

        return data[0];
    }

    // Listener

    public boolean addListener(PtpEventListener listener) {
        return listenerSet.add(listener);
    }

    public boolean removeListener(PtpEventListener listener) {
        return listenerSet.remove(listener);
    }

    // Closeable

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

        // (3) Shutdown Listener Executor
        executor.shutdown();
    }
}
