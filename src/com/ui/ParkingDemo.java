package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.pricing.LenientPricingCalculator;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.NightDiscountCalculator;
import com.parking.src.com.pricing.PricingCalculator;
import com.parking.src.com.pricing.WeekendFreeCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        NightDiscountCalculator calculator1 = new NightDiscountCalculator(
                LocalTime.of(23, 0),
                LocalTime.of(5, 0),
                new BigDecimal("0.4"));

        LocalDateTime enterParking = LocalDateTime.now();
        LocalDateTime exitParking = enterParking.plusDays(2).withHour(5);
        System.out.println("enter - " + enterParking);
        System.out.println("exit - " + exitParking);
        System.out.println("amount - " + calculator1.calculate(enterParking, exitParking));
    }
}
