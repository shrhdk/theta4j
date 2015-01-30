package com.theta360.ptpip.packet;

import com.theta360.ptp.code.Code;
import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.io.PtpOutputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * PTP-IP Packet
 */
public class PtpIpPacket {
    private final Type type;
    byte[] payload;

    // Utility Field

    protected static final int HEADER_SIZE_IN_BYTES = UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES;

    // Constructor

    public PtpIpPacket(Type type) {
        this(type, new byte[0]);
    }

    public PtpIpPacket(Type type, byte[] payload) {
        Validators.validateNonNull("type", type);
        Validators.validateNonNull("payload", payload);

        this.type = type;
        this.payload = payload.clone();
    }

    // Getter

    public Type getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload.clone();
    }

    // Converter

    public final byte[] bytes() {
        UINT32 length = new UINT32(UINT32.SIZE_IN_BYTES + UINT32.SIZE_IN_BYTES + payload.length);

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PtpOutputStream pos = new PtpOutputStream(baos);
        ) {
            pos.write(length);
            pos.write(type.value);
            pos.write(payload);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Basic Method

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PtpIpPacket rhs = (PtpIpPacket) o;

        return new EqualsBuilder()
                .append(type, rhs.type)
                .append(payload, rhs.payload)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(type)
                .append(payload)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("payload", payload)
                .toString();
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

        public static final int SIZE = 4;

        private final UINT32 value;

        private Type(int value) {
            this.value = new UINT32(value);
        }

        // Code

        public UINT32 value() {
            return value;
        }

        // valueOf

        private static final Map<UINT32, Type> TYPE_MAP = new HashMap<>();

        static {
            for (Type type : Type.values()) {
                TYPE_MAP.put(type.value, type);
            }
        }

        public static Type valueOf(UINT32 value) {
            Validators.validateNonNull("value", value);

            if (!TYPE_MAP.containsKey(value)) {
                throw new IllegalArgumentException();
            }

            return TYPE_MAP.get(value);
        }

        // read

        public static Type read(PtpInputStream pis) throws IOException {
            Validators.validateNonNull("pis", pis);
            UINT32 typeValue = pis.readUINT32();
            return valueOf(typeValue);
        }
    }
}
