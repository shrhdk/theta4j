package com.theta360.ptp;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.code.OperationCode;
import com.theta360.ptp.data.DeviceInfo;
import com.theta360.ptp.data.DevicePropDesc;
import com.theta360.ptp.data.ObjectInfo;
import com.theta360.ptp.data.StorageInfo;
import com.theta360.ptp.type.AUINT32;
import com.theta360.ptp.type.STR;
import com.theta360.ptp.type.UINT16;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class AbstractPtp implements Ptp {
    // Operations (Base)

    @Override
    public UINT32 sendOperationRequest(Code<UINT16> code) throws IOException {
        return sendOperationRequest(code, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1) throws IOException {
        return sendOperationRequest(code, p1, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1, UINT32 p2) throws IOException {
        return sendOperationRequest(code, p1, p2, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException {
        return sendOperationRequest(code, p1, p2, p3, UINT32.ZERO);
    }

    @Override
    public UINT32 sendOperationRequest(Code<UINT16> code, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException {
        return sendOperationRequest(code, p1, p2, p3, p4, UINT32.ZERO);
    }

    // Operations

    @Override
    public DeviceInfo getDeviceInfo() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_DEVICE_INFO);
        DeviceInfo deviceInfo = DeviceInfo.valueOf(receiveData());
        receiveOperationResponse();

        return deviceInfo;
    }

    @Override
    public void openSession(UINT32 sessionID) throws IOException, PtpException {
        Validators.validateNonNull("sessionID", sessionID);

        if (sessionID.longValue() == 0) {
            throw new IllegalArgumentException("sessionID must be non-zero.");
        }

        sendOperationRequest(OperationCode.OPEN_SESSION, sessionID);
        receiveOperationResponse();
    }

    @Override
    public void closeSession() throws IOException, PtpException {
        sendOperationRequest(OperationCode.CLOSE_SESSION);
        receiveOperationResponse();
    }

    @Override
    public List<UINT32> getStorageIDs() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_STORAGE_IDS);
        List<UINT32> storageIDs = AUINT32.valueOf(receiveData());
        receiveOperationResponse();

        return storageIDs;
    }

    @Override
    public StorageInfo getStorageInfo(UINT32 storageID) throws IOException, PtpException {
        Validators.validateNonNull("storageID", storageID);

        sendOperationRequest(OperationCode.GET_STORAGE_INFO, storageID);
        StorageInfo storageInfo = StorageInfo.valueOf(receiveData());
        receiveOperationResponse();

        return storageInfo;
    }

    @Override
    public UINT32 getNumObjects() throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_NUM_OBJECTS);
        UINT32 numObjects = UINT32.valueOf(receiveData());
        receiveOperationResponse();

        return numObjects;
    }

    @Override
    public List<UINT32> getObjectHandles() throws IOException, PtpException {
        return getObjectHandles(new UINT32(0xFFFFFFFFL));
    }

    @Override
    public List<UINT32> getObjectHandles(UINT32 storageID) throws IOException, PtpException {
        Validators.validateNonNull("storageID", storageID);

        sendOperationRequest(OperationCode.GET_OBJECT_HANDLES, storageID);
        List<UINT32> objectHandles = AUINT32.valueOf(receiveData());
        receiveOperationResponse();

        return objectHandles;
    }

    @Override
    public ObjectInfo getObjectInfo(UINT32 objectHandle) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);

        sendOperationRequest(OperationCode.GET_OBJECT_INFO, objectHandle);
        ObjectInfo objectInfo = ObjectInfo.valueOf(receiveData());
        receiveOperationResponse();

        return objectInfo;
    }

    @Override
    public void getObject(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        sendOperationRequest(OperationCode.GET_OBJECT, objectHandle);
        receiveData(dst);
        receiveOperationResponse();
    }

    @Override
    public void getThumb(UINT32 objectHandle, OutputStream dst) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        sendOperationRequest(OperationCode.GET_THUMB, objectHandle);
        receiveData(dst);
        receiveOperationResponse();
    }

    @Override
    public void deleteObject(UINT32 objectHandle) throws IOException, PtpException {
        Validators.validateNonNull("objectHandle", objectHandle);

        sendOperationRequest(OperationCode.DELETE_OBJECT, objectHandle);
        receiveOperationResponse();
    }

    @Override
    public void initiateCapture() throws IOException, PtpException {
        sendOperationRequest(OperationCode.INITIATE_CAPTURE);
        receiveOperationResponse();
    }

    @Override
    public DevicePropDesc<?> getDevicePropDesc(Code<UINT16> devicePropCode) throws IOException, PtpException {
        sendOperationRequest(OperationCode.GET_DEVICE_PROP_DESC, new UINT32(devicePropCode.value().intValue()));
        DevicePropDesc<?> devicePropDesc = DevicePropDesc.valueOf(receiveData());
        receiveOperationResponse();

        return devicePropDesc;
    }

    @Override
    public byte[] getDevicePropValue(Code<UINT16> devicePropCode) throws IOException, PtpException {
        Validators.validateNonNull("devicePropCode", devicePropCode);

        sendOperationRequest(OperationCode.GET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        byte[] value = receiveData();
        receiveOperationResponse();

        return value;
    }

    @Override
    public byte getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return getDevicePropValue(devicePropCode)[0];
    }

    @Override
    public UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return UINT16.valueOf(getDevicePropValue(devicePropCode));
    }

    @Override
    public UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return UINT32.valueOf(getDevicePropValue(devicePropCode));
    }

    @Override
    public String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException, PtpException {
        return STR.valueOf(getDevicePropValue(devicePropCode));
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException, PtpException {
        Validators.validateNonNull("devicePropCode", devicePropCode);
        Validators.validateNonNull("value", value);

        sendOperationRequest(OperationCode.SET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        sendData(value);
        receiveOperationResponse();
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, byte value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, new byte[]{value});
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, UINT16 value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, UINT32 value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, value.bytes());
    }

    @Override
    public void setDevicePropValue(Code<UINT16> devicePropValue, String value) throws IOException, PtpException {
        setDevicePropValue(devicePropValue, STR.toBytes(value));
    }

    @Override
    public void terminateOpenCapture() throws IOException, PtpException {
        terminateOpenCapture(UINT32.MAX_VALUE);
    }

    @Override
    public void terminateOpenCapture(UINT32 transactionID) throws IOException, PtpException {
        sendOperationRequest(OperationCode.TERMINATE_OPEN_CAPTURE, transactionID);
        receiveOperationResponse();
    }

    @Override
    public UINT32 initiateOpenCapture() throws IOException, PtpException {
        UINT32 transactionID = sendOperationRequest(OperationCode.INITIATE_OPEN_CAPTURER);
        receiveOperationResponse();

        return transactionID;
    }

    // Data

    @Override
    public byte[] receiveData() throws IOException, PtpException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        receiveData(baos);
        return baos.toByteArray();
    }
}
