package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.pricing.LenientPricingCalculator;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.PricingCalculator;
import com.parking.src.com.pricing.WeekendFreeCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingDemo {
    public static void main(String[] args) throws InterruptedException {
        Parking parking = new ParkingImpl(20, new LenientPricingCalculator());
        parking.enter("AA8888AA");
        parking.enter("XA1234AA");
//        Thread.sleep(10_000);
        parking.exit("AA8888AA");
        parking.enter("XA7777AA");

        WeekendFreeCalculator weekendFreeCalculator = new WeekendFreeCalculator();
        LocalDateTime enter = LocalDateTime.of(2025, 10, 10, 16, 0);
//        LocalDateTime exit = LocalDateTime.of(2025, 10, 11, 21, 0);
        LocalDateTime exit = LocalDateTime.of(2025, 10, 31, 21, 0);
        BigDecimal price = weekendFreeCalculator.calculateForTest(enter, exit);
        System.out.println("price: " + price);
    }
}
