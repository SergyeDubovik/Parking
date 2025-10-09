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
        DayOfWeek entDay = enter.getDayOfWeek();
        DayOfWeek exDay = exit.getDayOfWeek();
        if ((entDay == DayOfWeek.SATURDAY || entDay == DayOfWeek.SUNDAY) &&
                (exDay == DayOfWeek.SATURDAY || exDay == DayOfWeek.SUNDAY)) {
            return BigDecimal.ZERO;
        }
        return usualCalculator.calculate(duration);
    }
}
