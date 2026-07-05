package com.parking.src.com.core.logging;

import com.parking.src.com.core.report.ReportRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ParkingHistory {
    void log(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price);
    List<ReportRecord> findAll();
}
