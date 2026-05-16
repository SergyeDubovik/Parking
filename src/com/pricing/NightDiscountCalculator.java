package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NightDiscountCalculator implements PricingCalculator {
    private final LocalTime NIGHT_START = LocalTime.of(22, 00);
    private final LocalTime NIGHT_END = LocalTime.of(6, 00);
    private final BigDecimal nightDiscount = new BigDecimal("0.5");

    @Override
    public BigDecimal calculate(LocalDateTime enter, LocalDateTime exit) {
        Duration duration = Duration.between(enter, exit);
        if (duration.compareTo(Duration.ofHours(1)) < 0) {
            return ParkingPrice.LESS_THAN_HOUR.getValue();
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDateTime currentTime = enter;

        while (currentTime.isBefore(exit)) {
            BigDecimal perHour = ParkingPrice.PER_HOUR.getValue();
            if (isNightTime(currentTime)) {
                perHour = perHour.multiply(nightDiscount);
            }
            totalPrice = totalPrice.add(perHour);
            currentTime = currentTime.plusHours(1);
        }
        return totalPrice;
    }

    private boolean isNightTime(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        return !time.isBefore(NIGHT_START) || time.isBefore(NIGHT_END);
    }
}
