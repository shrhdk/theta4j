/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.ptpip.packet;

import org.theta4j.ptp.code.Code;
import org.theta4j.ptp.io.PtpInputStream;
import org.theta4j.ptp.io.PtpOutputStream;
import org.theta4j.ptp.type.UINT32;
import org.theta4j.util.Validators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * PTP-IP Packet
 */
public abstract class PtpIpPacket {
    // Utility Field

    static final int HEADER_SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES;

    // Map for valueOf method

    private static final Map<UINT32, Type> TYPE_MAP = new HashMap<>();

    static {
        for (Type type : Type.values()) {
            TYPE_MAP.put(type.value, type);
        }
    }

    // Constructor

    PtpIpPacket() {
        // Allow extension for same package classes.
    }

    // Getter

    abstract Type getType();

    abstract byte[] getPayload();

    // Converter

    public final byte[] bytes() {
        UINT32 length = new UINT32(UINT32.SIZE_IN_BYTES + Type.SIZE_IN_BYTES + getPayload().length);

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final PtpOutputStream pos = new PtpOutputStream(baos);
            pos.write(length);
            pos.write(getType().value);
            pos.write(getPayload());
            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    // Inner Types

    public enum Type implements Code<UINT32> {
        INIT_COMMAND_REQUEST(0x0001),
        INIT_COMMAND_ACK(0x0002),
        INIT_EVENT_REQUEST(0x0003),
        INIT_EVENT_ACK(0x0004),
        INIT_FAIL(0x0005),
        OPERATION_REQUEST(0x0006),
        OPERATION_RESPONSE(0x0007),
        EVENT(0x0008),
        START_DATA(0x0009),
        DATA(0x000A),
        CANCEL(0x000B),
        END_DATA(0x000C),
        PROBE_REQUEST(0x000D),
        PROBE_RESPONSE(0x000D);

        public static final int SIZE_IN_BYTES = 4;

        // Property

        private final UINT32 value;

        // Constructor

        Type(int value) {
            this.value = new UINT32(value);
        }

        // Code

        @Override
        public UINT32 value() {
            return value;
        }

        // valueOf

        public static Type valueOf(UINT32 value) {
            Validators.notNull("value", value);

            if (!TYPE_MAP.containsKey(value)) {
                throw new IllegalArgumentException("Unknown Packet Type: " + value);
            }

            return TYPE_MAP.get(value);
        }

        // read

        public static Type read(PtpInputStream pis) throws IOException {
            Validators.notNull("pis", pis);

            UINT32 typeValue = pis.readUINT32();
            return valueOf(typeValue);
        }
    }
}
