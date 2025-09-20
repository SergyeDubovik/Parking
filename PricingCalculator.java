package com.parking;

import java.math.BigDecimal;
import java.time.Duration;

public interface PricingCalculator {
    BigDecimal calculate(Duration duration);
}
