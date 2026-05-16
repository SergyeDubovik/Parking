package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public interface PricingCalculator {
    BigDecimal calculate(LocalDateTime enter, LocalDateTime exit);
}
