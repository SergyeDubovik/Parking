package com.parking.src.com.core;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public interface Parking {
    boolean enter(String carNumber);
    BigDecimal exit(String carNumber);
    boolean[] getStatus();
    int takenSlots();
    Optional<Integer> findCar(String carNumber);
}
