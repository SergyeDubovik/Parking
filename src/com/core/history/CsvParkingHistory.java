package com.parking.src.com.core.history;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class CsvParkingHistory implements ParkingHistory {
    private static final String HISTORY_FILE_NAME = "parking-history.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void saveHistory(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price) {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(carNumber);
        joiner.add(enterTime.format(formatter));
        joiner.add(exitTime.format(formatter));
        joiner.add(String.valueOf(duration));
        joiner.add(price.toString());
        String line = joiner + System.lineSeparator();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE_NAME, true))) {
            writer.write(line);
        } catch (IOException e) {
            throw new RuntimeException("Error writing history", e);
        }
    }
}
