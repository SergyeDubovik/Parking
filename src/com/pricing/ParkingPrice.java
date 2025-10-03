package com.parking.src.com.pricing;

import java.math.BigDecimal;

public enum ParkingPrice {
    LESS_THAN_HOUR(BigDecimal.ZERO),
    PER_HOUR(new BigDecimal(20)),
    PER_DAY(new BigDecimal(300));

    private final BigDecimal value;

    ParkingPrice(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
