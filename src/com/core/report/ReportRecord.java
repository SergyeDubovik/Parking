package com.parking.src.com.core.report;

import java.math.BigDecimal;

public record ReportRecord(String carNumber, String enterTime, String exitTime, String duration, BigDecimal price) {
}
