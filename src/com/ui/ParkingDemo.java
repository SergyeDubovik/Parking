package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.pricing.LenientPricingCalculator;
import com.parking.src.com.core.ParkingImpl;

public class ParkingDemo {
    public static void main(String[] args) throws InterruptedException {
        Parking parking = new ParkingImpl(20, new LenientPricingCalculator());
        parking.enter("AA8888AA");
        parking.enter("XA1234AA");
        Thread.sleep(10_000);
        parking.exit("AA8888AA");
        parking.enter("XA7777AA");
    }
}
