/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptp;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.code.OperationCode;
import org.theta4j.ptp.code.ResponseCode;
import org.theta4j.ptp.data.DeviceInfo;
import org.theta4j.ptp.data.Response;
import org.theta4j.ptp.type.*;
import org.theta4j.util.Validators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractPtpInitiator implements PtpInitiator {
    // Session ID

    private UINT32 sessionID = UINT32.ZERO;

    protected UINT32 getSessionID() {
        return sessionID;
    }

    // Operations (Base)

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode) throws IOException {
        return sendOperation(operationCode, UINT32.ZERO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1) throws IOException {
        return sendOperation(operationCode, p1, UINT32.ZERO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2) throws IOException {
        return sendOperation(operationCode, p1, p2, UINT32.ZERO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3) throws IOException {
        return sendOperation(operationCode, p1, p2, p3, UINT32.ZERO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 sendOperation(Code<UINT16> operationCode, UINT32 p1, UINT32 p2, UINT32 p3, UINT32 p4) throws IOException {
        return sendOperation(operationCode, p1, p2, p3, p4, UINT32.ZERO);
    }

    // Operations

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceInfo getDeviceInfo() throws IOException {
        sendOperation(OperationCode.GET_DEVICE_INFO);
        DeviceInfo deviceInfo = DeviceInfo.read(receiveData());
        checkResponse();

        return deviceInfo;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeSession() throws IOException {
        sendOperation(OperationCode.CLOSE_SESSION);
        checkResponse();

        this.sessionID = UINT32.ZERO;
    }

    // Device Property

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getDevicePropValue(Code<UINT16> devicePropCode) throws IOException {
        Validators.notNull("devicePropCode", devicePropCode);

        sendOperation(OperationCode.GET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        InputStream is = receiveData();
        checkResponse();

        return is;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT8 getDevicePropValueAsUINT8(Code<UINT16> devicePropCode) throws IOException {
        return UINT8.read(getDevicePropValue(devicePropCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT16 getDevicePropValueAsUINT16(Code<UINT16> devicePropCode) throws IOException {
        return UINT16.read(getDevicePropValue(devicePropCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT32 getDevicePropValueAsUINT32(Code<UINT16> devicePropCode) throws IOException {
        return UINT32.read(getDevicePropValue(devicePropCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UINT64 getDevicePropValueAsUINT64(Code<UINT16> devicePropCode) throws IOException {
        return UINT64.read(getDevicePropValue(devicePropCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDevicePropValueAsString(Code<UINT16> devicePropCode) throws IOException {
        return STR.read(getDevicePropValue(devicePropCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, byte[] value) throws IOException {
        Validators.notNull("devicePropCode", devicePropCode);
        Validators.notNull("value", value);

        sendOperation(OperationCode.SET_DEVICE_PROP_VALUE, new UINT32(devicePropCode.value().intValue()));
        sendData(value);
        checkResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, UINT8 value) throws IOException {
        setDevicePropValue(devicePropCode, value.bytes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, UINT16 value) throws IOException {
        setDevicePropValue(devicePropCode, value.bytes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, UINT32 value) throws IOException {
        setDevicePropValue(devicePropCode, value.bytes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDevicePropValue(Code<UINT16> devicePropCode, String value) throws IOException {
        setDevicePropValue(devicePropCode, STR.toBytes(value + "\u0000"));
    }

    // Responses

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkResponse() throws IOException {
        Response response = receiveResponse();

        if (!response.getResponseCode().equals(ResponseCode.OK.value())) {
            String message = "ResponseCode was not OK: " + response.getResponseCode();
            throw new PtpException(response.getResponseCode(), message);
        }
    }

    // Data

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream receiveData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        receiveData(baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    // Listener

    protected final PtpEventListenerSet listenerSet = new PtpEventListenerSet();

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addListener(PtpEventListener listener) {
        Validators.notNull("listener", listener);

        return listenerSet.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean removeListener(PtpEventListener listener) {
        Validators.notNull("listener", listener);

        return listenerSet.remove(listener);
    }
}
