package com.parking.src.com.model;

import java.time.LocalDateTime;

public record ParkingRecord(int slot, LocalDateTime enterTime) {
}
