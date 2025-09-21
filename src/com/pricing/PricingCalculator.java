package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.Duration;

public interface PricingCalculator {
    BigDecimal calculate(Duration duration);
}
