package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.code.OperationCode;
import org.theta4j.ptp.code.ResponseCode;
import org.theta4j.ptp.data.*;
import org.theta4j.ptp.type.*;
import org.theta4j.util.Validators;

import java.io.*;
import java.util.List;

public abstract class AbstractPtpInitiator implements PtpInitiator {
    // Session ID

    private UINT32 sessionID = UINT32.ZERO;

    protected UINT32 getSessionID() {
        return sessionID;
    }

    // Operations (Base)

    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode) throws IOException {
        return sendOperation(operationCode, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1) throws IOException {
        return sendOperation(operationCode, p1, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2) throws IOException {
        return sendOperation(operationCode, p1, p2, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException {
        return sendOperation(operationCode, p1, p2, p3, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException {
        return sendOperation(operationCode, p1, p2, p3, p4, UINT32.ZERO);
    }

    // Operations

    @Override
    public DeviceInfo getDeviceInfo() throws IOException {
        sendOperation(OperationCode.GET_DEVICE_INFO);
        DeviceInfo deviceInfo = DeviceInfo.read(receiveData());
        checkResponse();

        return deviceInfo;
    }

    @Override
    public void openSession(UINT32 sessionID) throws IOException {
        Validators.notNull("sessionID", sessionID);

        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        sendOperation(OperationCode.OPEN_SESSION, sessionID);
        checkResponse();

        this.sessionID = sessionID;
    }

    @Override
    public void closeSession() throws IOException {
        sendOperation(OperationCode.CLOSE_SESSION);
        checkResponse();

        this.sessionID = UINT32.ZERO;
    }

    @Override
    public List<UINT32> getStorageIDs() throws IOException {
        sendOperation(OperationCode.GET_STORAGE_IDS);
        List<UINT32> storageIDs = AUINT32.read(receiveData());
        checkResponse();

        return storageIDs;
    }

    @Override
    public StorageInfo getStorageInfo(UINT32 storageID) throws IOException {
        Validators.notNull("storageID", storageID);

        sendOperation(OperationCode.GET_STORAGE_INFO, storageID);
        StorageInfo storageInfo = StorageInfo.read(receiveData());
        checkResponse();

        return storageInfo;
    }

    @Override
    public UINT32 getNumObjects() throws IOException {
        sendOperation(OperationCode.GET_NUM_OBJECTS);
        UINT32 numObjects = UINT32.read(receiveData());
        checkResponse();

        return numObjects;
    }

    @Override
    public List<UINT32> getObjectHandles() throws IOException {
        return getObjectHandles(new UINT32(0xFFFFFFFFL));
    }

    @Override
    public List<UINT32> getObjectHandles(UINT32 storageID) throws IOException {
        Validators.notNull("storageID", storageID);

        sendOperation(OperationCode.GET_OBJECT_HANDLES, storageID);
        List<UINT32> objectHandles = AUINT32.read(receiveData());
        checkResponse();

        return objectHandles;
    }

    @Override
    public ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException {
        Validators.notNull("objectHandle", objectHandle);

        sendOperation(OperationCode.GET_OBJECT_INFO, objectHandle);
        ObjectInfo objectInfo = ObjectInfo.read(receiveData());
        checkResponse();

        return objectInfo;
    }

    @Override
    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.notNull("objectHandle", objectHandle);
        Validators.notNull("dst", dst);

        sendOperation(OperationCode.GET_OBJECT, objectHandle);
        receiveData(dst);
        checkResponse();
    }

    @Override
    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.notNull("objectHandle", objectHandle);
        Validators.notNull("dst", dst);

        sendOperation(OperationCode.GET_THUMB, objectHandle);
        receiveData(dst);
        checkResponse();
    }

    @Override
    public void deleteObject(UINT32 objectHandle) throws IOException {
        Validators.notNull("objectHandle", objectHandle);

        sendOperation(OperationCode.DELETE_OBJECT, objectHandle);
        checkResponse();
    }

    @Override
    public void initiateCapture() throws IOException {
        sendOperation(OperationCode.INITIATE_CAPTURE);
        checkResponse();
    }

    @Override
    public DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException {
        sendOperation(OperationCode.GET_DEVICE_PROP_DESC, new UINT32(devicePropCode.value().intValue()));
        DevicePropDesc<?> devicePropDesc = DevicePropDesc.read(receiveData());
        checkResponse();

        return devicePropDesc;
    }

    @Override
    public InputStream getDevicePropValue(Code<UINT16> devicePropCode) throws IOException {
        Validators.notNull("devicePropCode", devicePropCode);

        sendOperation(OperationCode.GET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        InputStream is = receiveData();
        checkResponse();

        return is;
    }

    @Override
    public UINT8 getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException {
        return UINT8.read(getDevicePropValue(devicePropCode));
    }

    @Override
    public UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException {
        return UINT16.read(getDevicePropValue(devicePropCode));
    }

    @Override
    public UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException {
        return UINT32.read(getDevicePropValue(devicePropCode));
    }

    @Override
    public UINT64 getDevicePropValueAsUINT64(Code<UINT16> devicePropCode) throws IOException {
        return UINT64.read(getDevicePropValue(devicePropCode));
    }

    @Override
    public String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException {
        return STR.read(getDevicePropValue(devicePropCode));
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException {
        Validators.notNull("devicePropCode", devicePropCode);
        Validators.notNull("value", value);

        sendOperation(OperationCode.SET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        sendData(value);
        checkResponse();
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, byte value) throws IOException {
        setDevicePropValue(devicePropValue, new byte[]{value});
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, UINT16 value) throws IOException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, UINT32 value) throws IOException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, String value) throws IOException {
        setDevicePropValue(devicePropValue, STR.toBytes(value + "\u0000"));
    }

    @Override
    public void terminateOpenCapture() throws IOException {
        terminateOpenCapture(UINT32.MAX_VALUE);
    }

    @Override
    public void terminateOpenCapture(UINT32 transactionID) throws IOException {
        sendOperation(OperationCode.TERMINATE_OPEN_CAPTURE, transactionID);
        checkResponse();
    }

    @Override
    public UINT32 initiateOpenCapture() throws IOException {
        UINT32 transactionID = sendOperation(OperationCode.INITIATE_OPEN_CAPTURER);
        checkResponse();

        return transactionID;
    }

    // Responses

    @Override
    public void checkResponse() throws IOException {
        Response operationResponse = receiveResponse();

        if (!operationResponse.getResponseCode().equals(ResponseCode.OK.value())) {
            String message = "ResponseCode was not OK: " + operationResponse.getResponseCode();
            throw new PtpException(operationResponse.getResponseCode(), message);
        }
    }

    // Data

    @Override
    public InputStream receiveData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        receiveData(baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
