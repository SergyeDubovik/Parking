package com.parking.src.com.pricing;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class WeekendFreeCalculator implements PricingCalculator {

    @Override
    public BigDecimal calculate(LocalDateTime enter, LocalDateTime exit) {
        if (enter.plusHours(1).isAfter(exit)) {
            return ParkingPrice.LESS_THAN_HOUR.getValue();
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDateTime currentDateTime = enter;

        while (currentDateTime.isBefore(exit)) {
            DayOfWeek day = currentDateTime.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                totalPrice = totalPrice.add(ParkingPrice.PER_DAY.getValue());
            }
            currentDateTime = currentDateTime.plusHours(1);
        }
        return totalPrice;
    }
}
