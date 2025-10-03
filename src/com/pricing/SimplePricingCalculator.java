package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.Duration;

public class SimplePricingCalculator implements PricingCalculator {
    @Override
    public BigDecimal calculate(Duration duration) {
        BigDecimal lessThanHour = ParkingPrice.LESS_THAN_HOUR.getValue();
        BigDecimal perHour = ParkingPrice.PER_HOUR.getValue();
        BigDecimal perDay = ParkingPrice.PER_DAY.getValue();
        if (duration.compareTo(Duration.ofHours(1)) < 0) {
            return lessThanHour;
        } else if (duration.compareTo(Duration.ofDays(1)) < 0) {
            long hours = duration.toHours();
            long extraMinutes = duration.toMinutes() % 60;
            if (extraMinutes > 0) {
                hours += 1;
            }
            BigDecimal convertedHours = BigDecimal.valueOf(hours);
            return perHour.multiply(convertedHours);
        } else {
            long days = duration.toDays();
            long extraHours = duration.toHours() % 24;
            if (extraHours > 0) {
                days++;
            }
            BigDecimal convertToDays = BigDecimal.valueOf(days);
            return perDay.multiply(convertToDays);
        }
    }
}
