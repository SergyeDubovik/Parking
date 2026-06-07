package com.parking.src.com.ui;

import com.parking.src.com.core.Parking;
import com.parking.src.com.core.report.HtmlReportGenerator;
import com.parking.src.com.pricing.LenientPricingCalculator;
import com.parking.src.com.core.ParkingImpl;
import com.parking.src.com.pricing.NightDiscountCalculator;
import com.parking.src.com.pricing.PricingCalculator;
import com.parking.src.com.pricing.WeekendFreeCalculator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ParkingDemo {
    public static void main(String[] args) throws InterruptedException, SQLException {
        ParkingImpl parking = new ParkingImpl(10, new NightDiscountCalculator(), new HtmlReportGenerator());
        parking.enter("bn1221sx");
        parking.enter("as2341df");
        parking.enter("er2389yy");

        parking.exit("bn1221sx");

        parking.saveData();

    }
}
