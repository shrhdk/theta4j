package com.theta360.theta;

import com.theta360.ptp.type.UINT32;

import java.util.ArrayList;
import java.util.List;

class PrimitiveTypeUtils {
    private PrimitiveTypeUtils() {
        throw new AssertionError();
    }

    public static List<Long> convert(List<UINT32> src) {
        List<Long> dst = new ArrayList<>();
        for (UINT32 uint32 : src) {
            dst.add(uint32.longValue());
        }
        return dst;
    }
}
