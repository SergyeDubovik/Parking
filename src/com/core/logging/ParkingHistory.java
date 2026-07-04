package com.parking.src.com.core.logging;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ParkingHistory {
    void log(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price);
}
