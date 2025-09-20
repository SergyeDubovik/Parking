package com.parking;

import java.math.BigDecimal;
import java.time.Duration;

public class LenientPricingCalculator implements PricingCalculator {
    @Override
    public BigDecimal calculate(Duration duration) {
        BigDecimal lessThanHour = BigDecimal.ZERO;
        BigDecimal perHour = new BigDecimal(20);
        BigDecimal perDay = new BigDecimal(300);
        if (duration.compareTo(Duration.ofHours(1)) < 0) {
            return lessThanHour;
        } else if (duration.compareTo(Duration.ofDays(1)) < 0) {
            long hours = duration.toHours();
            BigDecimal convertedHours = BigDecimal.valueOf(hours);
            return perHour.multiply(convertedHours);
        } else {
            long days = duration.toDays();
            long extraHours = duration.toHours() % 24;
            if (extraHours > 4) {
                days += 1;
            }
            BigDecimal convertToDays = BigDecimal.valueOf(days);
            return convertToDays.multiply(perDay);
        }
    }
}
