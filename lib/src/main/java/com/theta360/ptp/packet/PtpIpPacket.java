package com.theta360.ptp.packet;

import com.theta360.ptp.type.UINT32;
import com.theta360.util.Validators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PtpIpPacket {
    final Type type;
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
        Type type = getType();
        byte[] payload = getPayload();

        byte[] lengthBytes = new UINT32(8 + payload.length).bytes();
        byte[] typeBytes = type.getCode().bytes();
        byte[] packetBytes = new byte[8 + payload.length];
        System.arraycopy(lengthBytes, 0, packetBytes, 0, 4);
        System.arraycopy(typeBytes, 0, packetBytes, 4, 4);
        System.arraycopy(payload, 0, packetBytes, 8, payload.length);

        return packetBytes;
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

        public UINT32 getCode() {
            return code;
        }
    }
}
