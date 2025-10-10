package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class WeekendFreeCalculator implements PricingCalculator {
    PricingCalculator usualCalculator = new SimplePricingCalculator();

    @Override
    public BigDecimal calculate(Duration duration) {
        LocalDateTime enter = LocalDateTime.now();
        LocalDateTime exit = enter.plus(duration);
        return calculateForTest(enter, exit);
    }

    public BigDecimal calculateForTest(LocalDateTime enter, LocalDateTime exit) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDateTime currentDateTime = enter;
        while (currentDateTime.isBefore(exit)) {
            DayOfWeek day = currentDateTime.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                totalPrice = totalPrice.add(ParkingPrice.PER_DAY.getValue());
            }
            currentDateTime = currentDateTime.plusDays(1);
        }
        return totalPrice;
    }
}
