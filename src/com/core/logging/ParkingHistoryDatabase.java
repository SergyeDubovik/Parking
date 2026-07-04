package com.parking.src.com.core.logging;

import com.parking.src.com.database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ParkingHistoryDatabase implements ParkingHistory {
    @Override
    public void log(String carNumber, LocalDateTime enterTime, LocalDateTime exitTime, long duration, BigDecimal price) {
        String sql = "INSERT INTO parking_history(car_number, enter_time, exit_time, duration_minutes, price)" +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setString(1, carNumber);
            pr.setTimestamp(2, Timestamp.valueOf(enterTime));
            pr.setTimestamp(3, Timestamp.valueOf(exitTime));
            pr.setLong(4, duration);
            pr.setBigDecimal(5, price);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error writing to db", e);
        }
    }
}
