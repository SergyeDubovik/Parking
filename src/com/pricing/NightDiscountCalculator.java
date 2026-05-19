package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NightDiscountCalculator implements PricingCalculator {
    private final LocalTime nightStart;
    private final LocalTime nightEnd;
    private final BigDecimal nightDiscount;

    public NightDiscountCalculator() {
        this(LocalTime.of(22, 0), LocalTime.of(6, 0), new BigDecimal("0.5"));
    }

    public NightDiscountCalculator(LocalTime nightStart, LocalTime nightEnd, BigDecimal nightDiscount) {
        this.nightStart = nightStart;
        this.nightEnd = nightEnd;
        this.nightDiscount = nightDiscount;
    }

    @Override
    public BigDecimal calculate(LocalDateTime enter, LocalDateTime exit) {
        Duration duration = Duration.between(enter, exit);
        if (duration.compareTo(Duration.ofHours(1)) < 0) {
            return ParkingPrice.LESS_THAN_HOUR.getValue();
        }
        BigDecimal billingTime = BigDecimal.ZERO;
        LocalDateTime currentTime = enter;

        while (currentTime.isBefore(exit)) {
            BigDecimal perHour = ParkingPrice.PER_HOUR.getValue();
            if (isNightTime(currentTime)) {
                perHour = perHour.multiply(nightDiscount);
            }
            billingTime = billingTime.add(perHour);
            currentTime = currentTime.plusHours(1);
        }
        return billingTime;
    }

    private boolean isNightTime(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        return time.isAfter(nightStart) || time.isBefore(nightEnd);
    }
}
