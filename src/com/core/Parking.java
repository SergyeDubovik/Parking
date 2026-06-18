package com.parking.src.com.core;

import com.parking.src.com.model.ParkingRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface Parking {
    boolean enter(String carNumber);
    BigDecimal exit(String carNumber);
    boolean[] getStatus();
    int takenSlots();
    Optional<Integer> findCar(String carNumber);
    void generateReport();
    Map<String, ParkingRecord> getParkedCars();
}
