package com.parking.src.com.model;

import java.time.LocalDateTime;

public class ParkingRecord {
    private int slot;
    private LocalDateTime enterTime;

    public ParkingRecord(int slot, LocalDateTime enterTime) {
        this.slot = slot;
        this.enterTime = enterTime;
    }

    public int getSlot() {
        return slot;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }
}
