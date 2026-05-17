package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.pricing.LenientPricingCalculator;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.NightDiscountCalculator;
import com.parking.src.com.pricing.PricingCalculator;
import com.parking.src.com.pricing.WeekendFreeCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingDemo {
    public static void main(String[] args) throws InterruptedException {
        Parking parking = new ParkingImpl(20, new NightDiscountCalculator());
        parking.enter("AA8888AA");
        parking.enter("XA1234AA");
//        Thread.sleep(10_000);
        parking.exit("AA8888AA");
        parking.enter("XA7777AA");

        NightDiscountCalculator calculator = new NightDiscountCalculator();
        LocalDateTime enter = LocalDateTime.now();
        LocalDateTime exit = enter.plusDays(1).withHour(6);
        System.out.println("enter: " + enter);
        System.out.println("exit: " + exit);
        System.out.println("price: " + calculator.calculate(enter, exit));
    }
}
