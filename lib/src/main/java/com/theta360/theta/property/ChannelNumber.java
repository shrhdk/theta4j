package com.theta360.theta.property;

import java.util.HashMap;
import java.util.Map;

public enum ChannelNumber {
    RANDOM((byte) 0),
    CH_1((byte) 1),
    CH_6((byte) 6),
    CH_11((byte) 11);

    private final byte value;

    private ChannelNumber(byte value) {
        this.value = value;
    }

    // Getter

    public byte getValue() {
        return value;
    }

    // valueOf

    private static final Map<Byte, ChannelNumber> channelNumbers = new HashMap<>();

    static {
        for (ChannelNumber channelNumber : ChannelNumber.values()) {
            channelNumbers.put(channelNumber.value, channelNumber);
        }
    }

    public static ChannelNumber valueOf(byte value) {
        if (!channelNumbers.containsKey(value)) {
            throw new RuntimeException();
        }

        return channelNumbers.get(value);
    }
}
