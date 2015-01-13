package com.theta360.ptp;

import com.theta360.ptp.code.OperationCode;
import com.theta360.ptp.data.*;
import com.theta360.ptp.io.PacketInputStream;
import com.theta360.ptp.io.PacketOutputStream;
import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.packet.*;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    /**
     * Get the host of responder which the initiator is connecting.
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the TCP port of responder which the initiator is connecting.
     */
    public int getPort() {
        return port;
    }

    // Operations

    /**
     * Get the DeviceInfo of the responder.
     *
     * @throws IOException
     */
    public DeviceInfo getDeviceInfo() throws IOException {
        // Send OperationRequest (GetDeviceInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_DEVICE_INFO.value(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDeviceInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        DeviceInfo deviceInfo = DeviceInfo.valueOf(data);
        LOGGER.info("Received DeviceInfo: " + deviceInfo);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return deviceInfo;
    }

    /**
     * Open the session.
     *
     * @param sessionID
     * @throws IOException
     */
    public void openSession(UINT32 sessionID) throws IOException {
        Validators.validateNonNull("sessionID", sessionID);

        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        // Send OperationRequest (OpenSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.OPEN_SESSION.value(),
                transactionID.next(),
                sessionID
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (OpenSession): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    /**
     * Close the session.
     *
     * @throws IOException
     */
    public void closeSession() throws IOException {
        // Send OperationRequest (CloseSession)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.CLOSE_SESSION.value(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (CloseSession): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    /**
     * Get list of storage ID
     *
     * @throws IOException
     */
    public List<UINT32> getStorageIDs() throws IOException {
        // Send OperationRequest (GetStorageIDs)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_STORAGE_IDS.value(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetStorageIDs): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        List<UINT32> storageIDs;
        try (PtpInputStream pis = new PtpInputStream(data)) {
            storageIDs = pis.readAUINT32();
            LOGGER.info("Received Storage IDs: " + storageIDs);
        }

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return storageIDs;
    }

    /**
     * Get storage info
     *
     * @param storageID
     * @throws IOException
     */
    public StorageInfo getStorageInfo(UINT32 storageID) throws IOException {
        Validators.validateNonNull("storageID", storageID);

        // Send OperationRequest (GetStorageInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_STORAGE_INFO.value(),
                transactionID.next(),
                storageID
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetStorageInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        StorageInfo storageInfo = StorageInfo.valueOf(data);
        LOGGER.info("Received Storage Info: " + storageInfo);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return storageInfo;
    }

    /**
     * Get number of objects.
     *
     * @throws IOException
     */
    public UINT32 getNumObjects() throws IOException {
        // Send OperationRequest (GetNumObjects)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_NUM_OBJECTS.value(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetNumObjects): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        UINT32 numObjects;
        try (PtpInputStream pis = new PtpInputStream(data)) {
            numObjects = pis.readUINT32();
            LOGGER.info("Received Num Objects: " + numObjects);
        }

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return numObjects;
    }

    /**
     * Get list of object handle.
     *
     * @throws IOException
     */
    public List<UINT32> getObjectHandles() throws IOException {
        return getObjectHandles(new UINT32(0xFFFFFFFFL));
    }

    /**
     * Get list of object handle.
     *
     * @param storageID
     * @throws IOException
     */
    public List<UINT32> getObjectHandles(UINT32 storageID) throws IOException {
        Validators.validateNonNull("storageID", storageID);

        // Send OperationRequest (GetDeviceInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_OBJECT_HANDLES.value(),
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

    /**
     * Get the information of the object.
     *
     * @param objectHandle
     * @throws IOException
     */
    public ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);

        // Send OperationRequest (GetObjectInfo)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_OBJECT_INFO.value(),
                transactionID.next(),
                objectHandle
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetObjectInfo): " + operationRequest);

        // Receive Data
        byte[] data = ci.readData();
        ObjectInfo objectInfo = ObjectInfo.valueOf(data);
        LOGGER.info("Received Object Info: " + objectInfo);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return objectInfo;
    }

    /**
     * Get the object from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetObject)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_OBJECT.value(),
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

    /**
     * Get the thumbnail of specified object handle from the responder.
     *
     * @param objectHandle
     * @param dst
     * @throws IOException
     */
    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetThumb)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_THUMB.value(),
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

    /**
     * Send initiate capture request to the responder.
     *
     * @throws IOException
     */
    public void initiateCapture() throws IOException {
        // Send OperationRequest (InitiateCapture)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.INITIATE_CAPTURE.value(),
                transactionID.next()
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (InitiateCapture): " + operationRequest);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    // Property Getter

    public byte[] getDevicePropValue(UINT16 devicePropCode) throws IOException {
        // Send OperationRequest (GetDevicePropValue)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.GET_DEVICE_PROP_VALUE.value(),
                transactionID.next(),
                new UINT32(devicePropCode.intValue())
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDevicePropValue): " + operationRequest);

        byte[] value = ci.readData();

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);

        return value;
    }

    public byte getDevicePropValueAsUINT8(UINT16 devicePropCode) throws IOException {
        return getDevicePropValue(devicePropCode)[0];
    }

    public UINT16 getDevicePropValueAsUINT16(UINT16 devicePropCode) throws IOException {
        byte[] data = getDevicePropValue(devicePropCode);

        try (InputStream is = new ByteArrayInputStream(data)) {
            return UINT16.read(is);
        }
    }

    public UINT32 getDevicePropValueAsUINT32(UINT16 devicePropCode) throws IOException {
        byte[] data = getDevicePropValue(devicePropCode);

        try (InputStream is = new ByteArrayInputStream(data)) {
            return UINT32.read(is);
        }
    }

    // Property Setter

    public void setDevicePropValue(UINT16 devicePropCode, byte[] value) throws IOException {
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                OperationCode.SET_DEVICE_PROP_VALUE.value(),
                transactionID.next(),
                new UINT32(devicePropCode.intValue())
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetDevicePropValue): " + operationRequest);

        co.writeData(transactionID, value);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }

    public void setDevicePropValue(UINT16 devicePropValue, byte value) throws IOException {
        setDevicePropValue(devicePropValue, new byte[]{value});
    }

    public void setDevicePropValue(UINT16 devicePropValue, UINT16 value) throws IOException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    public void setDevicePropValue(UINT16 devicePropValue, UINT32 value) throws IOException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    // Listener

    /**
     * Add the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator did not already contain the specified listener
     */
    public boolean addListener(PtpEventListener listener) {
        return listenerSet.add(listener);
    }

    /**
     * Remove the listener for PTP event.
     *
     * @param listener
     * @return true if this initiator contained the specified listener
     */
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
