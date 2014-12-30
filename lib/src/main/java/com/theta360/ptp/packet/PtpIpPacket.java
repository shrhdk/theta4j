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
        this.payload = payload;
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
        byte[] typeBytes = type.getBytes();
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
        INIT_COMMAND_REQUEST(1),
        INIT_COMMAND_ACK(2),
        INIT_EVENT_REQUEST(3),
        INIT_EVENT_ACK(4),
        INIT_FAIL(5),
        OPERATION_REQUEST(6),
        OPERATION_RESPONSE(7),
        EVENT(8),
        START_DATA(9),
        DATA(10),
        CANCEL(11),
        END_DATA(12),
        PROBE_REQUEST(13),
        PROBE_RESPONSE(13);

        private static final Map<Integer, Type> numbers = new HashMap<>();

        static {
            for (Type type : Type.values()) {
                numbers.put((int) type.getBytes()[0], type);
            }
        }

        private final byte[] bytes;

        private Type(int number) {
            this.bytes = new byte[]{(byte) number, 0x00, 0x00, 0x00};
        }

        public static Type valueOf(byte[] bytes) {
            if (bytes.length != 4) {
                throw new IllegalArgumentException();
            }

            int number = bytes[0];
            if (!numbers.containsKey(number)) {
                throw new IllegalArgumentException();
            }

            return numbers.get(number);
        }

        public byte[] getBytes() {
            return bytes.clone();
        }
    }
}
