package org.theta4j.data;

import org.theta4j.ptp.type.UINT8;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum ChannelNumber {
    RANDOM(0),
    CH_1(1),
    CH_6(6),
    CH_11(11);

    private final UINT8 value;

    private ChannelNumber(int value) {
        this.value = new UINT8(value);
    }

    // Getter

    public UINT8 value() {
        return value;
    }

    // valueOf

    private static final Map<UINT8, ChannelNumber> CHANNEL_NUMBER_MAP = new HashMap<>();

    static {
        for (ChannelNumber channelNumber : ChannelNumber.values()) {
            CHANNEL_NUMBER_MAP.put(channelNumber.value, channelNumber);
        }
    }

    public static ChannelNumber valueOf(UINT8 value) {
        Validators.validateNonNull("value", value);

        if (!CHANNEL_NUMBER_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown ChannelNumber Value: " + value);
        }

        return CHANNEL_NUMBER_MAP.get(value);
    }
}
