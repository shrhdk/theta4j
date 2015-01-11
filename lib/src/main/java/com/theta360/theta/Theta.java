package com.theta360.theta;

import com.theta360.ptp.PtpIpInitiator;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.packet.OperationRequestPacket;
import com.theta360.ptp.packet.OperationResponsePacket;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public final class Theta extends PtpIpInitiator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Theta.class);

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int TCP_PORT = 15740;

    public Theta() throws IOException {
        super(new GUID(UUID.randomUUID()), IP_ADDRESS, TCP_PORT);
    }

    public void getResizedImageObject(UINT32 objectHandle, OutputStream dst) throws IOException {
        Validators.validateNonNull("objectHandle", objectHandle);
        Validators.validateNonNull("dst", dst);

        // Send OperationRequest (GetResizedImageObject)
        OperationRequestPacket operationRequest = new OperationRequestPacket(
                new UINT32(1),
                com.theta360.theta.OperationCode.GET_RESIZED_IMAGE_OBJECT.getCode(),
                transactionID.next(),
                objectHandle,
                new UINT32(2048),
                new UINT32(1024)
        );
        co.write(operationRequest);
        LOGGER.info("Sent OperationRequest (GetResizedImageObject): " + operationRequest);

        // Receive Data
        ci.readData(dst);

        // Receive OperationResponse
        OperationResponsePacket operationResponse = ci.readOperationResponsePacket();
        LOGGER.info("Received OperationResponse: " + operationResponse);
    }
}
