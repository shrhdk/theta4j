package com.theta360.ptp.packet;

import com.theta360.ptp.io.PtpInputStream;
import com.theta360.ptp.io.PtpOutputStream;
import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * PTP-IP Packet
 */
public class PtpIpPacket {
    private final Type type;
    byte[] payload;

    public PtpIpPacket(Type type) {
        this(type, new byte[0]);
    }

    public PtpIpPacket(Type type, byte[] payload) {
        Validators.validateNonNull("type", type);
        Validators.validateNonNull("payload", payload);

        this.type = type;
        this.payload = payload.clone();
    }

    public Type getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload.clone();
    }

    public final byte[] bytes() {
        UINT32 length = new UINT32(UINT32.SIZE + UINT32.SIZE + payload.length);

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PtpOutputStream pos = new PtpOutputStream(baos);
        ) {
            pos.write(length);
            pos.write(type.code);
            pos.write(payload);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PtpIpPacket that = (PtpIpPacket) o;

        if (!Arrays.equals(payload, that.payload)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return "PtpIpPacket{" +
                "type=" + type +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }

    public enum Type {
        INIT_COMMAND_REQUEST(new UINT32(0x0001)),
        INIT_COMMAND_ACK(new UINT32(0x0002)),
        INIT_EVENT_REQUEST(new UINT32(0x0003)),
        INIT_EVENT_ACK(new UINT32(0x0004)),
        INIT_FAIL(new UINT32(0x0005)),
        OPERATION_REQUEST(new UINT32(0x0006)),
        OPERATION_RESPONSE(new UINT32(0x0007)),
        EVENT(new UINT32(0x0008)),
        START_DATA(new UINT32(0x0009)),
        DATA(new UINT32(0x000A)),
        CANCEL(new UINT32(0x000B)),
        END_DATA(new UINT32(0x000C)),
        PROBE_REQUEST(new UINT32(0x000D)),
        PROBE_RESPONSE(new UINT32(0x000D));

        private static final Map<UINT32, Type> numbers = new HashMap<>();

        static {
            for (Type type : Type.values()) {
                numbers.put(type.code, type);
            }
        }

        private final UINT32 code;

        private Type(UINT32 code) {
            this.code = code;
        }

        public static Type valueOf(UINT32 value) {
            Validators.validateNonNull("value", value);

            if (!numbers.containsKey(value)) {
                throw new IllegalArgumentException();
            }

            return numbers.get(value);
        }

        public static Type read(PtpInputStream pis) throws IOException {
            Validators.validateNonNull("pis", pis);
            UINT32 typeValue = pis.readUINT32();
            return valueOf(typeValue);
        }

        public UINT32 getCode() {
            return code;
        }
    }
}
