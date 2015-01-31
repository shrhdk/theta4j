package com.theta360.theta.data;

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

    private static final Map<Byte, ChannelNumber> CHANNEL_NUMBER_MAP = new HashMap<>();

    static {
        for (ChannelNumber channelNumber : ChannelNumber.values()) {
            CHANNEL_NUMBER_MAP.put(channelNumber.value, channelNumber);
        }
    }

    public static ChannelNumber valueOf(byte value) {
        if (!CHANNEL_NUMBER_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown ChannelNumber Value: " + value);
        }

        return CHANNEL_NUMBER_MAP.get(value);
    }
}
