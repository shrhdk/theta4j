/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.data;

import org.theta4j.ptp.type.INT16;
import org.theta4j.util.Validators;

import java.util.HashMap;
import java.util.Map;

public enum ExposureBiasCompensation {
    PLUS_2000(2000), PLUS_1700(1700), PLUS_1300(1300), PLUS_1000(1000), PLUS_700(700), PLUS_300(300), ZERO(0),
    MINUS_300(-300), MINUS_700(-700), MINUS_1000(-1000), MINUS_1300(-1300), MINUS_1700(-1700), MINUS_2000(-2000);

    // Map for valueOf method

    private static final Map<INT16, ExposureBiasCompensation> EXPOSURE_BIAS_COMPENSATION_MAP = new HashMap<>();

    static {
        for (ExposureBiasCompensation exposureBiasCompensation : ExposureBiasCompensation.values()) {
            EXPOSURE_BIAS_COMPENSATION_MAP.put(exposureBiasCompensation.value, exposureBiasCompensation);
        }
    }

    // Property

    private final INT16 value;

    // Constructor

    ExposureBiasCompensation(int value) {
        this.value = new INT16(value);
    }

    // Getter

    public INT16 value() {
        return value;
    }

    // valueOf

    public static ExposureBiasCompensation valueOf(INT16 value) {
        Validators.notNull("value", value);

        if (!EXPOSURE_BIAS_COMPENSATION_MAP.containsKey(value)) {
            throw new IllegalArgumentException("Unknown ExposureBiasCompensation value: " + value);
        }

        return EXPOSURE_BIAS_COMPENSATION_MAP.get(value);
    }
}
