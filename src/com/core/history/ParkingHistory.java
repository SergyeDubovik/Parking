package com.parking.src.com.core.history;

import com.parking.src.com.core.report.ReportRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ParkingHistory {
    void saveHistory(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price);
    List<ReportRecord> findAll();
}
